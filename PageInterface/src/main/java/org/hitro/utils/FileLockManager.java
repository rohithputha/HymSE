package org.hitro.utils;

import lombok.Getter;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileLockManager {

    @Getter
    private final ReentrantReadWriteLock rwLock;

    private FileLockManager(){
        this.rwLock = new ReentrantReadWriteLock();
    }

    private static Map<String, FileLockManager> fileLockManagerMap;
    public static FileLockManager getInstance(String file){
        synchronized (FileLockManager.class){
            if(fileLockManagerMap==null){
                fileLockManagerMap = new ConcurrentHashMap<>();
            }
            if(!fileLockManagerMap.containsKey(file)){
                    fileLockManagerMap.put(file, new FileLockManager());
                }
            }

        return fileLockManagerMap.get(file);
    }
}
