package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * StreamExample Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 7, 2018</pre>
 */
public class StreamExampleTest {

    @Before
    public void before() throws Exception {
        example = new StreamExample();
    }

    @Test
    public void testExample() throws Exception {
        example.example();

        Thread.sleep(10 * 1000);
    }

    private StreamExample example;
} 
