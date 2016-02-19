package com.a1works.parralelFileAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class Chunker {

    private int chunksCount;
    private Reader reader;
    private long chunkSize;

    public static Chunker getInstance(Reader reader, int chunksCount, long chunkSize){
        return new Chunker(reader, chunksCount, chunkSize);
    }

    private Chunker(Reader reader, int chunksCount, long chunkSize){
        this.reader = reader;
        this.chunksCount = chunksCount;
        this.chunkSize = chunkSize;
    }

    private static class ChunkReadersIterator implements Iterator<ChunkReader> {
        private int chunksCount;
        private Reader reader;
        private long chunkSize;
        private int nextChunkIndex = 0;

        private ChunkReadersIterator(Reader reader, int chunksCount, long chunkSize){
            this.reader = reader;
            this.chunksCount = chunksCount;
            this.chunkSize = chunkSize;
        }

        @Override
        public boolean hasNext() {
            return (nextChunkIndex < chunksCount);
        }

        @Override
        public ChunkReader next() {
            if (!this.hasNext()) return null;
            ChunkReader chunkReader = new DefaultChunkReader(nextChunkIndex, chunkSize, reader);
            nextChunkIndex++;
            return chunkReader;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("You cannot remove an entry from this iterator");
        }
    };

    public int getChunksCount(){
        return chunksCount;
    }

    public Iterable<ChunkReader> readers() {
        return new Iterable<ChunkReader>() {
            @Override
            public Iterator<ChunkReader> iterator() {
                return new ChunkReadersIterator(reader, chunksCount, chunkSize);
            }
        };
    }


    private static class DefaultChunkReader implements ChunkReader {
        private int chunkIndex;
        private long chunkSize;
        private boolean canRead = false;

        private BufferedReader bufferedReader;
        private long readBytesCount;

        private DefaultChunkReader(int chunkIndex, long chunkSize, Reader reader){
            this.chunkIndex = chunkIndex;
            this.chunkSize = chunkSize;
            long start = chunkIndex * chunkSize;
            try {
                this.bufferedReader = getChunkReaderSetOnFirstCompleteLine(reader, start);
                this.canRead = true;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        private boolean isNewLine(int readChar){
            return (readChar == 13 || readChar == 10);
        }

        private BufferedReader getChunkReaderSetOnFirstCompleteLine(Reader reader, long start) throws IOException {
            BufferedReader bufferedReader;
            if (start > 0) {    // if this is not the first chunk
                reader.skip(start);
                bufferedReader = new BufferedReader(reader);
                // if the last character of the previous chunk is not a newline character
                if (!isNewLine(bufferedReader.read())) {
                    // skip the rest of the line
                    String line = bufferedReader.readLine();
                    System.out.println("----> " + line);
                    if (line != null) //throw new RuntimeException("Empty Chunk");
                        readBytesCount += line.length();
                }
            } else {
                bufferedReader = new BufferedReader(reader);
            }
            return bufferedReader;
        }

        @Override
        public void close(){
            try {
                canRead = false;
                bufferedReader.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public String readLine() {
            if (!canRead) return null;
            String line = null;
            try {
                if (readBytesCount < chunkSize) {
                    line = bufferedReader.readLine();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                if (line == null) {
                    canRead = false;
                } else {
                    readBytesCount += line.length();
                }
                return line;
            }
        }

        @Override
        public int getChunkIndex() {
            return chunkIndex;
        }
    }
}
