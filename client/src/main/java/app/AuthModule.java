package app;

import communication.User;

//отвечает за считывание User по логину и паролю с помощью Asker.readUser() и хранение последнего считанного User.

public interface AuthModule {
    User getUser();
    void setUser(User user);
    User readUser();
}
