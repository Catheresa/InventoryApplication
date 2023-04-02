package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**  A class that allows modifications to products. */
public class ModifyProductController implements Initializable {
    //Name or ID search field:
    @FXML private TextField modifyProductPartSearchInput;

    //Text area for error notifications:
    @FXML private TextArea modifyProductTextArea;

    //The parts table and associated columns:
    @FXML private TableView<Part> modifyProductPartsTable;
    @FXML private TableColumn<Part, Integer> modifyProductPartID1;
    @FXML private TableColumn<Part, String> modifyProductPartName1;
    @FXML private TableColumn<Part, Integer> modifyProductPartInv1;
    @FXML private TableColumn<Part, Double> modifyProductPartPrice1;

    //The associated parts table and fields:
    @FXML private TableView<Part> associatedPartsTable;
    @FXML private TableColumn<Part, Integer> modifyProductPartID2;
    @FXML private TableColumn<Part, String> modifyProductPartName2;
    @FXML private TableColumn<Part, Integer> modifyProductPartInv2;
    @FXML private TableColumn<Part, Double> modifyProductPartPrice2;

    //Input fields to modify products:
    @FXML private TextField modifyProductIDAutoGen;
    @FXML private TextField modifyProductNameInput;
    @FXML private TextField modifyProductInvInput;
    @FXML private TextField modifyProductPriceInput;
    @FXML private TextField modifyProductMaxInput;
    @FXML private TextField modifyProductMinInput;
    @FXML private TextField modifyProductName;

    /** An observable list to house a unique list of parts that get associated to products. */
    ObservableList<Part> uniqueList = FXCollections.observableArrayList();
    //Variables to utilize throughout the "ModifyProductController."
    Product identifiedProduct;
    int identifyProductIndex;
    boolean error;

