package data;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAOFactory {
    void start(String url, String user, String password);
    Connection createConnection() throws SQLException;
}
