package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * CompletableFutureExample Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 5, 2018</pre>
 */
public class CompletableFutureExampleTest {

    @Before
    public void before() throws Exception {
        example = new CompletableFutureExample();
    }

    @Test
    public void testExmaple() throws Exception {
        example.exmaple();
        Thread.sleep(10 * 1000);
    }

    private CompletableFutureExample example;
} 
