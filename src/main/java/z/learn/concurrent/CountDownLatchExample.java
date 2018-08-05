package z.learn.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public void example(int num) {
        CountDownLatch latch = new CountDownLatch(num);

        for (int i = 0; i < num; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(Math.round(Math.random() * 10 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finished.");
                latch.countDown();
            }, "Job-" + i);
            t.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
