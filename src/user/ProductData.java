package user;

import edit.EditController;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class ProductData { // CLASS TO ADD STUDENTS


    private final StringProperty PRODUCT_ID;
    private final StringProperty PRODUCT_NAME;
    private final StringProperty CATEGORY;
    private final StringProperty PRICE;
    private HBox BUTTON_HBOX;
    private Button editButton;
    private Button deleteButton;

    private static String id; // id of the product, not the product_id itself


    public ProductData(String productId, String productName, String category, String price, String id){

        this.PRODUCT_ID = new SimpleStringProperty( productId );
        this.PRODUCT_NAME = new SimpleStringProperty( productName );
        this.CATEGORY = new SimpleStringProperty( category );
        this.PRICE = new SimpleStringProperty( price );
        this.editButton = new Button("Edit");
        this.deleteButton = new Button("Delete");

        editButton.setUserData(id); // save the id of the product where it's at in the table
        editButton.setOnAction( event -> {

            System.out.println( editButton.getUserData() );
            ProductData.id = editButton.getUserData().toString(); // pass that id to the update button
            System.out.println( getId() );
            EditController.editScreen();

        } );
        BUTTON_HBOX = new HBox();
        BUTTON_HBOX.getChildren().addAll(editButton, deleteButton);
    }


    public String getPRODUCT_ID() {
        return PRODUCT_ID.get();
    }

    public StringProperty PRODUCT_IDProperty() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(String PRODUCT_ID) {
        this.PRODUCT_ID.set(PRODUCT_ID);
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME.get();
    }

    public StringProperty PRODUCT_NAMEProperty() {
        return PRODUCT_NAME;
    }

    public void setPRODUCT_NAME(String PRODUCT_NAME) {
        this.PRODUCT_NAME.set(PRODUCT_NAME);
    }

    public String getCATEGORY() {
        return CATEGORY.get();
    }

    public StringProperty CATEGORYProperty() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY.set(CATEGORY);
    }

    public String getPRICE() {
        return PRICE.get();
    }

    public StringProperty PRICEProperty() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE.set(PRICE);
    }


    public void setEditButton(Button editButton) {

        this.editButton = editButton;
    }

    public Button getEditButton() {

        return editButton;
    }

    public void setDeleteButton(Button deleteButton) {

        this.deleteButton = deleteButton;
    }

    public Button getDeleteButton() {

        return deleteButton;
    }


    public void setBUTTON_HBOX(HBox hbox) {

        this.BUTTON_HBOX = hbox;
    }

    public HBox getBUTTON_HBOX() {

        return BUTTON_HBOX;
    }

    public static String getId() {
        return id;
    }

}

