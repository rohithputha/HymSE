package org.hitro.diskinterfaces.abstractions;

public interface ReadDiskInterface extends DiskInterface<byte[], Integer>{
    public void closeFile();
}
