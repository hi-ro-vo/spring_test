package DictionaryNew;

public class Dictionary2 extends DictionaryNew{
    @Override
    boolean isRuleFulfilled(String s) {
        return s.matches("^[0-9]{5}$");
    }
}
