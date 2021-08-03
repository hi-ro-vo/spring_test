import dictionarynew.Dictionary1;
import dictionarynew.Dictionary2;
import dictionarynew.DictionaryNew;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class Mmtr {

    static DictionaryNew[] getDictionarys(){
        DictionaryNew[] dictionaries = new DictionaryNew[2];


        dictionaries[0] = new Dictionary1();
        try {
            dictionaries[0].readFromFile("dic1.txt");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        dictionaries[1] = new Dictionary2();
        try {
            dictionaries[1].readFromFile("dic2.txt");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return dictionaries;
    }


    public static void main(String[] args) {
        DictionaryNew[] dictionaries = getDictionarys();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("To select a dictionary enter /dic 'DictionaryId'");
        int dictionaryId = 0;

        while (true){


            try {
                String line = in.readLine();

                if (line.charAt(0) == '/') {
                    line = line.substring(1);
                    String[] strings = line.split(" ");
                    String command = strings[0];
                    switch (command) {
                        case "add": {
                            String key = strings[1];
                            String value = strings[2];
                            dictionaries[dictionaryId].add(key, value);
                            break;
                        } case "delete": {
                            dictionaries[dictionaryId].delete(strings[1]);
                            break;
                        } case "show": {
                            Map<String, String> map = dictionaries[dictionaryId].getMap();
                            for (String key : map.keySet()) {
                                System.out.println(key + " " + map.get(key));
                            }
                            break;
                        } case "dic": {
                            if (Integer.parseInt(strings[1]) <= dictionaries.length) {
                                dictionaryId = Integer.parseInt(strings[1]) - 1;
                            } else {
                                System.err.println("Unknown dictionary Id");
                            }
                            break;
                        } case "find": {
                            System.out.println(dictionaries[dictionaryId].find(strings[1]));
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }


        }

    }
}
