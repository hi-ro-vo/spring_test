package ru.test.dictionaries.dictionary;

public class Dictionary2 extends AbstractDictionary {

    @Override
    public boolean isRuleFulfilled(String s) {
        return s.matches("^[0-9]{5}$");
    }
}
