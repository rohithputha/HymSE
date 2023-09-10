package org.hitro.diskinterfaces.abstractions;

public interface DiskInterface<T, V> {
    public T execute(V inp);
}
