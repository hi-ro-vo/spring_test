package ru.test.dictionarynew;

public class Dictionary2 extends AbstractDictionary {
    @Override
    boolean isRuleFulfilled(String s) {
        return s.matches("^[0-9]{5}$");
    }
}
