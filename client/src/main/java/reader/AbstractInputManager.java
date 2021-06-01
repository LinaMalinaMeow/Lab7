package reader;

import command.CommandManager;

import java.util.Scanner;

public abstract class AbstractInputManager implements InputManager {
    protected CommandManager commandManager;
    protected Scanner userScanner;

    public AbstractInputManager(Scanner scanner, CommandManager commandManager) {
        this.userScanner = scanner;
        this.commandManager = commandManager;
    }
}
