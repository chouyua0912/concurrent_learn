package z.learn.concurrent;

import java.util.concurrent.Semaphore;

class SemaphorePermits {

    private Semaphore semaphore = new Semaphore(10);

    void example() {
        semaphore.release();
        semaphore.release(10);      // 实际初始化设置进去的值是没有最大值的限制的，如果要实现流量控制，还是需要定时放入信号才行

        if (semaphore.tryAcquire()) {
            System.out.println("hello");
        }
    }
}
