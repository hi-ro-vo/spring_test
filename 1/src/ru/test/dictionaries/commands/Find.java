package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.Optional;
import java.util.function.Supplier;

public class Find extends Command{
    private final Supplier<AbstractDictionary> Dictionary;

    public Find(Supplier<AbstractDictionary> getCurrentDictionary){
        Dictionary = getCurrentDictionary;
    }

    @Override
    public void showHelp() {
        System.out.println("/find [key] команда для поиска значения в словаре по ключу");
    }

    @Override
    public void execute(String... strings) {
        if (strings.length != 2) {
            System.err.println("Неверная сигнатура команды");
            showHelp();
        }
        String key = strings[1];
        Optional<String> value = Dictionary.get().find(key);
        if (value.isEmpty()) {
            System.out.println("Значение для ключа " + key + " не найдено");
        } else {
            System.out.println(value);
        }
    }
}
