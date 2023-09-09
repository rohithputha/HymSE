package org.hitro.diskinterfaces;

import org.hitro.diskinterfaces.abstractions.DiskInterface;
import org.hitro.utils.FileLockManager;
import java.io.RandomAccessFile;

public class WritePage implements DiskInterface<Boolean,byte[]> {

    private RandomAccessFile raf;
    private FileLockManager fileLockManager;
    private WritePage(RandomAccessFile raf, FileLockManager fileLockManager){
        this.raf = raf;
        this.fileLockManager = fileLockManager;
    }

    @Override
    public Boolean execute(byte[] inp) {
        return true;
    }
}
