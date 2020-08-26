package thredSafeClass;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            Date parse = new DateFormatter().parse("2020-01-01");
            System.out.println(parse);
        });
        Thread thread1 = new Thread(() -> {
            Date parse = new DateFormatter().parse("2020-02-01");
            System.out.println(parse);
        });

        thread1.start();
        thread.start();
    }
}



class DateFormatter {

    public String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    @SneakyThrows
    public Date parse(String date) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}


/*
class DateFormatter {
    private static ThreadLocal<DateFormat> local = new ThreadLocal<>();

    public DateFormatter() {
        local.set(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public String format(Date date) {
        var df = local.get();
        return df.format(date);
    }

    @SneakyThrows
    public Date parse(String date) {
        var df = local.get();
        return df.parse(date);
    }
}
*/