package data;

import communication.User;

public interface AuthModule {
    boolean login(User user);
    boolean register(User user);
}
