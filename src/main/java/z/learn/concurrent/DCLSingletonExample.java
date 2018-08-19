package z.learn.concurrent;

public class DCLSingletonExample {
    private volatile static DCLSingletonExample instance;

    private DCLSingletonExample() {
    }

    public static DCLSingletonExample getInstance() {
        if (instance == null) {         // 避免初始化之后还要每次获取锁检查
            synchronized (DCLSingletonExample.class) {
                if (instance == null)
                    instance = new DCLSingletonExample();
            }
        }
        return instance;
    }
}
