package ua.goit.controller;

import ua.goit.command.Command;
import ua.goit.command.PetCommand;
import ua.goit.command.StoreCommand;
import ua.goit.command.UserCommand;
import ua.goit.service.ConsoleManager;
import ua.goit.service.Util;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private final ConsoleManager consoleManager;
    private final ArrayList<Command> commands;

    public Controller(ConsoleManager consoleManager){
        this.consoleManager = consoleManager;
        commands = new ArrayList<>(Arrays.asList(
                new PetCommand(consoleManager), new UserCommand(consoleManager),
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
