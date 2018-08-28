package z.learn.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 让一组线程互相等待到达一个点之后再一起开始执行
 * 一般是最慢的那个线程去触发tripped操作
 */
public class CyclicBarrierExample {

    CountDownLatch latch = new CountDownLatch(3);
    private CyclicBarrier barrier = new CyclicBarrier(3, () -> tripped());

    public void example() {
        new Thread(() -> threadOne(), "one").start();
        new Thread(() -> threadTwo(), "two").start();
        new Thread(() -> threadThird(), "third").start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void threadOne() {
        System.out.println(Thread.currentThread().getName() + " I'm coming..");
        try {
            Thread.sleep(10 * 1000);
            System.out.println(Thread.currentThread().getName() + " index=" + barrier.await());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " I'm finished..");
        latch.countDown();
    }

    private void threadTwo() {
        System.out.println(Thread.currentThread().getName() + " I'm coming..");
        try {
            Thread.sleep(3 * 1000);
            System.out.println(Thread.currentThread().getName() + " index=" + barrier.await());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " I'm finished..");
        latch.countDown();
    }

    private void threadThird() {
        System.out.println(Thread.currentThread().getName() + " I'm coming..");
        try {
            Thread.sleep(Math.round(Math.random() * 10000));
            System.out.println(Thread.currentThread().getName() + " index=" + barrier.await());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " I'm finished..");
        latch.countDown();
    }

    private void tripped() {
        System.out.println(Thread.currentThread().getName() + " Everyone is tripped..");
    }
}
