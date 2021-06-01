package command;

import app.AbstractFactory;
import reader.Asker;
import reader.InputManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author alina
 */
public class ExecuteScriptFileName implements ClientCommand {
    @Override
    public void execute(String str, AbstractFactory factory, CommandManager commandManager) {
        File file = new File(str);
        if(!file.exists()) {
            System.out.println("Ошибка: файл не найден.");
            return;
        }
        if(!file.canRead()) {
            System.out.println("Ошибка: файл недоступен для чтения.");
            return;
        }
        Scanner userScanner;
        Asker asker;
        Asker prevAsker = commandManager.getAsker();
        try {
            userScanner = new Scanner(Paths.get(str));
            asker = factory.getFileAsker(userScanner);
            commandManager.setAsker(asker);
        } catch (IOException e) {
            System.out.println("Ошибка: файл недоступен.");
            return;
        }
        try {
            InputManager consFileManager = factory.getFileInputManager(userScanner, commandManager);
            while (userScanner.hasNext())
                consFileManager.readCommand();
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении скрипта.");
        } finally {
            commandManager.setAsker(prevAsker);
        }
    }
}
