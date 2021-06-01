package command_manager;

import communication.Request;
import communication.Response;
import communication.User;
import object.Vehicle;

public interface CommandManager {
    Response execute(String command, String arg, Vehicle vehicle, User user);
    Response askVehicle(String arg, User user);
}
