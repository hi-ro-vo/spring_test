package ru.test;

import ru.test.dictionaries.DictionariesController;

public class Mmtr {


    public static void main(String[] args) {
        DictionariesController controller = new DictionariesController();
        String firstFilePath;
        String secondFilePath;
        if (args.length == 2) {
            firstFilePath = args[0];
            secondFilePath = args[1];
        } else {
            firstFilePath = "dic1.txt";
            secondFilePath = "dic2.txt";
        }

        controller.start(firstFilePath, secondFilePath);

    }
}
