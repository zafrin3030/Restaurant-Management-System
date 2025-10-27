package bd.edu.seu.rms.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "binty152930";
    private static final String DB_Name = "rms_db";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_Name;

    private static Connection connection = null;
    private static ConnectionSingleton instance = new ConnectionSingleton();

    private ConnectionSingleton(){

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        } catch (SQLException e) {
            System.err.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
