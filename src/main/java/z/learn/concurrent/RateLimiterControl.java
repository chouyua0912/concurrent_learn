package z.learn.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class RateLimiterControl {

    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(0.5);
        CyclicBarrier barrier = new CyclicBarrier(10);

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    barrier.await();

                    int j = 0;
                    while (true) {
                        if (limiter.tryAcquire(10, TimeUnit.SECONDS)) {
                            System.out.println(Thread.currentThread().getName() + " acquired doing biz");
                            j = 0;
                        } else {
                            j++;
                            System.out.println(Thread.currentThread().getName() + " j++, j=" + j);
                        }
                        if (j > 10) break;
                    }
                    System.out.println(Thread.currentThread().getName() + " died.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
            t.start();
        }
    }
}
