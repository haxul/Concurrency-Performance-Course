package costum;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<Integer>> tasks = new ArrayList<>();
        List.of(10, 20, 30, 30, 10, 2000, 3000, 4000).forEach(el-> {
            Future<Integer> submit = executorService.submit(() -> {
                Thread.sleep(el);
                return el;
            });
            tasks.add(submit);
        });

        executorService.shutdown();
        OwnItr ownItr = new OwnItr(tasks);
        while (ownItr.hasNext()) {
            System.out.println(ownItr.next());
        }
    }
}


class OwnItr implements Iterator<Integer> {

    public OwnItr(List<Future<Integer>> list) {
        this.list = Collections.synchronizedList(list);
    }

    private List<Future<Integer>> list;
    private final ReentrantLock lock = new ReentrantLock();
    private int currentIndex = 0;
    private int size = 0;

    @Override
    public boolean hasNext() {
        if (currentIndex + 1 == size) return false;
        return list.get(currentIndex + 1).isDone();
    }

    @SneakyThrows
    @Override
    public Integer next() {
        size++;
        return list.get(currentIndex++).get();
    }

    @Override
    public void remove() {
    }

    @Override
    public void forEachRemaining(Consumer<? super Integer> action) {
    }
}

