package bazasql;

import static bazasql.BazaSql.dateBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Singleton {

    private static Singleton instance = null;

    Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection connection = dateBaseConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        return result;
    }

    public int executeUpdate(String query) throws SQLException {
        Connection connection = dateBaseConnection();
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(query);
        return result;
    }
}
