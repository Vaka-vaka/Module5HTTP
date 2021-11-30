package ua.goit.api;

import ua.goit.api.controller.Controller;
import ua.goit.api.service.ConsoleManager;

public class Main {
    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager(System.in, System.out);
        Controller controller = new Controller(consoleManager);
        controller.doCommand();
    }
}
