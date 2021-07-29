import DictionaryNew.*;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.lang.invoke.SwitchPoint;
import java.util.Map;
import java.util.regex.Pattern;

public class mmtr {
    public static void main(String[] args) {
        DictionaryNew[] dictionaries = new DictionaryNew[2];

        dictionaries[0] = new Dictionary1();
        try {
            dictionaries[0].readFromFile("dic1.txt");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        dictionaries[1] = new Dictionary2();
        try {
            dictionaries[1].readFromFile("dic2.txt");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("To select a dictionary enter /dic 'DictionaryId'");
        Integer dictionaryId = 0;
        for (;;){


            try {
                String line = in.readLine();

                if (line.charAt(0) == '/'){
                    line = line.substring(1);
                    String[] strings = line.split(" ");
                    switch (strings[0]){
                        case "add":{
                            dictionaries[dictionaryId].add(strings[1],strings[2]);
                            break;
                        }
                        case "delete":{
                            dictionaries[dictionaryId].delete(strings[1]);
                            break;
                        }
                        case "show":{
                            Map<String, String> map = dictionaries[dictionaryId].getMap();
                            for (String key: map.keySet()){
                                System.out.println(key+" "+map.get(key));
                            }
                            break;
                        }
                        case ("dic"):{
                            if (Integer.parseInt(strings[1])<=dictionaries.length){
                                dictionaryId = Integer.parseInt(strings[1]) - 1;
                            } else {
                                System.err.println("Unknown dictionary Id");
                            }
                            break;
                        }
                        case ("find"):{
                            System.out.println(dictionaries[dictionaryId].find(strings[1]));
                        }
                    }
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
            }



        }

    }
}
