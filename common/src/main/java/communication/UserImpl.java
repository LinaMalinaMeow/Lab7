package communication;

import java.io.Serializable;

public class UserImpl implements User, Serializable {

    private final String login;
    private final String password;

    public UserImpl(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
