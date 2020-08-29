package Cancellation;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLOutput;

public class Main {
    @SneakyThrows
    public static void main(String[] args) throws InterruptedException {
/*
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

*/
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5454);
        serverSocket.bind(hostAddress);
        serverSocket.configureBlocking(false);
        int ops = serverSocket.validOps();
        SelectionKey selectKy = serverSocket.register(selector, ops, null);



    }
}
