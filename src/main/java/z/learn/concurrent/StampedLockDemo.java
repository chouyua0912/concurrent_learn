package z.learn.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.StampedLock;

class StampedLockDemo {

    /**
     * 不可重入锁， 重入获取write，会导致自己陷入睡眠park
     */
    void unRenentrantLock() {
        Thread t = new Thread(() -> {
            StampedLock s = new StampedLock();
            long stamp1 = s.writeLock();                    // 非中断获取锁
            try {
                System.out.println(stamp1);
                long stamp2 = s.writeLockInterruptibly();   // 中断式获取
                try {
                    System.out.println(stamp2);
                } finally {
                    s.unlockWrite(stamp2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("writeLockInterruptibly exception..");
            } finally {
                s.unlockWrite(stamp1);
            }
        }, "write2");
        t.start();

        try {
            Thread.sleep(30 * 1000);
            t.interrupt();
            System.out.println("end..");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception..");
        }
    }

    /**
     * 也可以通过View来使用
     * 提供更易懂的接口来使用信笺锁
     */
    void StampedLockView() {
        StampedLock s = new StampedLock();
        Lock read = s.asReadLock();
        Lock write = s.asWriteLock();
        ReadWriteLock rw = s.asReadWriteLock();
        System.out.println(read);
        System.out.println(write);
        System.out.println(rw);
    }
}
