package command;

import communication.Response;
import communication.User;
import object.Vehicle;

public interface Command {
    Response execute(String str, Vehicle vehicle, User user);
}
