package ru.test;

import ru.test.dictionarynew.AbstractDictionary;
import ru.test.dictionarynew.Dictionary1;
import ru.test.dictionarynew.Dictionary2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionariesController {
    public enum DictionaryType {
        LATINIC_DICTIONARY, NUMERIC_DICTIONARY
    }

    public enum Commands{
        ADD,SHOW,DELETE,DIC,FIND,EXIT,ALL
    }

    static final private Logger logger = Logger.getLogger("ru.test.dictionaries.controller");
    private final Dictionary1 latinicDictionary = new Dictionary1();
    private final Dictionary2 numericDictionary = new Dictionary2();
    private DictionaryType currentDictionary = DictionaryType.LATINIC_DICTIONARY;
    private boolean loadedSuccessfully = false;

    public DictionariesController() {
        try {
            latinicDictionary.readFromFile("dic1.txt");
            numericDictionary.readFromFile("dic2.txt");
        } catch (Exception e) {
            logger.log(Level.FINE, e.getMessage(), e);
            loadedSuccessfully = false;
        }
        loadedSuccessfully = true;
    }

    private AbstractDictionary getCurrentDictionary() {
        switch (currentDictionary) {
            case LATINIC_DICTIONARY: {
                return latinicDictionary;
            }
            case NUMERIC_DICTIONARY: {
                return numericDictionary;
            }
        }
        return latinicDictionary;
    }

    private void changeCurrentDictionary(){
        switch (currentDictionary) {
            case LATINIC_DICTIONARY: {
                currentDictionary = DictionaryType.NUMERIC_DICTIONARY;
                break;
            }
            case NUMERIC_DICTIONARY: {
                currentDictionary = DictionaryType.LATINIC_DICTIONARY;
                break;
            }
        }
    }

    private Optional<Commands> parseCommand(String command){
        try {
            return Optional.of(Commands.valueOf(command.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException e){
            logger.log(Level.FINEST, "Unknown command", e);
            System.err.println("Unknown command: " + command);
        }
        return Optional.empty();
    }

    private void showHelp(Commands command){
        switch (command){
            case ADD:{

            }
        }

    }

    public void start() {
        if (!loadedSuccessfully) {
            System.err.println("Проверьте доступность и расположение файлов словарей");
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("");//TODO: add starting help
        boolean exitFlag = false;

        while (!exitFlag) {
            try {
                String line = in.readLine();

                if (line.charAt(0) == '/') {
                    String[] strings = line.substring(1).split(" ");
                    Optional<Commands> command = parseCommand(strings[0]);
                    if (command.isEmpty()) break;
                    switch (command.get()) {
                        case ADD: {
                            String key = strings[1];
                            String value = strings[2];
                            getCurrentDictionary().add(key, value);
                            break;
                        }
                        case DELETE: {
                            getCurrentDictionary().delete(strings[1]);
                            break;
                        }
                        case SHOW: {
                            getCurrentDictionary().getMap().forEach((key, value) -> System.out.println(key + " " + value));
                            break;
                        }
                        case DIC: {
                            changeCurrentDictionary();
                            break;
                        }
                        case FIND: {
                            String key = strings[1];
                            System.out.println(getCurrentDictionary().find(key));
                            break;
                        }
                        case EXIT: {
                            exitFlag = true;
                        }
                        default: {

                        }
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
