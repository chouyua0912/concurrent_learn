package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * PhaserDemo Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Sep 8, 2018</pre>
 */
public class PhaserDemoTest {

    @Test
    public void testExample() {
        example.exampleArrive();
    }

    @Test
    public void testArriveAwait() {
        example.exampleArriveAndAwaitAdvance();
    }

    @Test
    public void testExampleDeregister() {
        example.exampleDeregister();
    }

    private PhaserDemo example = new PhaserDemo();
} 
