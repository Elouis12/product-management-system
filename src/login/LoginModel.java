package login;

import dbUtil.DBConnection;
import user.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {


    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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

    public boolean hasUsername(String username){

        try{
            String sql = "SELECT username FROM users WHERE username = ?";
            this.connection = DBConnection.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);

            return resultSet.next();


        }catch (SQLException e){

            System.err.println(e);
        }

        return false;
    }

    public boolean hasEmail(String email){


        try{
            String sql = "SELECT email FROM users WHERE email = ?";
            this.connection = DBConnection.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            System.out.println(resultSet);
            return resultSet.next();


        }catch (SQLException e){

            System.err.println(e);
        }

        return false;
    }

    public boolean isLoggedIn(String username, String password){


        try{
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
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

            // so we can save the users's id to the correct product
            UserModel userModel = new UserModel(username/*, username*/);
            return resultSet.next();


        }catch (SQLException e){

            System.err.println(e);
        }

        return false;
    }
}
