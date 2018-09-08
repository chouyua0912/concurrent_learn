package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * StampedLockDemo Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Sep 7, 2018</pre>
 */
public class StampedLockDemoTest {

    @Before
    public void before() throws Exception {
    }

    /**
     * Method: exmaple()
     */
    @Test
    public void testURenentrantLock() throws Exception {
        example.unRenentrantLock();
    }

    @Test
    public void testStampedLockView() {
        example.StampedLockView();
    }

    @Test
    public void testReadLockTwo() {
        example.readLockTwo();
    }

    @Test
    public void testCheckIfReadLockCanBeyond126() {
        example.checkIfReadLockCanBeyond126();
    }


    private StampedLockDemo example = new StampedLockDemo();
} 
