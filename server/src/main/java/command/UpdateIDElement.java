package command;

import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;

public class UpdateIDElement extends AbstractCommand implements Command {
    private final CollectionManager repository;
    private final AbstractFactory factory;

    public UpdateIDElement(CollectionManager repository, AbstractFactory factory){
        super("update"," обновить значение элемента коллекции, id которого равен заданному");
        this.repository = repository;
        this.factory  = factory;
    }
    @Override
    public Response execute(String str, Vehicle vehicle, User user){
        try {
            int id = Integer.parseInt(str);
            if(this.repository.update(id, vehicle, user)){
                return factory.getResponse(true, "Элемент успешно обновлен!");
            }
            else {
                return factory.getResponse(false,"Вашего элемента с данным id нет в коллекции!");
            }
        } catch (NumberFormatException e) {
            return factory.getResponse(false,"Неверный формат id");
        }
    }
}
