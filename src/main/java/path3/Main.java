package path3;

import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Deposit deposit = new Deposit();
        Object lock = new Object();
        Thread thread = new Thread(new Task(deposit, lock));
        Thread thread1 = new Thread(new Task(deposit, lock));

        thread1.start();
        thread.start();
        thread.join();
        thread1.join();

        ThreadLocal<Integer> threadLocal1 = new ThreadLocal<>();
        ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
        threadLocal1.set(100);
        threadLocal2.set(200);
        System.out.println(threadLocal1.get());
        System.out.println(threadLocal2.get());

        Thread t3 = new Thread(() -> {
            System.out.println(threadLocal1.get());

        });
        t3.start();
        t3.join();

    }
}

class Deposit {
    public long all = 0;
}

class Task implements Runnable {

    public Task(Deposit deposit, Object lock) {
        this.deposit = deposit;
        this.lock = lock;
    }

    Object lock;
    Deposit deposit;

    @Override
    public void run() {
        increment();
    }

    public void increment() {
        synchronized (lock) {
            for (int i = 0; i < 10000; i++) {
                deposit.all++;
            }
        }
    }
}