package command;

import app.AbstractFactory;
import app.AuthModule;
import communication.Request;
import communication.RequestType;
import communication.User;

//LoginCommand и RegisterCommand - команды, которые с помощью AuthModule считывают User
// и формируют запрос для логина/регистрации соответственно

public class LoginCommand implements AuthCommand {
    private final AuthModule authModule;
    private final AbstractFactory factory;

    public LoginCommand(AuthModule authModule, AbstractFactory factory) {
        this.authModule = authModule;
        this.factory = factory;
    }

    @Override
    public Request execute() {
        return factory.getRequest(RequestType.EXECUTE, "login", null,null, authModule.readUser());
    }
}
