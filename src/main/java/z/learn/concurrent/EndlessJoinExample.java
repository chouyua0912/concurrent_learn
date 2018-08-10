package z.learn.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 死锁需要两把锁，互相持有的状态下获取对方的锁
 */
public class EndlessJoinExample {

    private ReentrantLock lock = new ReentrantLock();

    public void example() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("slept");
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lock.lock();
        try {

            Thread t = new Thread(() -> {
                lock.lock();
                try {
                    System.out.println("I'm in");
                } finally {
                    lock.unlock();
                }

            }, "thread-2");
            t.start();

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Joined");
        } finally {
            lock.unlock();
        }
    }
}
