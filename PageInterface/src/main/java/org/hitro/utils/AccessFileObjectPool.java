package org.hitro.utils;

import lombok.Getter;
import org.hitro.utils.abstractions.ObjectPool;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class AccessFileObjectPool implements ObjectPool<RandomAccessFile> {
    private BlockingQueue<RandomAccessFile> pool;
    @Getter
    private char type;
    private AccessFileObjectPool(int size, String file,String type) throws FileNotFoundException {
        pool = new ArrayBlockingQueue<>(size);

        for(int i=0;i<size;i++){
            pool.offer(new RandomAccessFile(file, type));
        }
    }
    @Override
    public RandomAccessFile acquire() throws InterruptedException {
        return pool.take();
    }

    @Override
    public void release(RandomAccessFile obj) throws InterruptedException {
        pool.put(obj);
    }

    private static Map<String, AccessFileObjectPool> accessFileObjectPoolMap;
    public static AccessFileObjectPool getInstance(int size, String file) throws FileNotFoundException {
        synchronized (AccessFileObjectPool.class){
                if(accessFileObjectPoolMap == null){
                    accessFileObjectPoolMap = new ConcurrentHashMap<>();
                }
                if(!accessFileObjectPoolMap.containsKey(file)){
                    accessFileObjectPoolMap.put(file, new AccessFileObjectPool(size, file, "rw"));
                }
        }

        return accessFileObjectPoolMap.get(file);
    }
}
