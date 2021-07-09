package com.example.second;

import java.util.ArrayList;

public class Dictionaries {
    private ArrayList<Dictionary> arr = new ArrayList<>();

    public Dictionary getDictionary(Integer Id){
        return arr.get(Id);
    }

    public void addDictionary(Dictionary dictionary){
        arr.add(dictionary);
    }

    public int length(){
        return arr.size();
    }
}
