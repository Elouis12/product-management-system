package user;

import dbUtil.DBConnection;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    private static String userId;
    private static String userUserName;
    private static String userEmail;

    public UserModel(/*int user_id, */String user_username/*, String user_email*/) {

        userUserName = user_username;
        queryUserInfo();
    }

    private void queryUserInfo(){ // fills in the remaining fields

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try{
            String sql = "SELECT * FROM users WHERE username = '" + this.userUserName + "';";
            connection = DBConnection.getConnection();

            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ){


                this.userId = resultSet.getString(1); // id
//                this.userUserName = resultSet.getString(2); // username
                this.userEmail = resultSet.getString(3); // email
            }

        }catch (SQLException e){

            System.err.println(e);

        }
    }

    // static so we don't have to make another object and have to "redefine the user's info"
    public static String getUserId() {

        return userId;
    }
    public static String getUserUserName() {

        return userUserName;
    }

    public static String getUserEmail() {

        return userEmail;
    }
}
