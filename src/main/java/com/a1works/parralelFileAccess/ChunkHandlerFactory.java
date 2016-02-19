package com.a1works.parralelFileAccess;

public interface ChunkHandlerFactory<T> {
    ChunkHandler<T> newChunkHandler();
}
