package command;

import collection.CollectionManager;
import communication.Response;
import communication.User;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alina
 */
public class Exit extends AbstractCommand implements Command {
    private CollectionManager repository;
    private static final Logger logger = LoggerFactory.getLogger(Exit.class);

    /**
     *
     * @param repository(Command 'exit'. Exits the program.)
     */
    public Exit(CollectionManager repository) {
        super("exit", "завершить программу (без сохранения в файл");
        this.repository = repository;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        logger.debug("Работа сервера была завершена командой exit.");
        System.exit(0);
        //UNREACHABLE
        return null;
    }
}
