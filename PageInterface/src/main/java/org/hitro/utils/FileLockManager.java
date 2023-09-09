package org.hitro.utils;

import lombok.Getter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileLockManager {

    @Getter
    private final ReentrantReadWriteLock rwLock;

    private FileLockManager(){
        this.rwLock = new ReentrantReadWriteLock();
    }

    private static FileLockManager fileLockManager;
    public static FileLockManager getInstance(){
        if(fileLockManager == null){
            synchronized (FileLockManager.class){
                if(fileLockManager == null){
                    fileLockManager = new FileLockManager();
                }
            }
        }
        return fileLockManager;
    }
}
