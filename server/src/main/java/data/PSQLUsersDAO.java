package data;

import communication.User;
import communication.UserImpl;
import connection.ServerConnectionManager;
import exceptions.LoginException;
import exceptions.PersistentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

//PSQLVehicleDAO, PSQLUsersDAO - классы доступа к базе данных.
//мы можем добавить пользователя в БД, посмотреть есть ли пользователь с данным логином и паролем.

public class PSQLUsersDAO implements UsersDAO{

    private final CryptoModule cryptoModule;

    private final DAOFactory daoFactory;

    private static final Logger logger = LoggerFactory.getLogger(PSQLUsersDAO.class);

    public PSQLUsersDAO(DAOFactory daoFactory, CryptoModule cryptoModule) throws SQLException {
        this.daoFactory = daoFactory;
        this.cryptoModule = cryptoModule;
        create();
    }

    private void create() throws SQLException {
        Statement statement = daoFactory.createConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS  users " +
                "(login varchar primary key, " +
                " password varchar not null)");
    }

    @Override
    public void auth(User user) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE login = ?", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.getResultSet();
                if(!resultSet.next() || !resultSet.getString(2).equals(cryptoModule.hash(user.getPassword())))
                    throw new LoginException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        } catch (Exception e) {
            logger.error("Ошибка во время авторизации: " + e.getClass());
            throw new LoginException();
        }
    }

    @Override
    public void insertUser(User user) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?,?)");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, cryptoModule.hash(user.getPassword()));
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        } catch (Exception e) {
            logger.error("Ошибка во время регистрации: " + e.getClass());
            throw new LoginException();
        }
    }

    @Override
    public void deleteUser(User user) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE login = ?");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        } catch (Exception e) {
            logger.error("Ошибка во время удаления пользователя: " + e.getClass());
            throw new LoginException();
        }
    }
}
