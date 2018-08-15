package z.learn.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockConditionExample {

    ReentrantLock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    LinkedList<Integer> strs = new LinkedList<>();
    int count = 0;
    final int maxCount = 10;


    public void example() throws InterruptedException {
        Thread consumer = new Thread(() -> consume(), "Consumer");
        Thread consumer2 = new Thread(() -> consume(), "Consumer2");

        Thread producer1 = new Thread(() -> produce(), "Producer1");
        Thread producer2 = new Thread(() -> produce(), "Producer2");
        Thread producer3 = new Thread(() -> produce(), "Producer3");
        consumer.start();
        consumer2.start();

        producer1.start();
        producer2.start();
        producer3.start();


        Thread.sleep(10 * 60 * 1000);
        consumer.interrupt();
        consumer2.interrupt();

        producer1.interrupt();
        producer2.interrupt();
        producer3.interrupt();
    }

    private void consume() {
        List<Integer> list = new LinkedList();
        while (true) {
            lock.lock();
            try {
                while (count == 0)
                    notEmpty.await();

                list.add(strs.removeFirst());
                count--;
                notFull.signal();
                if (Thread.interrupted()) break;
            } catch (InterruptedException e) {
                break;
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Consumed:" + list.size());
    }

    private void produce() {
        int i = 0;
        while (true) {
            lock.lock();
            try {
                while (count == maxCount)
                    notFull.await();

                strs.add(i++);
                count++;

                notEmpty.signal();
                if (Thread.interrupted()) break;
            } catch (InterruptedException e) {
                break;
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Produced:" + i);
    }
}
