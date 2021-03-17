/**
 * Class used to get connection with the database.
 *
 * @author Mitrica Livia Maria
 * @group 30424
 * @param LOGGER this object is used for logging information about the application
 */
package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/pt_a3_db";
    private static final String USER = "root";
    private static final String PASS = "Anaare11mere.";

    private static DBConnection singleInstance = new DBConnection();

    /**
     * method for connecting to the database
     */
    private DBConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets connection to the database
     * @return connection created
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * method for closing database connection
     * @param connection this represents the connection we want to close
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * method for closing statement
     * @param statement this represents the connection we want to close
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * method for closins database reesultSet
     * @param resultSet this represents the connection we want to close
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
    /**
     * method for connecting to the database
     * @return created connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }
}
