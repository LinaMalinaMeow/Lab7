package command;

import reader.Asker;
import reader.InputManager;

public interface CommandManager {
    void startCommand(String[] userCommand);
    void setAsker(Asker asker);
    void connect(String host, int port);
    Asker getAsker();
}
