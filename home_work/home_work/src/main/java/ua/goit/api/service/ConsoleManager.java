package ua.goit.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ConsoleManager {
    private final Scanner scanner;
    private final OutputStream out;

    public ConsoleManager(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    public String read() {
        return scanner.nextLine();
    }

    public void write(String message) {
        try {
            out.write(message.getBytes());
            out.write("\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
