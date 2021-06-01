package command;

import app.AbstractFactory;

public class ClientExitCommand implements ClientCommand{
    @Override
    public void execute(String fileName, AbstractFactory factory, CommandManager manager) {
        System.exit(0);
    }
}
