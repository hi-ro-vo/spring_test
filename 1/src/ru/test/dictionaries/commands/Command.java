package ru.test.dictionaries.commands;

public abstract class Command {

    public abstract void showHelp();
    public abstract void execute(String... strings);
}
