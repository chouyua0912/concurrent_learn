package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * StampedLockExample Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Sep 7, 2018</pre>
 */
public class StampedLockExampleTest {

    @Before
    public void before() throws Exception {
    }

    /**
     * Method: example()
     */
    @Test
    public void testExample() throws Exception {
        example.example();
    }

    /**
     * Method: writeState()
     */
    @Test
    public void testWriteState() throws Exception {
        example.example();
    }

    /**
     * Method: readState()
     */
    @Test
    public void testReadState() throws Exception {
        example.example();
    }


    private StampedLockExample example = new StampedLockExample();
} 
