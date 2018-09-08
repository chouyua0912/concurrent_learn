package z.learn.concurrent;

import java.util.concurrent.Phaser;

/**
 * 类似于CyclicBarrier，协调一组线程同时开始一个任务
 * 不同之处可以动态的调整parties数量，更灵活的可以是否继续前进、等待等等
 */
class PhaserDemo {

    void exampleArrive() {
        int parties = 3;
        Phaser p = new Phaser(parties);
        for (int i = 0; i < parties; i++) {
            Thread t = new Thread(() -> arrive(p), "Thread-" + i);
            t.start();
        }
        try {
            Thread.sleep(10000);
            System.out.println("main exit...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void arrive(Phaser p) {
        System.out.println(Thread.currentThread().getName() + " started..");
        long sleep = Math.round(Math.random() * 10000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " slept = [ " + sleep + " ] arrived = " + p.arrive());     // arrive，不等待其他人  首先到达phase 0
        System.out.println(Thread.currentThread().getName() + " end..");
    }

    void exampleArriveAndAwaitAdvance() {
        int parties = 3;
        Phaser p = new Phaser(parties);
        for (int i = 0; i < parties; i++) {
            Thread t = new Thread(() -> arriveAndAwaitAdvance(p), "Thread-" + i);
            t.start();
        }
        try {
            Thread.sleep(10000);
            System.out.println("main exit...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * arrive和awaitAdvance
     */
    private void arriveAndAwaitAdvance(Phaser p) {
        System.out.println(Thread.currentThread().getName() + " started..");
        long sleep = Math.round(Math.random() * 10000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int phase = p.arrive();         // 报告到达指定点
        System.out.println(Thread.currentThread().getName() + " slept = [ " + sleep + " ] arrived = " + phase);     // arrive，不等待其他人
        p.awaitAdvance(phase);          // 等待其他线程到达指定点，再继续执行
        System.out.println(Thread.currentThread().getName() + " end..");
    }

    /**
     * 允许动态调整parties数量  分阶段，第一阶段需要3个线程协调工作，第二个阶段只需要2个
     */
    void exampleDeregister() {
        int parties = 3;
        Phaser p = new Phaser(parties);
        new Thread(() -> arriveDeregister(p), "Thread-1-deregister").start();
        for (int i = 0; i < parties - 1; i++) {
            new Thread(() -> arriveDeregisterAdvanceToAnotherPhase(p), "Thread-advance-" + i).start();
        }
        try {
            Thread.sleep(30000);
            System.out.println("main exit...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void arriveDeregister(Phaser p) {
        System.out.println(Thread.currentThread().getName() + " started..");
        long sleep = Math.round(Math.random() * 10000 + 10000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int phase = p.arriveAndDeregister();         // 报告到达指定点，并且解开注册，减少parties
        System.out.println(Thread.currentThread().getName() + " slept = [ " + sleep + " ] arrived = " + phase);     // arrive，不等待其他人
        System.out.println(Thread.currentThread().getName() + " end.. phase 0..");
    }

    private void arriveDeregisterAdvanceToAnotherPhase(Phaser p) {
        System.out.println(Thread.currentThread().getName() + " started..");
        long sleep = Math.round(Math.random() * 10000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int phase = p.arrive();         // 报告到达指定点
        System.out.println(Thread.currentThread().getName() + " slept = [ " + sleep + " ] arrived = " + phase);     // arrive，不等待其他人
        p.awaitAdvance(phase);          // 等待其他线程到达指定点，再继续执行
        System.out.println(Thread.currentThread().getName() + " end.. phase 0..");

        sleep = Math.round(Math.random() * 10000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        phase = p.arrive();         // 报告到达指定点
        System.out.println(Thread.currentThread().getName() + " slept = [ " + sleep + " ] arrived = " + phase);     // arrive，不等待其他人
        p.awaitAdvance(phase);          // 等待其他线程到达指定点，再继续执行
        System.out.println(Thread.currentThread().getName() + " end.. phase 1..");
    }

}
