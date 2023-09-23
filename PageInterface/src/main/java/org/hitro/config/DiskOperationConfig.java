package org.hitro.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hitro.utils.AccessFileObjectPool;
import org.hitro.utils.FileLockManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@AllArgsConstructor
public class DiskOperationConfig {

    private final boolean diskLock;
    private final int accessObjectPoolSize;

    @Getter
    private final String file;

    public AccessFileObjectPool getAccessFileObjectPool() throws FileNotFoundException {
        return AccessFileObjectPool.getInstance(accessObjectPoolSize, file);
    }

    private FileLockManager getFileLockManager(){
        if(diskLock){
            return FileLockManager.getInstance(file);
        }
        return null;
    }

    public void lockRead(){
        if(diskLock){
            FileLockManager.getInstance(file).getRwLock().readLock().lock();
        }
    }
    public void unlockRead(){
        if(diskLock){
            FileLockManager.getInstance(file).getRwLock().readLock().unlock();
        }
    }
    public void lockWrite(){
        if(diskLock){
            FileLockManager.getInstance(file).getRwLock().writeLock().lock();
        }
    }
    public void unlockWrite(){
        if(diskLock){
            FileLockManager.getInstance(file).getRwLock().writeLock().unlock();
        }
    }
}
