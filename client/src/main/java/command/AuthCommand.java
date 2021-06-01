package command;

import communication.Request;
import communication.User;

// интерфейс команды авторизации(его реализуют LoginCommand и RegisterCommand).
// Метод execute() в нем возвращает Request для отправки серверу.

public interface AuthCommand {
    Request execute();
}
