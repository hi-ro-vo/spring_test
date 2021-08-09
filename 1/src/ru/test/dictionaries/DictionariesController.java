package ru.test.dictionaries;

import ru.test.dictionaries.commands.Command;
import ru.test.dictionaries.commands.CommandsEnum;
import ru.test.dictionaries.dao.DictionaryDAO;
import ru.test.dictionaries.dao.FileController;
import ru.test.dictionaries.dictionary.AbstractDictionary;
import ru.test.dictionaries.dictionary.LatinicDictionary;
import ru.test.dictionaries.dictionary.NumericDictionary;
import ru.test.dictionaries.exeptions.IllegalArgumentsCountException;
import ru.test.dictionaries.exeptions.NotACommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DictionariesController {
    private static final  Logger logger = Logger.getLogger(DictionariesController.class.getName());
    private final LatinicDictionary latinicDictionary = new LatinicDictionary();
    private final NumericDictionary numericDictionary = new NumericDictionary();
    private boolean exitFlag = false;
    private DictionaryType currentDictionary = DictionaryType.LATINIC_DICTIONARY;


    public DictionariesController() {
        logger.setLevel(Level.ALL);
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

    public void exit() {
        exitFlag = true;
    }

    public void start(String firstFilePath, String secondFilePath) {

        DictionaryDAO latinicDAO = new FileController(latinicDictionary, firstFilePath);
        DictionaryDAO numericDAO = new FileController(numericDictionary, secondFilePath);

        try {
            latinicDAO.load();
            numericDAO.load();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Loading from DAO error: ", e);
            return;
        }

        CommandFactory commandFactory = new CommandFactory(this);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            commandFactory.createCommand(CommandsEnum.HELP).execute();
        } catch (IllegalArgumentsCountException e) {
            logger.log(Level.FINE, e.getMessage(), e);
        }

        exitFlag = false;

        while (!exitFlag) {
            try {
                Command command = commandFactory.readCommandFromReader(in);
                command.execute();
            } catch (IllegalArgumentException | IOException | IllegalArgumentsCountException | NotACommandException e) {
                System.err.println(e.getMessage());
            }
        }

        latinicDAO.save();
        numericDAO.save();
    }

    public enum DictionaryType {
        LATINIC_DICTIONARY, NUMERIC_DICTIONARY
    }
}
