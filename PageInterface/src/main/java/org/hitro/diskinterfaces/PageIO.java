package org.hitro.diskinterfaces;

import org.hitro.config.PageConfig;
import org.hitro.diskinterfaces.abstractions.DiskInterface;
import org.hitro.exception.HymDiskAccessException;
import org.hitro.utils.AccessFileObjectPool;
import org.hitro.utils.FileLockManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PageIO implements DiskInterface<Integer, byte[]> {

    private AccessFileObjectPool accessFileObjectPool;
    private FileLockManager fileLockManager;

    public PageIO(AccessFileObjectPool accessFileObjectPool, FileLockManager fileLockManager){
        this.accessFileObjectPool= accessFileObjectPool;
        this.fileLockManager = fileLockManager;
    }

    @Override
    public byte[] read(Integer offset) {
        byte[] rd = new byte[PageConfig.getPageSize()];
        ReentrantReadWriteLock.ReadLock readLock = fileLockManager.getRwLock().readLock();
        try {
            RandomAccessFile raf = accessFileObjectPool.acquire();
            readLock.lock();
            raf.seek(offset);
            fileLockManager.getRwLock().readLock();
            raf.read(rd);
            readLock.unlock();
            accessFileObjectPool.release(raf);

        } catch (Exception e) {
            throw new HymDiskAccessException(e.getMessage());
        }
        return rd;
    }

    @Override
    public boolean write(Integer offset,byte[] data) {

        ReentrantReadWriteLock.WriteLock writeLock = fileLockManager.getRwLock().writeLock();
        try {
            RandomAccessFile raf = accessFileObjectPool.acquire();
            writeLock.lock();
            fileLockManager.getRwLock().readLock();
            raf.seek(offset);
            raf.write(data);
            writeLock.unlock();
            accessFileObjectPool.release(raf);
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
