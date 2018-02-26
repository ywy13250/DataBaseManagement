package sg.edu.nus.comp;

import java.sql.*;
import java.util.Optional;

public class DataBase {
    private String password;
    private String username;
    private String url;
    private final String DEFAULT_URL = "jdbc:postgresql://localhost:5433/postgres";
    private final String DEFAULT_URL_PREFIX = "jdbc:postgresql://";
    private Connection connection = null;

    public DataBase(String username, String password) {
        this.username = username;
        this.password = password;
        url = DEFAULT_URL;
    }

    public DataBase(String databaseAddress, String username, String password) {
        this.username = username;
        this.password = password;
        this.url = databaseAddress;
    }

    public DataBase(String url, String databaseName, String port, String username, String password) {
        this.username = username;
        this.password = password;
        this.url = DEFAULT_URL_PREFIX + url.trim()
                + ":" + port.trim() + "/" + databaseName;
    }

    public void connect() throws ConnectionFailException {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new ConnectionFailException();
        }
        if (connection == null) {
            throw new ConnectionFailException();
        }
    }

    public Optional<ResultSet> executeQuery(String query) {

        try {
            Statement stmt = connection.createStatement();
            boolean haveResult = stmt.execute(query);
            if(haveResult)
                return Optional.of(stmt.getResultSet());
            else
                return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }




}
