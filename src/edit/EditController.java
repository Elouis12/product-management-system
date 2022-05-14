package edit;

import dbUtil.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.UserController;
import user.ProductData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {

    private static boolean updated = false;

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
    public Label editItemMessageLabel;

    @FXML
    public void updateItem(){

        System.out.println("USER DATA PASSED IS " + ProductData.getId() );

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

                sql = "UPDATE products SET product_id = ? WHERE id = '" + ProductData.getId() + "';"; // update the product ID
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productId);
                preparedStatement.execute();
            }
            if( !productName.equals( "" ) ){

                sql = "UPDATE products SET product_name = ? WHERE id = '" + ProductData.getId() + "';"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, productName);
                preparedStatement.execute();
            }
            if( !category.equals( "" ) ){

                sql = "UPDATE products SET product_category = ? WHERE id = '" + ProductData.getId() + "';"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, category);
                preparedStatement.execute();
            }
            if( !price.equals( "" ) ){

                sql = "UPDATE products SET product_price = ? WHERE id = '" + ProductData.getId() + "';"; // update the product Name
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, price);
                preparedStatement.execute();
            }

            editItemMessageLabel.setText( "" );

            // using methods from other controllers without using static
            FXMLLoader loader = new FXMLLoader( getClass().getClassLoader().getResource("./user/user.fxml"));
            loader.load();
            UserController controller = loader.getController();
            controller.loadItems();

        }catch (SQLException | IOException e){

            System.err.println(e);
        }

    }
}
