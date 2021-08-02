package DictionaryNew;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class DictionaryNew{
    private Map<String, String> map = new HashMap<String, String>();

    public void readFromFile(String filePath) throws FileNotFoundException, IOException{
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try (reader) {
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(":", 2);
                if (parts.length >= 2 && isRuleFulfilled(parts[0]))
                {
                    String key = parts[0];
                    String value = parts[1];
                    map.put(key, value);
                } else {
                    System.err.println("ignoring line: " + line);
                }
            }
        }

    };
    public void delete(String key){
        map.remove(key);
    };

    public String find(String key){
        return map.get(key);
    };

    public void add(String key, String value){
        if (isRuleFulfilled(key)){
            map.put(key, value);
        } else {
            System.err.println(key +" doesn't suite the rule");
        }
    };

    public Map<String, String> getMap() {
        return map;
    }

    abstract boolean isRuleFulfilled(String s);

}
