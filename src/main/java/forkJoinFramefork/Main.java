package forkJoinFramefork;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Task task = new Task(List.of(1L, 2L, 3L, 4L));
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Long invoke = forkJoinPool.invoke(task);
        System.out.println(invoke);
    }

    // SINGLE THREAD
    public static long sumList(List<Integer> list) {
        if (list.size() == 1) return list.get(0);
        int middle = list.size() / 2;
        return sumList(list.stream().limit(middle).collect(Collectors.toList())) +
                sumList(list.stream().skip(middle).collect(Collectors.toList()));
    }
}
// MANY THREAD
class Task extends RecursiveTask<Long> {
    public List<Long> list;

    public Task(List<Long> list) {
        this.list = list;
    }

    @Override
    protected Long compute() {
        if (list.size() == 1) return list.get(0);
        int middle = list.size() / 2;
        Task firstPath = new Task(list.stream().limit(middle).collect(Collectors.toList()));
        firstPath.fork();
        Task secondPath = new Task(list.stream().skip(middle).collect(Collectors.toList()));
        secondPath.fork();
        Long join = firstPath.join();
        Long join1 = secondPath.join();
        return join + join1;
    }
}