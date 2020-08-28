package synchronizer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class Run {
    @SneakyThrows
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        Thread t1 = new Thread(new Task(latch, 1));
        Thread t2 = new Thread(new Task(latch, 3));
        Thread t3 = new Thread(new Task(latch, 2));


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}


@AllArgsConstructor
class Task extends Thread {

    private CountDownLatch latch;
    private int id;

    @SneakyThrows
    @Override
    public void run() {
        latch.countDown();
        Thread.sleep(2000);
        System.out.println("Task " + id + " is await");
        latch.await();
        Thread.sleep(1000);
        System.out.println("Task " + id + " is done");
    }


}