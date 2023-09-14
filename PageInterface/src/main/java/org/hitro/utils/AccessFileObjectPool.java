package org.hitro.utils;

import lombok.Getter;
import org.hitro.utils.abstractions.ObjectPool;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AccessFileObjectPool implements ObjectPool<RandomAccessFile> {
    private BlockingQueue<RandomAccessFile> pool;

    @Getter
    private char type;
    private AccessFileObjectPool(int size, String file,char type) throws FileNotFoundException {
        pool = new ArrayBlockingQueue<>(size);
        String t = String.valueOf(type);
        for(int i=0;i<size;i++){
            pool.offer(new RandomAccessFile(file, t));
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

    private static AccessFileObjectPool accessFileObjectPool;
    public static AccessFileObjectPool getInstance(int size, String file, char type) throws FileNotFoundException {
        if(accessFileObjectPool==null){
            synchronized (AccessFileObjectPool.class){
                if(accessFileObjectPool==null){
                    accessFileObjectPool = new AccessFileObjectPool(size, file, type);

                }
            }
        }

        return accessFileObjectPool;
    }
}
