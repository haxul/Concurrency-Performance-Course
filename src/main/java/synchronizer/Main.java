package synchronizer;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        Thread t1 = new Job(semaphore, 1);
        Thread t2 = new Job(semaphore, 2);
        Thread t3 = new Job(semaphore, 3);
        Thread t4 = new Job(semaphore, 4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.println("end");
    }
}

class Job extends Thread {
    private final int id;
    private final Semaphore semaphore;

    public Job(Semaphore semaphore, int id) {
        this.semaphore = semaphore;
        this.id = id;
    }


    @SneakyThrows
    public synchronized void makeJob() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println("id: " + id + " -> Work is done");
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void run() {
        makeJob();
    }
}