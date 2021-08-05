package ru.test.dictionaries.commands;

import ru.test.dictionaries.CommandFactory;

public class Help extends Command{
    private final CommandFactory commandFactory;


    public Help(CommandFactory factory){
        commandFactory = factory;
    }

    @Override
    public void showHelp() {
        System.out.println("/help выводит список доступных команд");
    }

    @Override
    public void execute(String... strings) {
        for (CommandsEnum command: CommandsEnum.values()){
            Command c = commandFactory.createCommand(command);
            c.showHelp();
        }
    }
}
