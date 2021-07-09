package com.example.second;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class SecondApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyContextConfig.class);
        Dictionaries dictionaries = ctx.getBean(Dictionaries.class);

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
                            dictionaries.getDictionary(dictionaryId).add(strings[1],strings[2]);
                            break;
                        }
                        case "delete":{
                            dictionaries.getDictionary(dictionaryId).delete(strings[1]);
                            break;
                        }
                        case "show":{
                            Map<String, String> map = dictionaries.getDictionary(dictionaryId).getMap();
                            for (String key: map.keySet()){
                                System.out.println(key+" "+map.get(key));
                            }
                            break;
                        }
                        case ("dic"):{
                            if (Integer.parseInt(strings[1])<=dictionaries.length()){
                                dictionaryId = Integer.parseInt(strings[1]) - 1;
                            } else {
                                System.err.println("Unknown dictionary Id");
                            }
                            break;
                        }
                        case ("find"):{
                            System.out.println(dictionaries.getDictionary(dictionaryId).find(strings[1]));
                        }
                    }
                }
            } catch (Exception e){
                System.err.println(e.getMessage());
            }



        }
    }


}
