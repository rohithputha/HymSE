package org.hitro.diskinterfaces;

import org.hitro.config.PageConfig;
import org.hitro.diskinterfaces.abstractions.DiskInterface;
import org.hitro.diskinterfaces.abstractions.ReadDiskInterface;
import org.hitro.exception.HymDiskAccessException;
import org.hitro.utils.FileLockManager;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadPage implements ReadDiskInterface, Callable<byte[]> {

    private RandomAccessFile raf;
    private FileLockManager fileLockManager;
    private int offset;
    private ReadPage(RandomAccessFile raf,FileLockManager fileLockManager){
        this.raf = raf;
        this.fileLockManager = fileLockManager;
        this.offset = -1;
    }
    private ReadPage(RandomAccessFile raf, FileLockManager fileLockManager, int offset){
        this.raf= raf;
        this.fileLockManager = fileLockManager;
        this.offset = offset;
    }
    @Override
    public byte[] call() throws Exception {
        if (this.offset!=-1) {
            byte[] page = this.execute(this.offset);
            this.closeFile();
            return page;
        }
        return null;
    }
    @Override
    public byte[] execute(Integer offset) {
        byte[] rd = new byte[PageConfig.getPageSize()];
        ReentrantReadWriteLock.ReadLock readLock = fileLockManager.getRwLock().readLock();
        try {
            readLock.lock();
            fileLockManager.getRwLock().readLock();
            raf.seek(offset);
            int bytesRead = raf.read(rd);
            readLock.unlock();
        } catch (IOException e) {
            throw new HymDiskAccessException(e.getMessage());
        }
        return rd;
    }
    public void closeFile(){
        try {
            this.raf.close();
        } catch (IOException e) {
            throw new HymDiskAccessException(e.getMessage());
        }
    }
    public static ReadPage getInstance(String file, FileLockManager fileLockManager){
        try{
            return new ReadPage(new RandomAccessFile(file, "r"),fileLockManager);
        }
        catch (Exception e){
            throw new HymDiskAccessException(e.getMessage());
        }
    }
    public static ReadPage getInstance(String file, FileLockManager fileLockManager, int offset){
        try{
            return new ReadPage(new RandomAccessFile(file, "r"),fileLockManager, offset);
        }
        catch (Exception e){
            throw new HymDiskAccessException(e.getMessage());
        }
    }
}
