package z.learn.concurrent;

import java.util.concurrent.*;

/**
 * 类似于生产者消费者
 */
public class CompletionServiceExample {

    private final ExecutorService executorService;

    public CompletionServiceExample(ExecutorService executorService) {
        this.executorService = executorService;
        if (this.executorService instanceof ThreadPoolExecutor)
            ((ThreadPoolExecutor) this.executorService).prestartAllCoreThreads();
    }

    public void example() {
        int threadCount = 10;

        CompletionService<String> completionService = new ExecutorCompletionService<>(this.executorService);

        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threadCount; i++) {
            final String jobName = "job" + i;
            completionService.submit(() -> SystemOutTask(jobName));
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                Future<String> result = completionService.take();
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private String SystemOutTask(String jobName) {
        System.out.println(Thread.currentThread().getName() + " " + jobName + " :startted");
        long slept = Math.round(Math.random() * 10 * 1000);
        try {
            Thread.sleep(slept);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName() + " " + jobName + " :ended, slept: " + slept;
    }
}
