package z.learn.concurrent;

import org.junit.Test;
import org.junit.Before;

/**
 * RWLockDictionary Tester.
 *
 * @author chouyua
 * @version 1.0
 * @since <pre>Aug 29, 2018</pre>
 */
public class RWLockDictionaryTest {

    @Before
    public void before() throws Exception {
    }

    /**
     * Method: put(String key, String value)
     */
    @Test
    public void testPut() throws Exception {
        example.put("test", "bbb");
    }

    /**
     * Method: get(String key)
     */
    @Test
    public void testGet() throws Exception {
        example.put("test", "bbb");
        assert "bbb".equals(example.get("test"));
    }


    private RWLockDictionary example = new RWLockDictionary();
} 
