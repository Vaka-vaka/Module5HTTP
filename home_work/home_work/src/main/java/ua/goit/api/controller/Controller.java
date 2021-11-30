package ua.goit.api.controller;

import ua.goit.api.command.Command;
import ua.goit.api.command.PetCommand;
import ua.goit.api.command.StoreCommand;
import ua.goit.api.command.UserCommand;
import ua.goit.api.service.ConsoleManager;
import ua.goit.api.service.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private final ConsoleManager consoleManager;
    private final ArrayList<Command> commands;

    public Controller(ConsoleManager consoleManager){
        this.consoleManager = consoleManager;
        commands = new ArrayList<>(Arrays.asList(new PetCommand(consoleManager), new UserCommand(consoleManager),
                new StoreCommand(consoleManager)));
    }

    public void doCommand(){
        boolean running = true;
        consoleManager.write("Welcome to Swagger PetStore!");
        while (running) {
            consoleManager.write(Util.menuMessage());
            String inputCommand = consoleManager.read();
            for (Command command : commands) {
                if (command.canProcess(inputCommand)) {
                    command.process();
                    break;
                } else if (inputCommand.equalsIgnoreCase("exit")) {
                    consoleManager.write("Good bye!");
                    running = false;
                    break;
                }
            }
        }
    }

}
