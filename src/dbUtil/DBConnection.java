package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String USERNAME = PrivateDBConnection.USERNAME;
    private static final String PASSWORD = PrivateDBConnection.PASSWORD;
    private static final String HOST = PrivateDBConnection.HOST;


    public static Connection getConnection() throws SQLException{

        try {

            Class.forName( "com.mysql.cj.jdbc.Driver"); // setup the connection

            Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

            return connection;

        }catch (SQLException | ClassNotFoundException e){

            System.err.println(e);
        }

        return null;
    }


}
