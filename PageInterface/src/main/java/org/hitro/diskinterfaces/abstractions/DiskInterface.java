package org.hitro.diskinterfaces.abstractions;

public interface DiskInterface<I, R> {
    public R read(I offset);

    public boolean write(I offset, R data);

    public boolean createFile(String file);
}
