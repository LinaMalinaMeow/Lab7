package command;


import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveByID extends AbstractCommand implements Command {
    private CollectionManager repository;
    private AbstractFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(RemoveByID.class);

    public RemoveByID(CollectionManager repository, AbstractFactory factory) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        if (str.equals("")) {
            return factory.getResponse(false,"Нужен аргумент");
        }
        try {
            int id = Integer.parseInt(str);
            if(this.repository.removeById(id, user)){
                return factory.getResponse(true,"Успешно удален!");
            }else {
                return factory.getResponse(false,"Ваш элемент с данным индексом не найден!");
            }
        } catch (Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
            return factory.getResponse(false, "Не удалось удалить элемент.");
        }
    }
}

