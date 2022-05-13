package login;

import dbUtil.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private TextField loginUsernameTextField;

    @FXML
    private TextField loginPasswordTextField;

    @FXML
    private Label loginMessageLabel;

    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    LoginModel loginModel = new LoginModel();

    @FXML
    public void login(){

        if( loginUsernameTextField.getText().equals("") ||
            loginPasswordTextField.getText().equals("") ){

            this.loginMessageLabel.setText( "Make sure all fields are filled" );
            return;
        }

        if( loginModel.isLoggedIn( this.loginUsernameTextField.getText(), this.loginPasswordTextField.getText() ) ){

            // remove the login screen
            Stage loginScreen = (Stage) this.loginUsernameTextField.getScene().getWindow();
            loginScreen.close();

            // show the user screen after logging in
            userLogin();

        }else{

            this.loginMessageLabel.setText( "Username or password is incorrect or try signing up" );
        }

    }

    // to show the user's screen after logging in
    public void userLogin(){

        try {

            Pane root = FXMLLoader.load( getClass().getClassLoader().getResource("./user/user.fxml") );

            Scene userScene = new Scene( root );

            Stage userStage = new Stage();
            userStage.setScene( userScene );
            userStage.setTitle( "Products" );
            userStage.setResizable( false );
            userStage.show();

        }catch (IOException e){

            System.err.println(e);
        }
    }


    @FXML
    private TextField signupUsernameTextField;

    @FXML
    private TextField signupEmailTextField;

    @FXML
    private TextField signupPasswordTextField;

    @FXML
    private Label signupMessageLabel;

    @FXML
    public void signUp(){


        try{

            if( signupUsernameTextField.getText().equals("") ||
                signupEmailTextField.getText().equals("") ||
                signupPasswordTextField.getText().equals("") ){

                this.signupMessageLabel.setText( "Make sure all fields are filled" );
                return;
            }

            if( loginModel.hasUsername( signupUsernameTextField.getText() ) ){

                this.signupMessageLabel.setText( "Username is already taken" );
                return;
            }

            if( loginModel.hasEmail( signupEmailTextField.getText() ) ){

                this.signupMessageLabel.setText( "Email is already taken" );
                return;
            }

            String sql = "INSERT INTO users(username, email, password) VALUES( ?, ?, ? );";
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // place in the values
            preparedStatement.setString(1, signupUsernameTextField.getText() );
            preparedStatement.setString(2, signupEmailTextField.getText() );
            preparedStatement.setString(3, signupPasswordTextField.getText() );

            // execute the query
            preparedStatement.execute();

            // close the connections
            connection.close();
            preparedStatement.close();

            // clear signup form
            this.signupUsernameTextField.setText( "" );
            this.signupEmailTextField.setText( "" );
            this.signupPasswordTextField.setText( "" );
            this.signupMessageLabel.setText( "" );


        }catch (SQLException e){

            System.err.println(e);

        }

    }

}