    /** A method utilized to send product data to the "ModifyProduct" screen.
     * @param product send product to the modify product screen.*/
    public void sendProduct(Product product){
        identifiedProduct = product;
        modifyProductIDAutoGen.setText(String.valueOf(product.getId()));
        modifyProductNameInput.setText(product.getName());
        modifyProductInvInput.setText(String.valueOf(product.getStock()));
        modifyProductPriceInput.setText(String.valueOf(product.getPrice()));
        modifyProductMaxInput.setText(String.valueOf(product.getMax()));
        modifyProductMinInput.setText(String.valueOf(product.getMin()));
        uniqueList.addAll(identifiedProduct.getAllAssociatedParts);
    }
    /** A method to save product data that has been modified.
     * @param actionEvent save modified product data. */
    @FXML
    void onModifyProductSave(ActionEvent actionEvent) throws IOException {
        String tempName = "";
        double tempPrice = 0.0;
        int tempStock = 0;
        int tempMin = 0;
        int tempMax = 0;
        try{
            if ((modifyProductNameInput.getText().isEmpty()) || (modifyProductInvInput.getText().isEmpty()) ||
                    (modifyProductPriceInput.getText().isEmpty()) || (modifyProductMinInput.getText().isEmpty()) || (modifyProductMaxInput.getText().isEmpty())){
                modifyProductTextArea.setWrapText(true);
                modifyProductTextArea.setText("All fields must be populated." + "\n");
                error = true;
            }else if(Double.parseDouble(modifyProductPriceInput.getText()) <= 0.0){
                modifyProductTextArea.setWrapText(true);
                modifyProductTextArea.appendText("Price must be a positive number and must be greater than $0.0." + "\n");
                error = true;
            }else if ((Integer.parseInt(modifyProductMinInput.getText()) >= Integer.parseInt(modifyProductMaxInput.getText()))){
                modifyProductTextArea.setWrapText(true);
                modifyProductTextArea.appendText("Min must be less than Max." + "\n");
                error = true;
            }else if ((Integer.parseInt(modifyProductInvInput.getText()) >= (Integer.parseInt(modifyProductMaxInput.getText()))) ||
                    ((Integer.parseInt(modifyProductInvInput.getText())) <= (Integer.parseInt(modifyProductMinInput.getText())))){
                modifyProductTextArea.setWrapText(true);
                modifyProductTextArea.appendText("Inventory should be between min/max quantities." + "\n");
                error = true;
            }else{error = false;}

            tempName = modifyProductNameInput.getText();
            tempPrice = Double.parseDouble(modifyProductPriceInput.getText());
            tempStock = Integer.parseInt(modifyProductInvInput.getText());
            tempMin = Integer.parseInt(modifyProductMinInput.getText());
            tempMax = Integer.parseInt(modifyProductMaxInput.getText());
        }catch(Exception e){
            modifyProductTextArea.setWrapText(true);
            modifyProductTextArea.appendText("Error: " + e.getMessage() + '\n');
            error = true;
        }

        if(!error){
            identifiedProduct = new Product(identifiedProduct.getId(), tempName,tempPrice, tempStock, tempMin, tempMax);
            identifiedProduct.getAllAssociatedParts.addAll(uniqueList);
            Inventory.updateProduct(Integer.parseInt(modifyProductIDAutoGen.getText())-1, identifiedProduct);

            Parent inventoryScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryScreen);
            Stage inventoryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        }
    }
    /** A method to add an associated part to an existing product.
     * @param actionEvent add part to product in the modify product screen. */
    @FXML
    public void onClickAddModifyProductPart(ActionEvent actionEvent) {
        Part selectedPart = modifyProductPartsTable.getSelectionModel().getSelectedItem();
        uniqueList.add(selectedPart);
    }
    /** A method to search for parts in the modify product screen.
     * @param mouseEvent search part in the modify product screen. */
    public void onModifyProductPartSearchInput(ActionEvent mouseEvent) {
        String partialName = modifyProductPartSearchInput.getText();
        ObservableList<Part> searchProductPartName = Inventory.lookupPart(partialName);
        modifyProductPartsTable.setItems(searchProductPartName);
        modifyProductTextArea.setText("");

        if(searchProductPartName.size() == 0){
            try{
                int tempID = Integer.parseInt(partialName);
                Part part = Inventory.lookupPart(tempID);
                if(part != null){
                    searchProductPartName.add(part);
                }else{throw new Exception();}
            }
            catch(Exception e){
                modifyProductTextArea.setWrapText(true);
                modifyProductTextArea.setText("No matching Product ID or Name found. Press 'Enter' to reload products");
            }
        }
        modifyProductPartSearchInput.setText("");

    }
    /** A method to remove associated parts in the modify product screen.
     * @param event remove associated part from product in the modify product screen. */
    @FXML
    void onModifyProductRemoveAssociatedPart(ActionEvent event) {
        Part selectedAssociatedPart;
        try{
            selectedAssociatedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
            identifiedProduct.getAllAssociatedParts.remove(selectedAssociatedPart);
            uniqueList.remove(selectedAssociatedPart);
            if(selectedAssociatedPart == null){
                throw new NullPointerException();
            }
        }catch(NullPointerException npe){
            modifyProductTextArea.setText("No associated part was selected.");
        }
    }
    /** A method utilize to exit the product modification screen.
     * @param actionEvent cancel product modification and go back to inventory screen. */
    @FXML
    void onModifyProductCancel(ActionEvent actionEvent) throws IOException {
        Alert modifyProductCancelled = new Alert(Alert.AlertType.CONFIRMATION);
        modifyProductCancelled.setTitle("Cancellation Confirmation");
        modifyProductCancelled.setHeaderText("Please Confirm");
        modifyProductCancelled.setContentText("Are you sure you wish to cancel?");

        Optional<ButtonType> result = modifyProductCancelled.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent inventoryScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryScreen);
            Stage inventoryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        }
    }
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    /** A method that overrides the superclass.  In this case, the products part table and associated parts table are populated utilizing this method.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductPartsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(uniqueList);

        modifyProductPartID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartInv1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductPartID2.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartInv2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

