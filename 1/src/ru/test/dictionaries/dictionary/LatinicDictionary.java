package ru.test.dictionaries.dictionary;

public class LatinicDictionary extends AbstractDictionary {

    @Override
    public boolean isRuleFulfilled(String s) {
        return s.matches("^[a-zA-Z]{4}$");
    }
}
