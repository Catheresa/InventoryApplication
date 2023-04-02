package com.example.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** A class that shows the main inventory controller screen and allows the user to add, modify, or delete parts and products.

    RUNTIME ERROR: Placed in the catch portion of the code for the onPartSearch method.  Thought a popup box would appear, but it did not. Went with text area instead of:
        Alert onPartSearch = new Alert(Alert.AlertType.INFORMATION);
        onPartSearch.setTitle("Not a Match");
        onPartSearch.setHeaderText("Not a match");
        onPartSearch.setContentText("No such product exists");

    FUTURE ENHANCEMENT: Create a method to switch between screens to prevent repeat of code throughout program.

    FUTURE ENHANCEMENT: Create a method to make the confirmation alerts more generic to reduce repeat of code throughout program.

    FUTURE ENHANCEMENT: Allow user to select multiple rows to delete rather than a single row at a time. */
public class InventoryController implements Initializable {
    //The name or ID search fields:
    @FXML private TextField partSearch;
    @FXML private TextField productSearch;

    //The text area for error notifications:
    @FXML private TextArea textArea;

    //The parts table and columns:
    @FXML public TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryLevelCol;
    @FXML private TableColumn<Part, Double> partPriceCPUCol;

    //The product table and columns:
    @FXML public TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryLevelCol;
    @FXML private TableColumn<Product, Double> productPriceCPUCol;

    //Variables to utilize throughout the "InventoryController":
    Part selectedPart;
    Product selectedProduct;
    boolean error = false;

    /** A method that allows a user to select a part to modify by selecting a part and then pressing the modify button.
     User will be taken to the modify part screen. If no part has been selected, a message will appear in the text area of the screen.
     @param actionEvent go to modify part screen. */
    public void onClickPartModify(ActionEvent actionEvent) throws IOException{
        if(partsTable.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/pa/ModifyPart.fxml"));
            Parent modifyParent = loader.load();
            Scene modifyScene = new Scene(modifyParent);
            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partsTable.getSelectionModel().getSelectedItem());
            Stage modifyPartStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyScene);
            modifyPartStage.show();

        }else{
            textArea.setText("Please select a part to modify");
            error = true;
        }
    }

    /** A method that allows a user to select a product to modify by selecting a product and then pressing the modify button.
     User will be taken to the modify product screen. If no product has been selected, a message will appear in the text area of the screen.
     @param actionEvent go to modify product screen. */
    @FXML
    public void onClickProductModify(ActionEvent actionEvent) throws IOException {
        if (productsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/pa/ModifyProduct.fxml"));
            Parent modifyParent = loader.load();
            Scene modifyScene = new Scene(modifyParent);
            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(productsTable.getSelectionModel().getSelectedItem());
            Stage modifyProductStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            modifyProductStage.setScene(modifyScene);
            modifyProductStage.show();
        } else {
            textArea.setText("Please select a product to modify");
            error = true;
        }
    }

    /** A method that allows the user to search for a part.  If the name search doesn't result in a match, the method
     will search for the part ID. If no search result is found, a message appears in the text area.  Once user presses "Enter",
     the table repopulates.
     @param actionEvent search for part. */
    @FXML
    public void onPartSearch(ActionEvent actionEvent) {
        String partialName = partSearch.getText();
        ObservableList<Part> searchPartName = Inventory.lookupPart(partialName);
        partsTable.setItems(searchPartName);
        textArea.setText("");

        if(searchPartName.size() == 0){
            try{
                int tempID = Integer.parseInt(partialName);
                Part part = Inventory.lookupPart(tempID);
                if(part != null){
                    searchPartName.add(part);
                }else{throw new Exception();}
            }
            catch(Exception e){
                textArea.setWrapText(true);
                textArea.setText("No matching Part ID or Name found. Press 'Enter' to reload parts");
            }
        }
        partSearch.setText("");
    }

    /** A method that allows the user to search for a product.  If the name search doesn't result in a match, the method
     will search for the product ID. If no search result is found, a message appears in the text area.  Once user presses "Enter",
     the table repopulates.
     @param actionEvent search product. */
    public void onProductSearch(ActionEvent actionEvent) {
        String partialName = productSearch.getText();
        ObservableList<Product> searchProductName = Inventory.lookupProduct(partialName);
        productsTable.setItems(searchProductName);
        textArea.setText("");

        if(searchProductName.size() == 0){
            try{
                int tempID = Integer.parseInt(partialName);
                Product product = Inventory.lookupProduct(tempID);
                if(product != null){
                    searchProductName.add(product);
                }else{throw new Exception();}
            }
            catch(Exception e){
                textArea.setWrapText(true);
                textArea.setText("No matching Product ID or Name found. Press 'Enter' to reload products");
            }
        }
        productSearch.setText("");
    }
    /** A method that loads the add part screen when the user clicks on "Add" button.
     * @param actionEvent go to add part screen. */
    @FXML
    public void onClickPartAdd(ActionEvent actionEvent) throws IOException{
        Parent addPartScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartScreen);
        Stage addPartStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }
    /** A method that loads the add product screen when the user clicks on "Add" button.
     * @param actionEvent go to add product screen. */
    @FXML
    public void onClickProductAdd(ActionEvent actionEvent) throws IOException {
        Parent addProductScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductScreen);
        Stage addProductStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }
    /** A method that deletes the selected part when user clicks the delete button and confirms they wish to delete.
     * @param actionEvent delete part from the parts table. */
    @FXML
    public void onClickPartDelete(ActionEvent actionEvent) {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if((Inventory.deletePart(selectedPart))){
            partsTable.getItems().removeAll(partsTable.getSelectionModel().getSelectedItem());
        }
    }
    /** A method that deletes the selected product when user clicks the delete button and confirms they wish to delete.
     * @param actionEvent product from the products table */
    @FXML
    public void onClickProductDelete(ActionEvent actionEvent) {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if(!selectedProduct.getAllAssociatedParts.isEmpty()){
            textArea.setWrapText(true);
            textArea.setText("All associated parts must be removed before product can be deleted.");
        }else{
            Inventory.deleteProduct(selectedProduct);
            productsTable.getItems().removeAll(productsTable.getSelectionModel().getSelectedItem());
        }
    }
    /** A method that allows the user to exit the screen by clicking on the "Exit" button.
     * @param actionEvent exit program. */
    public void onClickExitScreen(ActionEvent actionEvent) {
        Platform.exit();
    }
    /** A method that overrides the superclass.  In this case, it's setting the parts table and the product table up to get data. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}