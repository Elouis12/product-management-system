package login;

import dbUtil.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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

        if( loginModel.isLoggedIn( this.loginUsernameTextField.getText(), this.loginPasswordTextField.getText() ) ){

            // remove the login screen
            Stage loginScreen = (Stage) this.loginUsernameTextField.getScene().getWindow();
            loginScreen.close();

            // show the user screen after logging in
            userLogin();

        }else{

            this.loginMessageLabel.setText( "Username or password is incorrect" );
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
}
