package command;

import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;
import object.VehicleDisplayer;

import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * @author alina
 */
public class Head extends AbstractCommand implements Command {
    CollectionManager repository;
    AbstractFactory factory;
    VehicleDisplayer vehicleDisplayer;

    public Head(CollectionManager repository, AbstractFactory factory, VehicleDisplayer vehicleDisplayer){
        super("head", "вывести первый элемент коллекции");
        this.repository = repository;
        this.factory = factory;
        this.vehicleDisplayer = vehicleDisplayer;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user){
        Optional<Vehicle> vehicle1 = this.repository.getPriorityQueue().stream()
                .filter(x -> x.getUsername().equals(user.getLogin()))
                .max(Vehicle::compareTo);
        if(vehicle1.isPresent()) {
            vehicle = vehicle1.get();
            return factory.getResponse(true, vehicleDisplayer.displayVehicle(vehicle));
        } else {
            return factory.getResponse(false,"В коллекции нет первого элемента");
        }
    }
}
