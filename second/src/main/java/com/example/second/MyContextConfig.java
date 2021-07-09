package com.example.second;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyContextConfig {

    @Bean
    @Scope("singleton")
    public Dictionaries dictionaries(){
        Dictionaries dictionaries = new Dictionaries();
        Dictionary dictionary = new Dictionary(new Rule() {
            @Override
            public Boolean isSuitable(String key) {
                return key.matches("^[a-zA-Z]{4}$");
            }
        });

        dictionary.add("qwer", "qwert");
        try {
            dictionary.readFromFile("src/main/resources/dic1.txt");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        dictionaries.addDictionary(dictionary);

        dictionary = new Dictionary(new Rule() {
            @Override
            public Boolean isSuitable(String key) {
                return key.matches("^[0-9]{5}$");
            }
        });

        try {
            dictionary.readFromFile("src/main/resources/dic2.txt");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        dictionaries.addDictionary(dictionary);

        return dictionaries;
    }
}
