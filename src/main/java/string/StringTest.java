package string;

public class StringTest {
    public static void main(String[] args) {


        long start2 = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        String hello_world = builder.append("helloworld").append("hello world").toString();
        System.out.println(hello_world);
        System.out.println((System.nanoTime() - start2) / 1000);


        long start = System.nanoTime();
        String init = new String("helloworld");

        init += "hello orld";
        System.out.println(init);
        System.out.println((System.nanoTime() - start) / 1000);

    }
}
