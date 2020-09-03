package async;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, FileNotFoundException, ExecutionException, InterruptedException {

        JsonReader jsonReader = new JsonReader();
//        List<String> jsonArrays = jsonReader.completableFutureAllof(List.of(
//                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source.json",
//                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source2.json"
//        ));
        jsonReader.test();
    }
}


class JsonReader {
    private final JSONParser jsonParser = new JSONParser();


    public void test() throws ExecutionException, InterruptedException {
        long l = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CompletableFuture<String> future1
                    = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Hello";
            });
            futures.add(future1);
        }

        String combined = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        System.out.println(System.currentTimeMillis() - l);
        System.out.println(combined);

    }

    public CompletableFuture<JSONArray> readJson(String path) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (JSONArray) jsonParser.parse(new FileReader(path));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public List<String> completableFutureAllof(List<String> paths) {
        List<CompletableFuture<JSONArray>> list = new ArrayList<>();
        paths.forEach(path -> {
            list.add(readJson(path));
        });

        CompletableFuture<Void> allfuture = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));//Created All of object
        CompletableFuture<List<String>> allFutureList = allfuture.thenApply(val -> {
            return list.stream().map(f -> f.join()).map(JSONArray::toString).collect(Collectors.toList());
        });

        List<String> join = allFutureList.join();
        return join;
    }
}