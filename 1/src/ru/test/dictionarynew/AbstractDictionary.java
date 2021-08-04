package ru.test.dictionarynew;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractDictionary {
    private Map<String, String> map = new HashMap<>();

    public void readFromFile(String filePath) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try (reader) {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                String key = parts[0];
                String value = parts[1];
                if (parts.length == 2 && isRuleFulfilled(key)) {
                    map.put(key, value);
                } else {
                    System.err.println("ignoring line: " + line);
                }
            }
        }
    }

    public void loadFromMap(Map<String, String> map){//TODO: добавить проверку пришедшей мапы на валидность
        this.map = map;
    }

    public boolean delete(String key) {
        return map.remove(key) != null;
    }

    public Optional<String> find(String key) {
        return Optional.of(map.get(key));
    }

    public void add(String key, String value) {
        if (isRuleFulfilled(key)) {
            map.put(key, value);
        } else {
            System.err.println(key + " doesn't suite the rule");
        }
    }

    public Map<String, String> getMap() {
        return map;
    }

    abstract public boolean isRuleFulfilled(String s);

}
