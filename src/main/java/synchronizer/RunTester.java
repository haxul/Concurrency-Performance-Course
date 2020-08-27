package synchronizer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class RunTester {
    public static void main(String[] args) {
        PriorityBlockingQueue<Tasker> queue = new PriorityBlockingQueue<>();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    int random = new Random().nextInt(100);
                    queue.add(new Tasker(random));
                    System.out.println(random + " Task add in queue" + " size -> " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    int random = new Random().nextInt(100);
                    queue.add(new Tasker(random));
                    System.out.println(random + " Task add in queue" + " size -> " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                    Tasker poll = queue.take();
                    System.out.println(poll.getId() + " Task is pulled from queue" + "Size -> " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


@AllArgsConstructor
@Getter
class Tasker implements Comparable<Tasker> {

    private int id;

    @Override
    public int compareTo(Tasker tasker) {
        return Integer.compare(id, tasker.id);
    }
}




