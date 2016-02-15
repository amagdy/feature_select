package com.a1works.parralelFileAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creates a thread pool where every thread reads a chunk of a big file in parallel.
 * Every chunk is read in lines
 */
public class ParallelFileProcessor<T, E> {
    private int threadsCount = 0;
    private List<Runnable> tasks;
    private CountDownLatch countdownLatch;
    private Callable<E> callback;

    public ParallelFileProcessor (File file, Collection<ChunkHandler<E>> chunkHandlers, Callable<T> callback) {
        List<Runnable> tasks = generateTasks(file, chunkHandlers);
        this.threadsCount = chunkHandlers.size();
        this.tasks = tasks;
        this.countdownLatch = new CountDownLatch(threadsCount);
    }

    public ParallelFileProcessor(File file, Collection<ChunkHandler<E>> chunkHandlers) {

    }

    public ParallelFileProcessor(int threadsCount, File file, Class<ChunkHandler<E>> cls) {
        List<ChunkHandler> chunkHandlers = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            try {
                chunkHandlers.add(cls.newInstance());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        this(file, chunkHandlers);
    }

    public ParallelFileProcessor(int threadsCount, File file, ChunkHandler handler) {
        List<ChunkHandler> chunkHandlers = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            chunkHandlers.add(handler);
        }
        this(file, chunkHandlers);
    }

    private void start() {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (Runnable task : tasks) {
            executor.execute(task);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                this.wait(200);
            } catch (InterruptedException e) {

            }
        }
    }


    private static List<Runnable> generateTasks(File file, Collection<? extends ChunkHandler> chunkHandlers) {
        int threadCount = chunkHandlers.size();
        List<Runnable> tasks = new ArrayList<>(threadCount);
        int chunkSize = (int)Math.ceil((double)file.length() / (double)threadCount);
        try {
            int chunkIndex = 0;
            for (ChunkHandler handler : chunkHandlers) {
                Task task = new Task();
                task.start = (chunkIndex * chunkSize);
                // if this is not the first chunk then start one byte back to check if it is a newline or not
                if (task.start > 0) {
                    task.start--;
                }
                task.chunkSize = chunkSize;
                task.file = file;
                task.handler = handler;
                tasks.add(task);
                chunkIndex++;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return tasks;
    }



    private static class Task implements Runnable {
        private long start, chunkSize;
        private ChunkHandler handler;
        private File file;
        private long readBytesCount;

        private boolean isNewLine(int readChar){
            return (readChar == 13 || readChar == 10);
        }

        private BufferedReader getChunkReaderSetOnFirstCompleteLine(File file) throws IOException {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader;
            if (start > 0) {    // if this is not the first chunk
                fileReader.skip(start);
                bufferedReader = new BufferedReader(fileReader);
                // if the last character of the previous chunk is not a newline character
                if (!isNewLine(bufferedReader.read())) {
                    // skip the rest of the line
                    String line = bufferedReader.readLine();
                    if (line == null) throw new RuntimeException("Empty Chunk");
                    readBytesCount += line.length();
                }
            } else {
                bufferedReader = new BufferedReader(fileReader);
            }
            return bufferedReader;
        }


        @Override
        public void run() {
            try {
                BufferedReader bufferedReader = getChunkReaderSetOnFirstCompleteLine(file);
                String line;
                while (readBytesCount < chunkSize) {
                    line = bufferedReader.readLine();
                    if (line == null) break;
                    readBytesCount += line.length();
                    handler.processLine(line);
                }
                bufferedReader.close();
                handler.finished();
            } catch (Exception ex) {
                handler.error(ex);
            }
        }
    }
}
