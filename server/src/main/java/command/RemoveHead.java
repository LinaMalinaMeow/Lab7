package command;


import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;
import object.VehicleDisplayer;

public class RemoveHead extends AbstractCommand implements Command {
    private CollectionManager repository;
    private AbstractFactory factory;
    private VehicleDisplayer vehicleDisplayer;

    public RemoveHead(CollectionManager repository, AbstractFactory factory, VehicleDisplayer vehicleDisplayer) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
        this.repository = repository;
        this.factory = factory;
        this.vehicleDisplayer = vehicleDisplayer;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        try {
            Vehicle v = repository.removeHead(user);
            if(v != null)
                return factory.getResponse(true, vehicleDisplayer.displayVehicle(v));
            else{
                return factory.getResponse(false,"Ваш раздел коллекции пуст!");
            }
        } catch (Exception e) {
            return factory.getResponse(false, e.getMessage());
        }
    }
}
