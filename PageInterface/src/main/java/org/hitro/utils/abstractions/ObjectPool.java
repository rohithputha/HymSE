package org.hitro.utils.abstractions;

public interface ObjectPool<T> {
    public T acquire() throws Exception;

    public void release(T obj) throws Exception;
}
