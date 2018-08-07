package z.learn.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StreamExample {

    public void example() {

        List<String> strs = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg");

        strs.stream().forEach(str -> CompletableFuture.runAsync(() -> System.out.println(str)).join());
        System.out.println("-----------------------------");
        strs.parallelStream().forEach(str -> CompletableFuture.runAsync(() -> System.out.println(str)));
    }
}
