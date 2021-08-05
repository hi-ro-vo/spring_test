package ru.test.dictionaries.commands;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.function.Supplier;

public class Show extends Command{
    private final Supplier<AbstractDictionary> Dictionary;

    public Show(Supplier<AbstractDictionary> getCurrentDictionary){
        Dictionary = getCurrentDictionary;
    }


    @Override
    public void showHelp() {
        System.out.println("/show команда для вывода всего словаря на экран");
    }

    @Override
    public void execute(String... strings) {
        Dictionary.get().getMap().forEach((key, value) -> System.out.println(key + " " + value));
    }
}
