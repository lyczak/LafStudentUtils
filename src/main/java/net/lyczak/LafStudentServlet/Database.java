package net.lyczak.LafStudentServlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class Database {
    private static final String DATASOURCE_PATH = "java:comp/env/jdbc/lssdb";

    private static Database instance;

    private Database() { }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public void initDb() throws SQLException {
        openConnection(connection -> {
            Statement s = connection.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS users (userId TEXT PRIMARY KEY, casUsername TEXT NOT NULL, webDriverUrl TEXT)");
            return null;
        });
    }

    public LssUser findUserByUserId(String userId) {
        try {
            return openConnection(connection -> {
                PreparedStatement s = connection.prepareStatement(
                        "SELECT userId, casUsername, webDriverUrl FROM users WHERE userId = ?");
                s.setString(1, userId);

                ResultSet rs = s.executeQuery();
                if (rs.next()) {
                    return new LssUser(
                            rs.getString("userId"),
                            rs.getString("casUsername"),
                            rs.getString("webDriverUrl"));
                }
                return null;
            });
        } catch (SQLException e) {
            return null;
        }
    }

    public LssUser findUserByCasUsername(String casUsername) {
        try {
            return openConnection(connection -> {
                PreparedStatement s = connection.prepareStatement(
                        "SELECT userId, casUsername, webDriverUrl FROM users WHERE casUsername = ?");
                s.setString(1, casUsername);

                ResultSet rs = s.executeQuery();
                if (rs.next()) {
                    return new LssUser(
                            rs.getString("userId"),
                            rs.getString("casUsername"),
                            rs.getString("webDriverUrl"));
                }
                return null;
            });
        } catch (SQLException e) {
            return null;
        }
    }

    private <R> R openConnection(ConnectionFunc<R> connectionFunc) throws SQLException {
        Connection connection = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(DATASOURCE_PATH);

            connection = ds.getConnection();
            return connectionFunc.apply(connection);
        } catch (NamingException e) {
            throw new SQLException(e);
        } finally {
            if(connection != null)
                connection.close();
        }
    }

    private interface ConnectionFunc<R> {
        R apply(Connection connection) throws SQLException;
    }
}
