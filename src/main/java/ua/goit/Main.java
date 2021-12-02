package ua.goit;

import ua.goit.controller.Controller;
import ua.goit.service.ConsoleManager;

public class Main {
    public static void main(String[] args) {
        ConsoleManager consoleManager = new ConsoleManager(System.in, System.out);
        Controller controller = new Controller(consoleManager);
        controller.doCommand();
    }
}
