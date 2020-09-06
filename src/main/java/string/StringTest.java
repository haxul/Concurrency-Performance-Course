package string;

public class StringTest {
    public static void main(String[] args) {
        long start = System.nanoTime();

        String init = new String("helloworld");

        init += "hello orld";
        System.out.println(init);
        System.out.println((System.nanoTime() - start) / 1000);

        long start2 = System.nanoTime();
        String init2 = new String("helloworld");
        StringBuilder builder = new StringBuilder(init);
        String hello_world = builder.append("hello world").toString();
        System.out.println(hello_world);
        System.out.println((System.nanoTime() - start2) / 1000);

    }
}
