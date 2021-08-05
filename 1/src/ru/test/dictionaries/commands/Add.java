package ru.test.dictionaries.commands;

import java.util.Dictionary;
import java.util.function.Supplier;
import ru.test.dictionaries.dictionary.AbstractDictionary;

public class Add extends Command{

    private final Supplier<AbstractDictionary> Dictionary;

    public Add(Supplier<AbstractDictionary> getCurrentDictionary){
        Dictionary = getCurrentDictionary;
    }

    @Override
    public void showHelp() {
        System.out.println("/add [key, value] команда для добавления новой пары в словарь");
    }

    @Override
    public void execute(String... strings) {
        if (strings.length != 3) {
            System.err.println("Неверная сигнатура команды");
            showHelp();
        }
        String key = strings[1];
        String value = strings[2];
        Dictionary.get().add(key, value);
    }
}
