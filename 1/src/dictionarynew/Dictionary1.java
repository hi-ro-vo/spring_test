package dictionarynew;

public class Dictionary1 extends DictionaryNew{

    @Override
    boolean isRuleFulfilled(String s) {
        return  s.matches("^[a-zA-Z]{4}$");
    }
}
