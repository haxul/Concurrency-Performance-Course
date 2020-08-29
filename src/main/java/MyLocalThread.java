import lombok.SneakyThrows;

import java.util.HashMap;

class MyLocalThread {

    public MyLocalThread() {
        storage = new HashMap<>();
    }

    private HashMap<Long, String> storage;

    public void set(String value) {
        storage.put(getThreadId(), value);
    }

    public String get() {
        return storage.get(getThreadId());
    }

    public void remove() {
        storage.remove(getThreadId());
        if (storage.isEmpty()) storage = null;
    }

    private long getThreadId() {
        return Thread.currentThread().getId();
    }
}

class Main {
    @SneakyThrows
    public static void main(String[] args) {
        MyLocalThread myLocalThread = new MyLocalThread();

        myLocalThread.set("hello");

        Thread thread = new Thread(() -> {
            myLocalThread.set("world");
            System.out.println("Optional: " + myLocalThread.get());
        });


        thread.start();
        thread.join();

        System.out.println("Main: " + myLocalThread.get());
    }
}