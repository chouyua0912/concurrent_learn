package z.learn.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证Condition队列，传递信号时候并不唤醒线程
 */
public class ReentrantLockConditionSignalExample {
    ReentrantLock lock;
    Condition condition;
    volatile boolean conditionMet = false;
    CountDownLatch latch = new CountDownLatch(2);

    public ReentrantLockConditionSignalExample() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void example() throws InterruptedException {
        new Thread(() -> waitCondition(), "Wait-Thread").start();
        Thread.sleep(10 * 1000);
        new Thread(() -> signalCondition(), "Signal-Thread").start();
        latch.await();
    }

    private void waitCondition() {
        lock.lock();
        try {
            while (!conditionMet)
                condition.await();

            System.out.println("Wait condition met..");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void signalCondition() {
        lock.lock();
        try {
            conditionMet = true;
            condition.signal();
            System.out.println("Signal condition ..");
            latch.countDown();
        } finally {
            lock.unlock();
        }
    }
}
