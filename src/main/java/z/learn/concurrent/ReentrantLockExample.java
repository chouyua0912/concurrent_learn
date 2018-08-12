package z.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    ReentrantLock lock = new ReentrantLock();
    CountDownLatch latch = new CountDownLatch(2);

    public void example() {
        new Thread(() -> firstRun(), "Exam-1").start();
        new Thread(() -> secondRun(), "Exam-2").start();
        //new Thread(() -> secondRun(), "Exam-3").start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void firstRun() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Started");
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Ended");
        } finally {
            lock.unlock();
        }
        latch.countDown();
    }

    private void secondRun() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Acquired Started");

            System.out.println(Thread.currentThread().getName() + " ended");
        } finally {
            lock.unlock();
        }
        latch.countDown();
    }
}
