package z.learn.concurrent;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 定期释放出信号量给acquire线程使用
 * 信号量的上限实际是没有控制的
 */
public class SemaphoreFlowController {

    final static int MAX_QPS = 10;
    final static Semaphore semaphore = new Semaphore(MAX_QPS);

    public static void main(String... args) throws Exception {

        Executors.newScheduledThreadPool(1)     // 要实现流量控制，需要定期释放信号
                .scheduleAtFixedRate(() -> semaphore.release(MAX_QPS / 2), 1000, 500, TimeUnit.MILLISECONDS);

        //lots of concurrent calls:100 * 1000
        ExecutorService pool = Executors.newFixedThreadPool(100);

        for (int i = 100; i > 0; i--) {

            final int x = i;

            pool.submit(() -> {
                for (int j = 1000; j > 0; j--) {
                    semaphore.acquireUninterruptibly(1);        // 一直阻塞到获取到，或者被中断
                    remoteCall(x, j);
                }

            });
        }

        pool.shutdown();

        pool.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("DONE");
    }

    private static void remoteCall(int i, int j) {
        System.out.println(String.format("%s - %s: %d %d", new Date(),
                Thread.currentThread(), i, j));
    }
}
