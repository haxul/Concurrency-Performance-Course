package Cancellation;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
                return;
            }
//            for (int i = 0; i < 1_000_000_000; i++) {
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("Thread was interrupted");
//                    return;
//                }
//                Math.sin(i);
//            }
        });
        t.start();
        Thread.sleep(5000);

        t.interrupt();
        t.join();




    }
}
