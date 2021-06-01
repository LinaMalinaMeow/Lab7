package data;

import communication.User;
import exceptions.PersistentException;

//Он как надстройка над PSQLUsersDAO: только выдает true при успешной регистрации/входе или false при ошибке, а не сами ошибки.

public class AuthModuleImpl implements AuthModule{
    private final UsersDAO usersDAO;

    public AuthModuleImpl(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }


    @Override
    public boolean login(User user) {
        try {
            usersDAO.auth(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean register(User user) {
        try {
            usersDAO.insertUser(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
