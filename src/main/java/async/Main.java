package async;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, FileNotFoundException, ExecutionException, InterruptedException {

        JsonReader jsonReader = new JsonReader();
//        List<String> jsonArrays = jsonReader.completableFutureAllof(List.of(
//                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source.json",
//                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source2.json"
//        ));
        jsonReader.tester();
    }
}


class JsonReader {

    public void tester() {
        List<String> urls = List.of(
                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source.json",
                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source2.json"
        );

        List<CompletableFuture<JSONArray>> futures = new ArrayList<>();
        for (var url : urls) {
            CompletableFuture<JSONArray> future = CompletableFuture.supplyAsync(() -> {
                try {
                    if (url.equals( "/home/haxul/Development/cuncurrency-course/src/main/java/async/source2.json")) Thread.sleep(3000);
                    JSONParser jsonParser = new JSONParser();
                    return (JSONArray) jsonParser.parse(new FileReader(url));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    return null;
                }
            });
            futures.add(future);
        }
        List<JSONArray> collect = futures.stream().map(future -> {
            try {
                return future.get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        List<Long> result = new ArrayList<>();
        for (JSONArray jsonArray : collect) {
           for (Object item : jsonArray) {
               JSONObject el = (JSONObject) item;
               Long id = (Long) el.get("id");
               result.add(id);
           }
        }
        result.sort((Long::compareTo));
        System.out.println(result);
    }

}