package z.learn.concurrent;

import java.util.concurrent.locks.StampedLock;

/**
 * 优化版的读写锁
 * Stamp 时间戳
 */
public class StampedLockExample {
    private int state = 0;
    private StampedLock s = new StampedLock();

    void example() {
        long SBIT = ~127L;
        System.out.println(SBIT);
        System.out.println(Long.toBinaryString(SBIT));
        System.out.println("128: " + Long.toBinaryString(128L));
        System.out.println("127: " + Long.toBinaryString(127L));
        System.out.println("126: " + Long.toBinaryString(126L));
        System.out.println("255: " + Long.toBinaryString(255L));
    }

    void writeState() {
        long stamp = s.writeLock();
        try {
            state = 10;
        } finally {
            s.unlock(stamp);
        }
    }

    void readState() {
        long stamp = s.tryOptimisticRead();     // 尝试乐观读取
        int st = state;
        if (!s.validate(stamp)) {               // 读取数据期间stamp没有被修改，则乐观读取成功
            stamp = s.readLock();
            try {
                st = state;
            } finally {
                s.unlock(stamp);
            }
        }
        System.out.println(st);
    }
}
