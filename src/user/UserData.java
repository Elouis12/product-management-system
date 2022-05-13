package user;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserData { // CLASS TO ADD STUDENTS


    private final StringProperty PRODUCT_ID;
    private final StringProperty PRODUCT_NAME;
    private final StringProperty CATEGORY;
    private final StringProperty PRICE;


    public UserData(String productId, String productName, String category, String price){

        this.PRODUCT_ID = new SimpleStringProperty( productId );
        this.PRODUCT_NAME = new SimpleStringProperty( productName );
        this.CATEGORY = new SimpleStringProperty( category );
        this.PRICE = new SimpleStringProperty( price );
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



}

