package app;

import command.CommandManager;
import communication.Request;
import communication.RequestType;
import communication.User;
import object.Vehicle;
import reader.Asker;
import reader.InputManager;

import java.util.Scanner;

public interface AbstractFactory {
    Asker getFileAsker(Scanner scanner);
    InputManager getFileInputManager(Scanner scanner, CommandManager commandManager);
    Request getRequest(RequestType askVehicle, String command, String arg, Vehicle vehicle, User user);
}
