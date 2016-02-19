package com.a1works.parralelFileAccess;


public class DefaultChunkHandlerFactory<T> implements ChunkHandlerFactory<T> {
    private Class<ChunkHandler<T>> cls;
    public static <T> ChunkHandlerFactory<T> getInstance(Class<ChunkHandler<T>> cls) {
        DefaultChunkHandlerFactory<T> instance = new DefaultChunkHandlerFactory<T>();
        instance.cls = cls;
        return instance;
    }

    @Override
    public ChunkHandler<T> newChunkHandler() {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
