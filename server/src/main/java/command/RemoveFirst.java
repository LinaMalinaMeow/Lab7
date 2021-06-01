package command;


import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;

public class RemoveFirst extends AbstractCommand implements Command {
    private CollectionManager repository;
    private AbstractFactory factory;

    public RemoveFirst(CollectionManager repository, AbstractFactory factory){
        super("remove_first", "удалить первый элемент из коллекции");
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user){
        try {
            if(repository.removeFirst(user))
                return factory.getResponse(true,"Элемент был успешно удален из коллекции!");
            else{
                return factory.getResponse(false,"Ваш раздел коллекции пуст!");
            }
        } catch (Exception e) {
            return factory.getResponse(false, e.getMessage());
        }
    }
}
