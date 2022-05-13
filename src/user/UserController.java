package user;

import dbUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    // ADD ITEMS FORM
    @FXML
    private TextField productIdTextField;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private Label addItemMessageLabel;

    // SEARCH FORM

    // TABLE
    @FXML
    private TableView<UserData> productTable;

    @FXML
    private TableColumn<UserData, String> productIdColumn;

    @FXML
    private TableColumn<UserData, String> productNameColumn;

    @FXML
    private TableColumn<UserData, String> categoryColumn;

    @FXML
    private TableColumn<UserData, String> priceColumn;

    @FXML
    private TableColumn<UserData, String> actionsColumn;

    private ObservableList<UserData> dataForTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadItems();
    }

    public void loadItems(){

        String sql = "SELECT * FROM products";

        try {

            Connection connection = DBConnection.getConnection();
            this.dataForTable = FXCollections.observableArrayList();

//            ResultSet resultSet = connection.createStatement().executeQuery(sql); // gets the data
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery(); // gets the data

            while( resultSet.next() ){ // has anything .. going through each row

                this.dataForTable.add( new UserData( resultSet.getString(1), // id
                        resultSet.getString(3), // product name
                        resultSet.getString(4), // category name
                        resultSet.getString(5)// price
                ) );

            }

            connection.close();
            resultSet.close();

        }catch (SQLException e){

            System.err.println("Error " + e);
        }

        this.productIdColumn.setCellValueFactory( new PropertyValueFactory<UserData, String>("PRODUCT_ID"));
        this.productNameColumn.setCellValueFactory( new PropertyValueFactory<UserData, String>("PRODUCT_NAME"));
        this.categoryColumn.setCellValueFactory( new PropertyValueFactory<UserData, String>("CATEGORY"));
        this.priceColumn.setCellValueFactory( new PropertyValueFactory<UserData, String>("PRICE"));
//        this.actionsColumn.setCellValueFactory( new PropertyValueFactory<UserData, String>("DATE_OF_BIRTH"));

//        this.productTable.setItems(null);
        this.productTable.setItems(this.dataForTable); // put in that list to the table

    }

    @FXML
    public void addItem(){

        String productId = productIdTextField.getText();
        String productName = productNameTextField.getText();
        String category = categoryTextField.getText();
        String price = priceTextField.getText();

        if( productId.equals("") ||
            productName.equals("") ||
            category.equals("") ||
            price.equals("")
            ){

            addItemMessageLabel.setText( "Make sure all fields are filled" );
            return;
        }

        Connection connection;
        PreparedStatement preparedStatement;

        try{

            String sql = "INSERT INTO products( product_id, product_owner, product_name, product_category, product_price ) VALUES( ?, ?, ?, ?, ? );";

            connection = DBConnection.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productId);
            preparedStatement.setString(2, UserModel.getUserId() );
            preparedStatement.setString(3, productName);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, price);

            preparedStatement.execute();

            clearForm(); // clear the text fields and  message label

        }catch (SQLException e){

            System.err.println(e);
        }

    }

    @FXML
    public void clearForm(){

        this.productIdTextField.setText( "" );
        this.productNameTextField.setText( "" );
        this.categoryTextField.setText( "" );
        this.priceTextField.setText( "" );
        this.addItemMessageLabel.setText( "" );
    }
}
