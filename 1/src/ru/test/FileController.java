package ru.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileController {
    static final private  Logger logger = Logger.getLogger("ru.test.file.controller");

    public Optional<Map<String, String>> readFromFile(String filePath, Predicate<String> isRuleFulfilled)
    {
        Map<String, String> result = new HashMap<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            try (reader) {
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0];
                    String value = parts[1];
                    if (parts.length == 2 && isRuleFulfilled.test(key)) {
                        result.put(key, value);
                    } else {
                        System.err.println("ignoring line: " + line);
                    }
                }
            }
        } catch (IOException e){
            logger.log(Level.FINE, "При чтении файла "+ filePath +" произошла ошибка", e);
            return Optional.empty();
        }
        return Optional.of(result);
    }

    public boolean saveToFile(String filePath, Map<String, String> map){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            //map.forEach((key, value) -> writer.write(key+":"+value));//TODO: Спросить как еще это ограничение можно обойти
            for (Map.Entry<String, String> entry: map.entrySet()){
                writer.write(entry.getKey()+":"+entry.getValue()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            logger.log(Level.FINE, "При записи в файл "+ filePath +" произошла ошибка", e);
            return false;
        }
        return true;
    }
}
