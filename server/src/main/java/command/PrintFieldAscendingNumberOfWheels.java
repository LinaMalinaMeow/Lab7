package command;


import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;

public class PrintFieldAscendingNumberOfWheels extends AbstractCommand implements Command {
    private CollectionManager repository;
    private AbstractFactory factory;

    public PrintFieldAscendingNumberOfWheels(CollectionManager repository, AbstractFactory factory){
        super("print_field_ascending_number_of_wheels", "вывести значения поля numberOfWheels всех элементов в порядке возрастания");
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user){
        StringBuilder builder = new StringBuilder();
        this.repository.getAscNumberWheels().forEach(x -> builder.append(x).append(" "));
        return factory.getResponse(true, builder.toString());
    }
}
