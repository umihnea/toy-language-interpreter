package com.inter.view;

import com.inter.exceptions.ExitSignal;
import com.inter.exceptions.InterpreterException;
import com.inter.view.commands.Command;

import java.util.HashMap;
import java.util.Scanner;

public class ConsoleView {
    private Scanner in;
    private HashMap<String, Command> commands;

    public ConsoleView() {
        commands = new HashMap<>();
        in = new Scanner(System.in);
    }

    public void render() {
        while (true) {
            renderMenu();

            System.out.print(">> ");
            String key = in.nextLine();
            Command command = commands.get(key);

            if (command == null) {
                System.out.println("Command not implemented.");
                continue;
            }

            try {
                command.execute();
            } catch (InterruptedException ine) {
                System.err.println(ine.getMessage());
            } catch (ExitSignal es) {
                break;
            } catch (InterpreterException ie) {
                System.err.println(ie.getMessage());
            }
        }

    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void renderMenu() {
        for (Command c : commands.values()) {
            System.out.println(c.getKey() + ". " + c.getDescription());
        }
    }
}
