package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.function.Supplier;

public class Delete extends Command{

    private final Supplier<AbstractDictionary> Dictionary;

    public Delete(Supplier<AbstractDictionary> getCurrentDictionary){
        Dictionary = getCurrentDictionary;
    }

    @Override
    public void showHelp() {
        System.out.println("/delete [key] команда для удаления пары из словаря по ключу");
    }

    @Override
    public void execute(String... strings) {
        if (strings.length != 2) {
            System.err.println("Неверная сигнатура команды");
            showHelp();

        }
        String key = strings[1];
        if (Dictionary.get().delete(key))
            System.err.println("В словаре нет ключа" + key);
    }
}
