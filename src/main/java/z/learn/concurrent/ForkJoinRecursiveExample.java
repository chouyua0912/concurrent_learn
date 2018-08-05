package z.learn.concurrent;

import java.util.concurrent.*;

/**
 * 方便执行递归类任务
 */
public class ForkJoinRecursiveExample {

    public void example(int number) throws InterruptedException {
        PrintTask task = new PrintTask(0, number);

        long start = System.currentTimeMillis();
        ForkJoinTask<String> ret = ForkJoinPool.commonPool().submit(task);
        try {
            System.out.println(ret.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("time spend on fork join :" + (System.currentTimeMillis() - start));
    }

    class PrintTask extends RecursiveTask<String> {

        private static final int THRESHOLD = 50; //最多只能打印50个数
        private int start;
        private int end;

        public PrintTask(int start, int end) {
            super();
            this.start = start;
            this.end = end;
        }

        @Override
        protected String compute() {
            if (end - start < THRESHOLD) {
                StringBuilder msg = new StringBuilder();
                for (int i = start; i < end; i++) {
                    msg.append(i).append(",");
                }
                return msg.substring(0, msg.length() - 1);
            } else {
                int middle = (start + end) / 2;
                PrintTask left = new PrintTask(start, middle);
                PrintTask right = new PrintTask(middle, end);
                //并行执行两个“小任务”
                ForkJoinTask<String> leftTask = left.fork();    // 重新提交自己作为任务
                ForkJoinTask<String> rightTask = right.fork();
                try {
                    return leftTask.get() + "," + rightTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
