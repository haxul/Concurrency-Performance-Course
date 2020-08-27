package synchronizer;

import lombok.SneakyThrows;

import java.util.concurrent.Phaser;

public class Programm {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        Thread t1 = new Thread(new Phase(phaser, 1));
        Thread t2 = new Thread(new Phase(phaser, 2));
        Thread t3 = new Thread(new Phase(phaser, 3));


        t1.start();
        t2.start();
        t3.start();
        phaser.arriveAndDeregister();
        System.out.println("end");
    }
}


class Phase implements Runnable {
    private Phaser phaser;
    private int id;
    public Phase (Phaser phaser, int id) {
        this.id = id;
        this.phaser = phaser;
        this.phaser.register();
    }
    @Override
    @SneakyThrows
    public void run() {
        Thread.sleep(2000);
        System.out.println("phase " + id + " is made");
        phaser.arriveAndAwaitAdvance();
    }
}
