package ru.test.dictionaries;

import ru.test.dictionaries.commands.Command;
import ru.test.dictionaries.commands.CommandsEnum;
import ru.test.dictionaries.dictionary.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionariesController {
    public enum DictionaryType {
        LATINIC_DICTIONARY, NUMERIC_DICTIONARY
    }

    static final private Logger logger = Logger.getLogger("ru.test.dictionaries.controller");
    private final Dictionary1 latinicDictionary = new Dictionary1();
    private final Dictionary2 numericDictionary = new Dictionary2();
    private DictionaryType currentDictionary = DictionaryType.LATINIC_DICTIONARY;
    private boolean loadedSuccessfully;
    private final FileController fileController = new FileController();
    private final CommandFactory commandFactory;

    public DictionariesController() {
        logger.setLevel(Level.ALL);
        ConsoleHandler c = new ConsoleHandler();
        c.setLevel(Level.ALL);
        logger.addHandler(c);
        loadedSuccessfully = true;

        commandFactory = new CommandFactory(this);

        Optional<Map<String, String>> map, map1;
        map = fileController.readFromFile("dic1.txt", latinicDictionary::isRuleFulfilled);
        map.ifPresent(latinicDictionary::loadFromMap);

        map1 = fileController.readFromFile("dic2.txt", numericDictionary::isRuleFulfilled);
        map1.ifPresent(numericDictionary::loadFromMap);

        if (map.isEmpty() || map1.isEmpty()) loadedSuccessfully = false;

    }

    public AbstractDictionary getCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            return latinicDictionary;
        } else {
            return numericDictionary;
        }
    }

    public void changeCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            currentDictionary = DictionaryType.NUMERIC_DICTIONARY;
        } else {
            currentDictionary = DictionaryType.LATINIC_DICTIONARY;
        }
    }





    public void saveDictionaries() {
        if (!fileController.saveToFile("dic1.txt", latinicDictionary.getMap()) ||
                !fileController.saveToFile("dic2.txt", numericDictionary.getMap())) {
            System.err.println("Проблемы при сохранении словарей");
        }
    }

    boolean exitFlag = false;//TODO: Вынес из start() для реализации команды exit;

    public void exit() {
        exitFlag = true;
    }

    public void start() {
        if (!loadedSuccessfully) {
            System.err.println("Проверьте доступность и расположение файлов словарей");
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //showHelp(Commands.HELP);
        exitFlag = false;

        while (!exitFlag) {
            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {
                logger.log(Level.FINE, e.getMessage(), e);
                break;
            }
            if (line.charAt(0) == '/') {
                String[] strings = line.substring(1).split(" ", 3);
                try {
                    Command command = commandFactory.createCommand(strings[0]);
                    command.execute(strings);
                } catch (IllegalArgumentException e){
                    System.err.println("Unknown command: " + strings[0]);
                }
            }
        }
    }
}
