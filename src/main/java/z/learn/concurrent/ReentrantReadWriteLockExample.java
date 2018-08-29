package z.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = lock.readLock();
    private ReentrantReadWriteLock.WriteLock w = lock.writeLock();
    private CountDownLatch latch;


    public void example() {
        try {
            readShared();
            blockExample2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void readShared() throws InterruptedException {
        latch = new CountDownLatch(3);
        new Thread(() -> read(), "Read1_").start();
        new Thread(() -> read(), "Read2_").start();
        new Thread(() -> read(), "Read3_").start();    // 共享锁 没有独占阻拦，直接就可以获取到
        latch.await();
    }

    private void blockedByWrite() throws InterruptedException {
        latch = new CountDownLatch(2);
        new Thread(() -> write(), "Write1").start();
        Thread.sleep(1000);
        new Thread(() -> read(), "Read1").start();
        latch.await();
    }

    private void blockExample2() throws InterruptedException {
        latch = new CountDownLatch(4);
        new Thread(() -> read(), "Read1_").start();
        new Thread(() -> read(), "Read2_").start();     // 读锁先被获取到，write被阻塞， write是队首，会阻塞后续的读线程
        Thread.sleep(1000);
        new Thread(() -> write(), "Write1_").start();   // 读取会被 write block，都是获取读锁的时候都能获取到
        Thread.sleep(1000);
        new Thread(() -> read(), "Read3_").start();
        latch.await();
    }

    private void write() {
        w.lock();
        try {
            for (int i = 0; i < 60 * 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            w.unlock();
            latch.countDown();
        }
    }

    private void read() {
        r.lock();
        try {
            for (int i = 0; i < 60 * 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            r.unlock();
            latch.countDown();
        }
    }
}
