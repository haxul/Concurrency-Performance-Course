package executorFramework;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
*/
//
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(4000);
//                return 100;
//            } catch (InterruptedException e) {
//                return null;
//            }
//        }).thenAccept((data) -> System.out.println("work is done: " + data )).join();

// FIND NUMBERS SUM FOR INTEGER LIST
        ExecutorService tpool = Executors.newFixedThreadPool(8);
        int size = 1000;
        List<Integer> integers = IntStream.range(0, size).boxed().collect(Collectors.toList());
//        AtomicInteger total = new AtomicInteger(0);
//
//        for (int i = 0; i < size; i++) {
//            int a = integers.get(i);
//            tpool.submit(() -> total.addAndGet(a));
//        }
//
//        tpool.shutdown();
//        tpool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//        System.out.println(total.get());
//        System.exit(0);

// USING CompletionService
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(tpool);
        int sum = 0;
        ArrayList<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int finalI = i;
            Future<Integer> submit = completionService.submit(() -> integers.get(finalI));
            futures.add(submit);
        }
        tpool.shutdown();

        for (int i = 0; i < futures.size(); i++) {
            sum +=  futures.get(i).get();
        }
        System.out.println(sum);
    }
}

