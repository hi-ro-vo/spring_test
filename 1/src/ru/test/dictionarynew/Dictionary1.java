package ru.test.dictionarynew;

public class Dictionary1 extends AbstractDictionary {

    @Override
    public boolean isRuleFulfilled(String s) {
        return  s.matches("^[a-zA-Z]{4}$");
    }
}