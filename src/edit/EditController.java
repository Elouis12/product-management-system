package edit;

import dbUtil.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.UserController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {


    public static void editScreen(){

        try{

            Pane root = FXMLLoader.load( UserController.class.getClassLoader().getResource("/user/edit.fxml") );

            Scene editScene = new Scene( root );

            Stage editStage = new Stage();
            editStage.setScene( editScene );
            editStage.setTitle( "Edit Item" );
            editStage.show();

        }catch (IOException e){

            System.err.println( e );
        }
    }


    // UPDATE ITEM
    @FXML
    private TextField productIdEditTextField;

    @FXML
    private TextField productNameEditTextField;

    @FXML
    private TextField categoryEditTextField;

    @FXML
    private TextField priceEditTextField;


    @FXML
    private Label editItemMessageLabel;

    @FXML
    public void updateItem(){

        String productId = productIdEditTextField.getText();
        String productName = productNameEditTextField.getText();
        String category = categoryEditTextField.getText();
        String price = priceEditTextField.getText();

        // at least one field needs to be filled
        if( productId.equals("") &&
                productName.equals("") &&
                category.equals("") &&
                price.equals("")
        ){

            editItemMessageLabel.setText( "No input was given" );
            return;
        }

        Connection connection;
        PreparedStatement preparedStatement;

        try{

            connection = DBConnection.getConnection();

            String sql;

            if( !productId.equals( "" ) ){

                sql = "UPDATE products SET product_id = ?;"; // update the product ID
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productId);
                preparedStatement.execute();
            }
            if( !productName.equals( "" ) ){

                sql = "UPDATE products SET product_name = ?;"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productName);
                preparedStatement.execute();
            }
            if( !category.equals( "" ) ){

                sql = "UPDATE products SET product_category = ?;"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, category);
                preparedStatement.execute();
            }
            if( !price.equals( "" ) ){

                sql = "UPDATE products SET product_price = ?;"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, price);
                preparedStatement.execute();
            }

        }catch (SQLException e){

            System.err.println(e);
        }

    }
}
