package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * EndlessJoinExample Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Aug 10, 2018</pre>
 */
public class EndlessJoinExampleTest {

    @Before
    public void before() throws Exception {
        example = new EndlessJoinExample();
    }

    @Test
    public void testExample() throws Exception {
        example.example();
    }

    private EndlessJoinExample example;
} 
