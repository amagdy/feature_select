package com.a1works.parralelFileAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Chunker implements Iterable<ChunkReader> {

    private int chunksCount;
    private File file;
    private long chunkSize;

    public static Chunker getInstance(File file, int chunksCount){
        return new Chunker(file, chunksCount);
    }

    private Chunker(File file, int chunksCount){
        this.file = file;
        this.chunksCount = chunksCount;
        this.chunkSize = (long)Math.ceil((double)file.length() / (double)chunksCount);
    }

    private static class ChunkReadersIterator implements Iterator<ChunkReader> {
        private int chunksCount;
        private File file;
        private long chunkSize;
        private int nextChunkIndex = 0;

        private ChunkReadersIterator(File file, int chunksCount, long chunkSize){
            this.file = file;
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
            ChunkReader reader = new DefaultChunkReader(nextChunkIndex, chunkSize, file);
            nextChunkIndex++;
            return reader;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("You cannot remove an entry from this iterator");
        }
    };

    @Override
    public Iterator<ChunkReader> iterator() {
        return new ChunkReadersIterator(file, chunksCount, chunkSize);
    }


    private static class DefaultChunkReader implements ChunkReader {
        private int chunkIndex;
        private long chunkSize;
        private boolean canRead = false;

        private BufferedReader bufferedReader;
        private long readBytesCount;

        private DefaultChunkReader(int chunkIndex, long chunkSize, File file){
            this.chunkIndex = chunkIndex;
            this.chunkSize = chunkSize;
            long start = chunkIndex * chunkSize;
            try {
                this.bufferedReader = getChunkReaderSetOnFirstCompleteLine(file, start);
                this.canRead = true;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        private boolean isNewLine(int readChar){
            return (readChar == 13 || readChar == 10);
        }

        private BufferedReader getChunkReaderSetOnFirstCompleteLine(File file, long start) throws IOException {
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
