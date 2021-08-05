package ru.test.dictionaries;

import ru.test.dictionaries.commands.*;

import ru.test.dictionaries.dictionary.AbstractDictionary;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandFactory {

    private Supplier<AbstractDictionary> getCurrentDictionary;
    private DictionariesController dictionariesController;
    boolean resourceSeted = false;

    private final Logger logger = Logger.getLogger("ru.test.dictionaries.CommandFactory");


    CommandFactory(DictionariesController controller) {
        dictionariesController = controller;
        getCurrentDictionary = controller::getCurrentDictionary;
        resourceSeted = true;
    }


    public Command createCommand(CommandsEnum command) {//TODO: возможно кешировать уже созданные команды;
        Command result;
        switch (command) {
            case ADD: {
                result = new Add(getCurrentDictionary);
                break;
            }
            case DELETE: {
                result = new Delete(getCurrentDictionary);
                break;
            }
            case SHOW: {
                result = new Show(getCurrentDictionary);
                break;
            }
            case DIC: {
                result = new ChangeDictionary(dictionariesController);
                break;
            }
            case FIND: {
                result = new Find(getCurrentDictionary);
                break;
            }
            case HELP: {
                result = new Help(this);
                break;
            }
            case EXIT: {
                result = new Exit(dictionariesController);
                break;
            }
            default: {
                IllegalStateException e = new IllegalStateException("Unexpected value: " + command);
                logger.log(Level.FINE, "Unexpected value: " + command, e);
                throw e;

            }
        }
        return result;
    }

    public Command createCommand(String stringCommand){
        CommandsEnum command;
            try {
                command = CommandsEnum.valueOf(stringCommand.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                logger.log(Level.FINEST, "Unknown command", e);
                throw e;
            }

        return createCommand(command);
    }
}

