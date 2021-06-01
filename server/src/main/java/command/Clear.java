package command;

import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import exceptions.WrongArgumentException;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author alina
 */
public class Clear extends AbstractCommand implements Command {
    CollectionManager repository;
    AbstractFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(Clear.class);

    public Clear(CollectionManager repository, AbstractFactory factory) {
        super("clear", "очистить коллекцию");
        this.repository = repository;
        this.factory = factory;
    }

    /**
     * Execute of 'clear' command.
     */
    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        try {
            if (!str.isEmpty()) {
                throw new WrongArgumentException();
            }
            repository.clear(user);
            return factory.getResponse(true,"Ваш раздел коллекции успешно очищен!");
        } catch (WrongArgumentException e) {
            return factory.getResponse(false,"Используйте: '" + getName() + "'");
        } catch (Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
            return factory.getResponse(false,"Что-то пошло не так. Повторите ввод.");
        }
    }
}