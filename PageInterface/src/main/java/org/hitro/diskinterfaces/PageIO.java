package org.hitro.diskinterfaces;

import org.hitro.config.DiskOperationConfig;
import org.hitro.config.PageConfig;
import org.hitro.diskinterfaces.abstractions.DiskInterface;
import org.hitro.exception.HymDiskAccessException;
import org.hitro.utils.AccessFileObjectPool;
import org.hitro.utils.FileLockManager;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PageIO implements DiskInterface<Integer, byte[]> {

    private final DiskOperationConfig diskOperationConfig;

    public PageIO(DiskOperationConfig diskOperationConfig){
        this.diskOperationConfig = diskOperationConfig;
    }

    @Override
    public byte[] read(Integer offset) {
        byte[] rd = new byte[PageConfig.getPageSize()];
        try {
            RandomAccessFile raf = diskOperationConfig.getAccessFileObjectPool().acquire();
            diskOperationConfig.lockRead();
            raf.seek(offset);
            raf.read(rd);
            diskOperationConfig.unlockRead();
            diskOperationConfig.getAccessFileObjectPool().release(raf);

        } catch (Exception e) {
            throw new HymDiskAccessException(e.getMessage());
        }
        return rd;
    }

    @Override
    public boolean write(Integer offset,byte[] data) {
        try {
            RandomAccessFile raf = diskOperationConfig.getAccessFileObjectPool().acquire();
            diskOperationConfig.lockWrite();
            raf.seek(offset);
            raf.write(data);
            diskOperationConfig.unlockWrite();
            diskOperationConfig.getAccessFileObjectPool().release(raf);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createFile(String file) {
        try {
            String filePath = PageConfig.getTreeFolder() + file + ".bin";
            return new File(filePath).createNewFile();
        }
        catch (Exception e){
            throw new HymDiskAccessException(e.getMessage());
        }
    }
}
