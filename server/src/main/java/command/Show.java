package command;

import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;
import object.VehicleDisplayer;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * Command 'show'. Shows information about all elements of the collection.
 */
public class Show extends AbstractCommand implements Command {
    private final CollectionManager repository;
    private final AbstractFactory factory;
    private final VehicleDisplayer vehicleDisplayer;

    public Show(CollectionManager repository, AbstractFactory factory, VehicleDisplayer vehicleDisplayer) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.repository = repository;
        this.factory = factory;
        this.vehicleDisplayer = vehicleDisplayer;
    }

    @Override
    public Response execute(String str, Vehicle v, User user) {
        if(this.repository.getPriorityQueue().size() == 0){
            return factory.getResponse(false,"Коллекция пуста");
        }
        return factory.getResponse(true, repository.getPriorityQueue().stream().map(vehicleDisplayer::displayVehicle).collect(Collectors.joining("\n")));
    }
}
