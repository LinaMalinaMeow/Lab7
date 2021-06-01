package command;

import app.AbstractFactory;
import communication.Response;
import communication.User;
import data.AuthModule;
import object.Vehicle;

public class LoginCommand extends AbstractCommand implements Command, AuthCommand{
    private final AuthModule authModule;
    private final AbstractFactory abstractFactory;

    public LoginCommand(AuthModule usersDAO, AbstractFactory abstractFactory) {
        super("login", "войти в систему");
        this.abstractFactory = abstractFactory;
        this.authModule = usersDAO;
    }

    @Override
    public Response execute(String str, Vehicle vehicle, User user) {
        return authModule.login(user)
                ? abstractFactory.getResponse(true, "Успешный вход")
                : abstractFactory.getResponse(false, "Неверный логин или пароль");
    }
}
