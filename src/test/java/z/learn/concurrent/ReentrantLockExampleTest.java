package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * ReentrantLockExample Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Aug 12, 2018</pre>
 */
public class ReentrantLockExampleTest {

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

    private ReentrantLockExample example = new ReentrantLockExample();
} 
