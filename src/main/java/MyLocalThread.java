import lombok.SneakyThrows;

import java.util.HashMap;

class MyLocalThread {

    public MyLocalThread() {
        storage = new HashMap<>();
    }

    private HashMap<Thread, String> storage;

    public void set(String value) {
        storage.put(Thread.currentThread(), value);

    }

    public String get() {
        return storage.get(Thread.currentThread());
    }

    public void remove() {
        storage.remove(Thread.currentThread());
        if (storage.isEmpty()) storage = null;
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