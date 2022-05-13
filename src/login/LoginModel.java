package login;

import dbUtil.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {


    private Connection connection;

    public LoginModel(){

        try{

            this.connection = DBConnection.getConnection();

        }catch (SQLException e){

            e.printStackTrace();
        }

        if( this.connection == null ){ // not connected

            System.exit( 1 );
        }
    }

    public boolean isLoggedIn(String username, String password){

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try{
            String sql = "SELECT * FROM login WHERE username = ? AND password = ?";
            this.connection = DBConnection.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

//            connection.close();
//            preparedStatement.close();
            /*while ( resultSet.next() ){

                return true;
            }*/
            return resultSet.next();


        }catch (SQLException e){

            System.err.println(e);
        }

        return false;
    }
}
