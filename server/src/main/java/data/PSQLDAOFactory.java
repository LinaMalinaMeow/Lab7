package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//создает соединение с базой данных.

public class PSQLDAOFactory implements DAOFactory {

    private static final Logger logger = LoggerFactory.getLogger(PSQLDAOFactory.class);


    private String URL="jdbc:postgresql://pg:5432/studs";
    private String USER;
    private String PASSWORD;

    @Override
    public void start(String url, String user, String password) {
        URL = url;
        USER = user;
        PASSWORD = password;
        Connection testConnection = null;
        try {
            testConnection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error. Can't connect to data base: exception {}", e.getClass());
            System.exit(1);
        }
        if (testConnection != null)
            logger.info("Successfully connected to the data base.");
        else
            logger.error("Error. Can't connect to data base.");
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
