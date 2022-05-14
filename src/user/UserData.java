package user;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class UserData { // CLASS TO ADD STUDENTS


    private final StringProperty PRODUCT_ID;
    private final StringProperty PRODUCT_NAME;
    private final StringProperty CATEGORY;
    private final StringProperty PRICE;
    private HBox BUTTON_HBOX;
    private Button editButton;
    private Button deleteButton;


    public UserData(String productId, String productName, String category, String price, Button editButton, Button deleteButton){

        this.PRODUCT_ID = new SimpleStringProperty( productId );
        this.PRODUCT_NAME = new SimpleStringProperty( productName );
        this.CATEGORY = new SimpleStringProperty( category );
        this.PRICE = new SimpleStringProperty( price );
        this.editButton = editButton /*new Button("Edit")*/;
        this.deleteButton = deleteButton/*new Button("Delete")*/;

        /*editButton.setOnAction( event -> {

            UserController.editScreen();
        } );*/
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
}

