package com.a1works.parralelFileAccess;


public interface ChunkReader {
    String readLine();
    int getChunkIndex();
    void close();
}
