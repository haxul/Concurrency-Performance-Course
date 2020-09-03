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
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, FileNotFoundException, ExecutionException, InterruptedException {
        List<String> urls = List.of(
                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source.json",
                "/home/haxul/Development/cuncurrency-course/src/main/java/async/source2.json"
        );
        JsonReader jsonReader = new JsonReader();
        jsonReader.readJsonFiles(urls);
    }
}


class JsonReader {

    public JSONArray tryParseJsonArray(String url) {
        try {
            JSONParser jsonParser = new JSONParser();
            return (JSONArray) jsonParser.parse(new FileReader(url));
        } catch (Exception r) {
            return null;
        }
    }

    public JSONArray tryParseJsonArray(CompletableFuture<JSONArray> future) {
        try {
            return future.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            return null;
        }
    }

    public void readJsonFiles(List<String> urls) {

        List<CompletableFuture<JSONArray>> futures = urls.stream()
                .map((url) -> CompletableFuture.supplyAsync(()-> tryParseJsonArray(url)))
                .collect(Collectors.toList());

        List<JSONArray> collect = futures.stream()
                .map(this::tryParseJsonArray)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

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