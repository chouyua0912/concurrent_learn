package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * ForkJoinRecursiveExample Tester.
 * <p>
 * 适合N^2 类型 优化为 Log2N 的算法？ 比如排序累算法
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Aug 5, 2018</pre>
 */
public class ForkJoinRecursiveExampleTest {

    @Before
    public void before() throws Exception {
        example = new ForkJoinRecursiveExample();
    }

    @Test
    public void testExample() throws Exception {
        int limit = 300000;
        example.example(limit);

        long start = System.currentTimeMillis();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            msg.append(i).append(",");
        }
        // System.out.println(msg.substring(0, msg.length() - 1));
        System.out.println("Time spend :" + (System.currentTimeMillis() - start));
    }

    private ForkJoinRecursiveExample example;

} 
