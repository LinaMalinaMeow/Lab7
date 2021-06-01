package command;

import app.AbstractFactory;
import reader.InputManager;

public interface ClientCommand {
    void execute(String fileName, AbstractFactory factory, CommandManager manager);
}
