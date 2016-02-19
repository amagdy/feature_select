package com.a1works.parralelFileAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creates a thread pool where every thread reads a chunk of a big file in parallel.
 * Every chunk is read in lines
 */
public class ParallelFileProcessor<T, E> {
    private int threadsCount = 0;
    private List<Runnable> tasks;
    private Callable<T> callback;


    public static class Builder<T, E> {
        private int threadsCount = 0;
        private Callable<T> callback;
        private Chunker chunker;
        private ChunkHandlerFactory<E> handlerFactory;

        public Builder(Chunker chunker, ChunkHandlerFactory<E> handlerFactory) {
            this.chunker = chunker;
            this.handlerFactory = handlerFactory;
            this.threadsCount = chunker.getChunksCount();
            this.callback = null;
        }

        public Builder<T, E> setThreadsCount(int threadsCount){
            this.threadsCount = threadsCount;
            return this;
        }

        public Builder<T, E> setCallBack(Callable<T> callback){
            this.callback = callback;
            return this;
        }

        public ParallelFileProcessor<T, E> build(){
            ParallelFileProcessor<T, E> instance = new ParallelFileProcessor(threadsCount, chunker, handlerFactory, callback);
            return instance;
        }
    }

    private ParallelFileProcessor (int threadsCount, Chunker chunker, ChunkHandlerFactory<E> handlerFactory, Callable<T> callback) {
        List<Runnable> tasks = generateTasks(chunker, handlerFactory);
        this.threadsCount = threadsCount;
        this.tasks = tasks;
        this.callback = callback;
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (Runnable task : tasks) {
            executor.execute(task);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                this.wait(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (callback != null) {
            try {
                callback.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static List<Runnable> generateTasks(Chunker chunker, ChunkHandlerFactory<?> chunkHandlersFactory) {
        List<Runnable> tasks = new ArrayList<>(chunker.getChunksCount());
        for (ChunkReader reader : chunker.readers()) {
            ChunkHandler<?> handler = chunkHandlersFactory.newChunkHandler();
            Task task = new Task(reader, handler);
            tasks.add(task);
        }
        return tasks;
    }



    private static class Task implements Runnable {
        private ChunkReader reader;
        private ChunkHandler<?> handler;

        private Task(ChunkReader reader, ChunkHandler<?> handler) {
            this.reader = reader;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    handler.processLine(line);
                }
                handler.finished();
            } catch (Exception ex) {
                handler.error(ex);
            } finally {
                try {
                    reader.close();
                } catch (Exception ex) {
                    handler.error(ex);
                }
            }
        }
    }
}
