package z.learn.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    public void exmaple() throws ExecutionException, InterruptedException {
        runAsync();
        thenApply();
        thenAccept();
        whenComplete();
    }

    private void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() -> System.out.println("test")).get();
    }

    private void thenApply() {
        String str = CompletableFuture.supplyAsync(() -> "thenApply hello")  // 对象不能引用类方法
                .thenApply(s -> s + " world").join();
        System.out.println(str);
    }

    private void thenAccept() {
        CompletableFuture.supplyAsync(() -> "thenAccept hello world").thenAccept(s -> System.out.println(s));
    }

    private void whenComplete() {   // 异步的
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, ex) -> System.out.println("whenComplete finished"));
    }
}
