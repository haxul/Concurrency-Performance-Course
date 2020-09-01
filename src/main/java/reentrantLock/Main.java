package reentrantLock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

    }
}

class Account {
    private final AutoLock lock = new AutoLock(new ReentrantLock());
    private final StampedLock sl = new StampedLock();
    private long balance;
    private long counter;

    public void add() throws Exception {
        try (AutoLock autoLock = lock.lock()) {
            balance++;
        }
    }

    private void counterWriter() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long stamp = sl.writeLock(); // выставляем блокировку на запись
                try {
                    long tmp = counter;
                    System.out.println("start counter modification:" + tmp);
                    Thread.sleep(10_000);
                    tmp++;
                    counter = tmp; // изменяем общую переменную.
                    System.out.println("end counter modification:" + tmp);
                } finally {
                    sl.unlockWrite(stamp); //снимаем блокировку на запись
                }
                Thread.sleep(30_000);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void counterReader(int id) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long stamp = sl.tryOptimisticRead(); // берем метку состояния
                long tmp = counter; // читаем значение общей переменной
                if (!sl.validate(stamp)) { // проверяем метку состояния,
                    System.out.println("    id:" + id + " protected value has been changed");
                    stamp = sl.readLock(); // если состояние изменилось, ставим блокировку
                    System.out.println("    id:" + id + " new readLock");
                    try {
                        tmp = counter; // читаем данные под блокировкой
                    } finally {
                        sl.unlockRead(stamp); // снимаем блокировку
                    }
                }
                System.out.println("    id:" + id + " current value:" + tmp);
                Thread.sleep(1_000);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}

class AutoLock implements AutoCloseable {

    private final Lock lock;

    public AutoLock(Lock lock) {
        this.lock = lock;
    }

    public AutoLock lock() {
        lock.lock();
        return this;
    }

    @Override
    public void close() throws Exception {
        lock.unlock();
    }
}