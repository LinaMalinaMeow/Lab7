package command;

import app.AbstractFactory;
import communication.Response;
import communication.User;
import data.AuthModule;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterCommand extends AbstractCommand implements Command, AuthCommand {
    private final AuthModule usersDAO;
    private final AbstractFactory abstractFactory;
    private static final Logger logger = LoggerFactory.getLogger(RegisterCommand.class);

    public RegisterCommand(AuthModule usersDAO, AbstractFactory abstractFactory) {
        super("register", "зарегистрироваться в системе");
        this.abstractFactory = abstractFactory;
        this.usersDAO = usersDAO;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null || user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            return abstractFactory.getResponse(false, "Ошибка. Логин или пароль не могут быть пустыми.");
        }
        if (usersDAO.register(user)) {
            logger.info("Успешная регистрация " + user.getLogin());
            return abstractFactory.getResponse(true, "Успешная регистрация");
        } else {
            logger.info("Ошибка при регистрации " + user.getLogin());
            return abstractFactory.getResponse(false, "Ошибка: пользователь с таким логином уже существует.");
        }
    }
}
