package app;

import command.CommandManager;
import communication.Request;
import communication.RequestImpl;
import communication.RequestType;
import communication.User;
import object.Vehicle;
import reader.Asker;
import reader.FileAsker;
import reader.FileInputManager;
import reader.InputManager;

import java.util.Scanner;

public class ClientAbstractFactory implements AbstractFactory{

    @Override
    public Asker getFileAsker(Scanner scanner) {
        return new FileAsker(scanner);
    }

    @Override
    public InputManager getFileInputManager(Scanner scanner, CommandManager commandManager) {
        return new FileInputManager(scanner, commandManager);
    }

    @Override
    public Request getRequest(RequestType askVehicle, String command, String arg, Vehicle vehicle, User user) {
        return new RequestImpl(askVehicle, vehicle, command, arg, user);
    }
}
