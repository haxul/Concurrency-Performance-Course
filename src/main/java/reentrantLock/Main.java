package reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");
    }
}

class Account {
    private final AutoLock lock = new AutoLock(new ReentrantLock());

    private long balance;

    public void add() throws Exception {
        try (AutoLock autoLock = lock.lock()) {
            balance++;
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