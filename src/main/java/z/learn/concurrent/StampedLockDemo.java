package z.learn.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * 不像ReadWriteLock，StampedLocks是不可重入的。因此尽管StampedLocks可能更快，但可能产生死锁。在实践中，这意味着你应该始终确保锁以及对应的门票不要逃逸出所在的代码块。
 * http://ifeve.com/java-8-stampedlocks-vs-readwritelocks-and-synchronized/
 */
class StampedLockDemo {

    /**
     * 不可重入锁， 重入获取write，会导致自己陷入睡眠park
     */
    void unRenentrantLock() {
        Thread t = new Thread(() -> {
            StampedLock s = new StampedLock();
            long stamp1 = s.writeLock();                    // 非中断获取锁
            try {
                System.out.println(stamp1);
                long stamp2 = s.writeLockInterruptibly();   // 中断式获取
                try {
                    System.out.println(stamp2);
                } finally {
                    s.unlockWrite(stamp2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("writeLockInterruptibly exception..");
            } finally {
                s.unlockWrite(stamp1);
            }
        }, "write2");
        t.start();

        try {
            Thread.sleep(30 * 1000);
            t.interrupt();
            System.out.println("end..");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("exception..");
        }
    }

    /**
     * 读取锁一定程度上可以重入？
     */
    void readLockTwo() {
        StampedLock s = new StampedLock();
        long stamp = s.readLock();
        try {
            System.out.println("read1");
            long stamp2 = s.readLock();
            try {
                System.out.println("read2");
            } finally {
                s.unlockRead(stamp2);
            }
        } finally {
            s.unlockRead(stamp);
        }
    }

    /**
     * 检验是否可以持有超过126个读锁
     */
    void checkIfReadLockCanBeyond126() {
        StampedLock s = new StampedLock();
        doCheckIfReadLockCanBeyond126(s);
    }

    void doCheckIfReadLockCanBeyond126(StampedLock s) {
        long stamp = s.readLock();
        try {
            System.out.println(stamp);
            if (stamp < 381)                    // 382-256=126
                doCheckIfReadLockCanBeyond126(s);
            else
                System.out.println("end");   // 会一直保持382递归，直到栈溢出为止 overflow了之后会一直返回382给readLock的调用者
        } finally {
            s.unlockRead(stamp);
        }
    }


    /**
     * 也可以通过View来使用
     * 提供更易懂的接口来使用信笺锁
     */
    void StampedLockView() {
        StampedLock s = new StampedLock();
        Lock read = s.asReadLock();                 // 封装了readLock()，condition功能，没有乐观读功能optimistic read
        Lock write = s.asWriteLock();               // 封装了writeLock()，不支持condition
        ReadWriteLock rw = s.asReadWriteLock();
        System.out.println(read);
        System.out.println(write);
        System.out.println(rw);
    }
}
