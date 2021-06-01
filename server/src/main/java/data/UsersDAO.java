package data;

import communication.User;

public interface UsersDAO {
    void auth(User user);
    void insertUser(User user);
    void deleteUser(User user);
}
