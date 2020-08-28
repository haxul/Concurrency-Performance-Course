package executorFramework;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
/*
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello world");
            }
        }, 1000, 1000);


        AtomicInteger x = new AtomicInteger();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(x::getAndIncrement, 0, 1, TimeUnit.SECONDS);
        while (true) {
            Thread.sleep(1000);
            if (x.get() == 5) return;
            System.out.println(x.get());
        }
        // scheduledExecutorService.schedule(() -> System.out.println("task 2 is done"), 5, TimeUnit.SECONDS);
        // System.out.println("task 3 is done");
        // scheduledExecutorService.shutdown();
*/
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(executorService);
        Future<String> submit = executorCompletionService.submit(() -> {
            Thread.sleep(5000);
            return "Hello world";
        });
        Future<String> submit2 = executorCompletionService.submit(() -> {
            Thread.sleep(5000);
            return "Hello world2";
        });
        executorService.shutdown();

        String s = submit.get();
        System.out.println(s);
        String s1 = submit2.get();
        System.out.println(s1);

    }
}


