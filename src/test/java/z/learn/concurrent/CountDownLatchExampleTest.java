package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * CountDownLatchExample Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 5, 2018</pre>
 */
public class CountDownLatchExampleTest {

    @Before
    public void before() throws Exception {
        example = new CountDownLatchExample();
    }

    @Test
    public void testExample() throws Exception {
        example.example(10);
    }

    private CountDownLatchExample example;
} 
