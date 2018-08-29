package z.learn.concurrent;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockDictionary {
    HashMap<String, String> map = new HashMap<>();

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();         // 虽然创建了2个 lock，实际上争夺的是同一个同步器
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();      // 这里的2个锁，实际是这个同步器对外提供的接口

    public void put(String key, String value) {
        writeLock.lock();       // 以独占模式获取同步器
        try {
            map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public String get(String key) {
        readLock.lock();        // 共享模式获取同步器
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }
}
