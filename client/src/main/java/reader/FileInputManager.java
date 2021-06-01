package reader;

import command.CommandManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileInputManager extends AbstractInputManager{
    public FileInputManager(Scanner scanner, CommandManager commandManager) {
        super(scanner, commandManager);
    }

    /**
     * Mode for work with commands from user input.
     */
    public void readCommand() {
        String[] userCommand;
        try {
            while (true) {
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandManager.startCommand(userCommand);
            }
        } catch (NoSuchElementException exception) {
            //System.out.println("Введён конец файла! Завершаю программу.");
            //System.exit(0);
        } catch (IllegalStateException exception) {
            System.out.println("Непредвиденная ошибка!");
        }
    }
}
