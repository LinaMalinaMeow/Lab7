package app;

import command.CommandManager;
import reader.InputManager;

import java.net.InetAddress;
import java.util.NoSuchElementException;

public class ClientImpl implements Client {

    private final InputManager inputManager;
    private final CommandManager commandManager;

    public ClientImpl(InputManager inputManager, CommandManager commandManager) {
        this.inputManager = inputManager;
        this.commandManager = commandManager;
    }

    @Override
    public void userMode(String host, int port) {
        try {
            commandManager.connect(host, port);
            while (true) {
                inputManager.readCommand();
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Ошибка! Завершаю программу.");
            System.exit(0);
        } catch (Exception exception) {
            System.out.println("Упс... ошибочка!");
        }
    }

}
