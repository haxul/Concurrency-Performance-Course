package safeCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        ConcurrentHashMap<Integer, Integer> cache = new ConcurrentHashMap<>();
        cache.computeIfAbsent(123, (k) -> {
            return 1000;
        });

    }
}
