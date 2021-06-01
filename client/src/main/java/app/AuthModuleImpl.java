package app;

import communication.User;
import reader.Asker;

public class AuthModuleImpl implements AuthModule{
    private User user;
    private final Asker asker;

    public AuthModuleImpl(Asker asker) {
        this.asker = asker;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User readUser() {
        User user = asker.readUser();
        this.user = user;
        return user;
    }
}
