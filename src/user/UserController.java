package user;

import dbUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
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
    private TableView<ProductData> productTable;

    @FXML
    private TableColumn<ProductData, String> productIdColumn;

    @FXML
    private TableColumn<ProductData, String> productNameColumn;

    @FXML
    private TableColumn<ProductData, String> categoryColumn;

    @FXML
    private TableColumn<ProductData, String> priceColumn;

    @FXML
    private TableColumn<ProductData, String> actionsColumn;

    private ObservableList<ProductData> dataForTable;


    @FXML
    private ComboBox<String> addItemComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.addItemComboBox.setItems( ProductFilterSearchBy.getSearchByOptions() );
        loadItems();
    }

    public void loadItems(){

        String sql = "SELECT * FROM products WHERE product_owner = '" + UserModel.getUserId() + "';";

        try {

            Connection connection = DBConnection.getConnection();
            this.dataForTable = FXCollections.observableArrayList();

//            ResultSet resultSet = connection.createStatement().executeQuery(sql); // gets the data
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery(); // gets the data

            while( resultSet.next() ){ // has anything .. going through each row

                this.dataForTable.add( new ProductData( resultSet.getString(1), // product id
                        resultSet.getString(3), // product name
                        resultSet.getString(4), // category name
                        resultSet.getString(5),// price
                        resultSet.getString(6) // general id
                        // the two buttons don't have to be added since it will in
                        // the constructor
                ) );

            }

            connection.close();
            resultSet.close();

        }catch (SQLException e){

            System.err.println("Error " + e);
        }

        this.productIdColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRODUCT_ID"));
        this.productNameColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRODUCT_NAME"));
        this.categoryColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("CATEGORY"));
        this.priceColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRICE"));
        this.actionsColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("BUTTON_HBOX") );

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
        ResultSet resultSet;

        try{

            String sql = "SELECT product_id FROM products WHERE product_owner = '" + UserModel.getUserId() + "' AND product_id = '" + productId + "';";
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if( resultSet.next() ){ // ID ALREADY EXISTS
                addItemMessageLabel.setText( "Product Id already exists" );
                return;
            }

            sql = "INSERT INTO products( product_id, product_owner, product_name, product_category, product_price ) VALUES( ?, ?, ?, ?, ? );";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productId);
            preparedStatement.setString(2, UserModel.getUserId() );
            preparedStatement.setString(3, productName);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, price);

            preparedStatement.execute();

            loadItems(); // show the newly added item

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

    @FXML
    public void editScreen(){

        try{
//            Pane root = FXMLLoader.load( UserController.class.getClassLoader().getResource("./user/edit.fxml") );
            Pane root = FXMLLoader.load( getClass().getClassLoader().getResource("./edit/edit.fxml") );

            Scene editScene = new Scene( root );

            Stage editStage = new Stage();
            editStage.setScene( editScene );
            editStage.setTitle( "Edit Item" );
            editStage.initModality( Modality.APPLICATION_MODAL );
            editStage.show();

        }catch (IOException e){

            System.err.println( e );
        }
    }


    @FXML
    public void deleteItem(){

        Connection connection;
        PreparedStatement preparedStatement;

        try{

            connection = DBConnection.getConnection();

            String sql = "DELETE FROM products WHERE id = '" + ProductData.getId() + "';";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            loadItems();

            connection.close();
            preparedStatement.close();

        }catch (SQLException e){

            System.err.println(e);
        }

    }


    @FXML
    private TextField searchByTextField;

    @FXML
    private Label searchMessageLabel;

    @FXML
    private void searchProduct(){


        try{

            String userComboOption = this.addItemComboBox.getValue().toLowerCase();

            String searchBy = userComboOption.replace(" ", "_"); // represents what's how it's in the table with _

            clearTable(); // so it doesn't keep adding to the table

            String sqlSearch = "SELECT * FROM products WHERE product_owner = '" + UserModel.getUserId() + "' AND '" + searchBy + "' LIKE ?"; // sql search query


            Connection connection = DBConnection.getConnection(); // establish a connection

            PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch); //
            preparedStatement.setString( 1, "%" + this.searchByTextField.getText() + "%" ); // input text

            ResultSet resultSet = preparedStatement.executeQuery();

            while( resultSet.next() ){ // has anything .. going through each row

                this.dataForTable.add( new ProductData( resultSet.getString(1), // product id
                        resultSet.getString(3), // product name
                        resultSet.getString(4), // category
                        resultSet.getString(5), // price
                        resultSet.getString(6) // id
                ) );

            }

            connection.close();
            resultSet.close();

            searchMessageLabel.setText( "" );

        }catch (SQLException e ){

            System.err.println(e);

        }catch (NullPointerException e){

            searchMessageLabel.setText(  "Please select a search filter" );
        }

        // load it to the table
        this.productIdColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRODUCT_ID"));
        this.productNameColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRODUCT_NAME"));
        this.categoryColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("CATEGORY"));
        this.priceColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("PRICE"));
        this.actionsColumn.setCellValueFactory( new PropertyValueFactory<ProductData, String>("BUTTON_HBOX") );

        this.productTable.setItems(this.dataForTable); // put in that list to the table

    }

    @FXML
    public void logout(){

        try {

            Parent root = (Parent) FXMLLoader.load( getClass().getClassLoader().getResource("./login/login.fxml") );
            Scene scene = new Scene( root );

            Stage userStage = (Stage) productIdTextField.getScene().getWindow(); // using a componenet from the stage to close it
            userStage.close();

            Stage loginStage = new Stage();
            loginStage.setScene( scene );
            loginStage.setTitle( "Login" );
            loginStage.show();

        }catch (IOException e){

            System.err.println(e);
        }
    }


    private void clearTable(){ // for the search functionality

        this.productTable.getItems().clear();
    }

}
