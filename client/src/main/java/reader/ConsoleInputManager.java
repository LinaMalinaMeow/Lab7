package reader;

import command.CommandManager;

import java.util.Scanner;

public class ConsoleInputManager extends AbstractInputManager {

    public ConsoleInputManager(Scanner userScanner, CommandManager commandManager) {
        super(userScanner, commandManager);
        super.commandManager = commandManager;
    }

    /**
     * Mode for work with commands from user input.
     */
    public void readCommand() {
        String[] userCommand;
        System.out.println("Введите команду{add,clear,head,info,show,min_by_distance_travelled,print_ascending,print_field_ascending_number_of_wheels,remove_by_id,remove_first,remove_head,save,update,execute_script ..,exit}: ");
        userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
        userCommand[1] = userCommand[1].trim();
        this.commandManager.startCommand(userCommand);
    }
}
