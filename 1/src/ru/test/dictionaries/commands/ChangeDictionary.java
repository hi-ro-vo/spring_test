package ru.test.dictionaries.commands;

import ru.test.dictionaries.DictionariesController;

public class ChangeDictionary extends Command{
    private final DictionariesController dictionariesController;

    public ChangeDictionary(DictionariesController controller){
        dictionariesController = controller;
    }

    @Override
    public void showHelp() {
        System.out.println("/dic команда для смены словаря");
    }

    @Override
    public void execute(String... strings) {
        dictionariesController.changeCurrentDictionary();//TODO: мне это не нравится :(, как сделать  по-другому я не знаю
    }
}
