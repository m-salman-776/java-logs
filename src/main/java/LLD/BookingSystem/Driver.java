package LLD.BookingSystem;

import java.sql.Connection;
import java.sql.Statement;

public class Driver {

    static String insertUser = "INSERT INTO user (name, email, version_id) VALUES ('John Doe', 'john.doe@example.com', 1)";
    public static void main(String []args){
        String dbName = "booking_system";
        Connection dbConnection = DbConnection.getConnection(dbName);
//        Connection dbConnection = DbConnection.createConnection(dbName);

        try (Statement statement = dbConnection.createStatement()){
            int rows = statement.executeUpdate(insertUser);
        }catch (Exception e){

        }
    }
}
