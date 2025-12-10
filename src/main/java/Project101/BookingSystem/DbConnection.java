package Project101.BookingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {

    private static Map<String,Connection> connections;

    DbConnection(){
        connections = new HashMap<>();
    }

    public static Connection getConnection(String database) {
        if (!connections.containsKey(database))
            connections.put(database,createConnection(database));
        return  connections.get(database);
    }

    public static synchronized Connection createConnection(String database) {
        Connection connection = null;
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            String url = "jdbc:mysql://localhost:3306/"+database;
            String username = "root";
            String password = "rootroot";
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

