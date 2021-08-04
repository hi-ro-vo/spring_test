package ru.test;

import ru.test.dictionarynew.AbstractDictionary;
import ru.test.dictionarynew.Dictionary1;
import ru.test.dictionarynew.Dictionary2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public enum Commands {
        ADD, SHOW, DELETE, DIC, FIND, EXIT, HELP
    }

    static final private Logger logger = Logger.getLogger("ru.test.dictionaries.controller");
    private final Dictionary1 latinicDictionary = new Dictionary1();
    private final Dictionary2 numericDictionary = new Dictionary2();
    private DictionaryType currentDictionary = DictionaryType.LATINIC_DICTIONARY;
    private boolean loadedSuccessfully;
    private final FileController fileController = new FileController();

    public DictionariesController() {
        logger.setLevel(Level.ALL);
        ConsoleHandler c = new ConsoleHandler();
        c.setLevel(Level.ALL);
        logger.addHandler(c);
        loadedSuccessfully = true;

        Optional<Map<String, String>> map, map1;
        map = fileController.readFromFile("dic1.txt", latinicDictionary::isRuleFulfilled);
        map.ifPresent(latinicDictionary::loadFromMap);

        map1 = fileController.readFromFile("dic2.txt", numericDictionary::isRuleFulfilled);
        map1.ifPresent(numericDictionary::loadFromMap);

        if (map.isEmpty() || map1.isEmpty()) loadedSuccessfully = false;

    }

    private AbstractDictionary getCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            return latinicDictionary;
        } else {
            return numericDictionary;
        }
    }

    private void changeCurrentDictionary() {
        if (currentDictionary == DictionaryType.LATINIC_DICTIONARY) {
            currentDictionary = DictionaryType.NUMERIC_DICTIONARY;
        } else {
            currentDictionary = DictionaryType.LATINIC_DICTIONARY;
        }
    }

    private Optional<Commands> parseCommand(String command) {
        try {
            return Optional.of(Commands.valueOf(command.toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException e) {
            logger.log(Level.FINEST, "Unknown command", e);
            System.err.println("Unknown command: " + command);
        }
        return Optional.empty();
    }

    private void showHelp(Commands command) {
        switch (command) {
            case ADD: {
                System.out.println("/add [key, value] команда для добавления новой пары в словарь");
                break;
            }
            case DELETE: {
                System.out.println("/delete [key] команда для удаления пары из словаря по ключу");
                break;
            }
            case SHOW: {
                System.out.println("/show команда для вывода всего словаря на экран");
                break;
            }
            case DIC: {
                System.out.println("/dic команда для смены словаря");
                break;
            }
            case FIND: {
                System.out.println("/find [key] команда для поиска значения в словаре по ключу");
                break;
            }
            case HELP: {
                System.out.println("/help выводит список доступных команд");
                for (Commands c : Commands.values()) {
                    if (c != Commands.HELP) {
                        showHelp(c);
                    }
                }
                break;
            }
            case EXIT: {
                System.out.println("/exit завершение программы");
                break;
            }
        }

    }

    public void start() {
        if (!loadedSuccessfully) {
            System.err.println("Проверьте доступность и расположение файлов словарей");
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        showHelp(Commands.HELP);
        boolean exitFlag = false;

        while (!exitFlag) {
            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {

                break;
            }
            if (line.charAt(0) == '/') {
                String[] strings = line.substring(1).split(" ");
                Optional<Commands> command = parseCommand(strings[0]);
                if (command.isPresent()) {
                    switch (command.get()) {
                        case ADD: {
                            if (strings.length != 3) {
                                System.err.println("Неверная сигнатура команды");
                                showHelp(command.get());
                                break;
                            }
                            String key = strings[1];
                            String value = strings[2];
                            getCurrentDictionary().add(key, value);
                            break;
                        }
                        case DELETE: {
                            if (strings.length != 2) {
                                System.err.println("Неверная сигнатура команды");
                                showHelp(command.get());
                                break;
                            }
                            String key = strings[1];
                            if (!getCurrentDictionary().delete(key))
                                System.err.println("В словаре нет ключа" + key);
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
                            if (strings.length != 2) {
                                System.err.println("Неверная сигнатура команды");
                                showHelp(command.get());
                                break;
                            }
                            String key = strings[1];
                            Optional<String> value = getCurrentDictionary().find(key);
                            if (value.isEmpty()) {
                                System.out.println("Значение для ключа " + key + " не найдено");
                            } else {
                                System.out.println(value);
                            }
                            break;
                        }
                        case HELP: {
                            showHelp(Commands.HELP);
                            break;
                        }
                        case EXIT: {
                            exitFlag = true;
                            if (!fileController.saveToFile("dic1.txt", latinicDictionary.getMap()) ||
                                    !fileController.saveToFile("dic2.txt", numericDictionary.getMap()))
                                System.err.println("Проблемы при сохранении словарей");
                            break;
                        }
                    }
                }
            }
        }
    }
}
