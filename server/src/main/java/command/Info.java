package command;

import app.AbstractFactory;
import collection.CollectionManager;
import communication.Response;
import communication.User;
import exceptions.WrongArgumentException;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Info extends AbstractCommand implements Command {
    private final CollectionManager repository;
    private final AbstractFactory abstractFactory;
    private static final Logger logger = LoggerFactory.getLogger(Info.class);

    public Info(CollectionManager repository, AbstractFactory abstractFactory) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.repository = repository;
        this.abstractFactory = abstractFactory;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        try {
            if (!str.isEmpty()) {
                throw new WrongArgumentException();
            }
            return abstractFactory.getResponse(true, repository.info());
        } catch (WrongArgumentException e) {
            return abstractFactory.getResponse(false,"Используйте: '" + getName() + "'");
        } catch (Exception e) {
            logger.error(e.getClass() + " " + e.getMessage());
            return abstractFactory.getResponse(false,"Что-то пошло не так. Повторите ввод!");
        }
    }
}
