package user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductFilterSearchBy {

    private static final ObservableList<String> searchByOptions = FXCollections.observableArrayList("Product Id", "Product Name", "Product Category", "Product Price");

    public static ObservableList<String> getSearchByOptions() {

        return searchByOptions;
    }
}
