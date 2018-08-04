package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CompletionServiceExample Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 4, 2018</pre>
 */
public class CompletionServiceExampleTest {

    @Before
    public void before() throws Exception {
        completionServiceExample =
                new CompletionServiceExample(
                        new ThreadPoolExecutor(10, 12, 180, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5)));
        // 同时最多能接受的任务为 maximumPoolSize + BlockingQueue size
        // 12 + 5 = 17,还是有可能会被reject， 极端情况并发等于 7， core thread 处于park状态，入队之后被唤醒调度时间不确定
    }

    /**
     * Method: example()
     */
    @Test
    public void testExample() throws Exception {
        completionServiceExample.example();
    }

    private CompletionServiceExample completionServiceExample;
} 
