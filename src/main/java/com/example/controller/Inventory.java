package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/** The inventory class which houses observable lists for all parts and products and contains methods that allow functionality
    to add, delete, and modify parts and products.

    LOGIC ERROR: lookupPart(int partID) Attempted to create a popup alert to notify user when no match was found but went with a text area box instead as it didn't work as intended.
    public static int lookupPart(int partID){
    ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
    ObservableList<Part> allParts = getAllParts();
    for(Part searchedPart : allParts){
        if(searchedPart.getId().contains(partID)){
            Alert addPartCancelled = new Alert(Alert.AlertType.INFORMATION);
            addPartCancelled.setTitle("Part ID - No Match");
            addPartCancelled.setHeaderText("Not a match");
            addPartCancelled.setContentText("No such Part ID exists");
        }else{
        continue;
        }
    }
    return partID;
 }
    RUNTIME ERROR: Initially passed the partID instead of the index which caused the wrong part to update.
 */
public class Inventory {
    /** The creation of the allParts and all Products observable lists. */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** A method that adds parts to the allParts observable list.
     * @param part add to allParts observable list. */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** A method that adds products to the allProducts observable list.
     * @param product add to allProducts observable list. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** A method to iterate through a part list and grab all parts meeting ID criteria provided.
     * @param partID look up part by ID. */
    public static Part lookupPart(int partID) {
        ObservableList<Part> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part searchedPart = allParts.get(i);

            if (searchedPart.getId() == partID) {
                return searchedPart;
            }
        }
        return null;
    }

    /** A method to iterate through a product list and grab all products meeting ID criteria provided.
     * @param productID look up product by ID.*/
    public static Product lookupProduct(int productID) {
        ObservableList<Product> allProducts = getAllProducts();

        for (Product searchedProduct : allProducts) {
            if (searchedProduct.getId() == productID) {
                return searchedProduct;
            }
        }
        return null;
    }

    /** A method to iterate through a part list and grab all part meeting Name criteria provided.
     * @param partialName look up part by name. */
    public static ObservableList<Part> lookupPart(String partialName) {
        ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for (Part searchedPart : allParts) {
            if (searchedPart.getName().contains(partialName)) {
                filteredPartList.add(searchedPart);
            }
        }
        return filteredPartList;
    }

    /** A method to iterate through a product list and grab all product meeting Name criteria provided.
     * @param partialName look up product by name.*/
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for (Product searchedProduct : allProducts) {
            if (searchedProduct.getName().contains(partialName)) {
                filteredProductList.add(searchedProduct);
            }
        }
        return filteredProductList;
    }

    /** A method that updates a selected part based on selection and index.
     * @param selectedPart update part in allParts observable list. */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
     }

    /** A method that updates a selected product based on selection and index.
     * @param newProduct update part in allProducts observable list. */
    public static void updateProduct ( int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /** A method that identifies whether a part should be deleted.
     * @return part boolean. */
    public static boolean deletePart (Part part) {
        Alert partDeletion = new Alert(Alert.AlertType.CONFIRMATION);
        partDeletion.setTitle("Part Deletion");
        partDeletion.setHeaderText("Delete");
        partDeletion.setContentText("Are you sure you wish to delete selected part?");

        Optional<ButtonType> result = partDeletion.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /** A method that identifies whether a product should be deleted.
     * @return product boolean.*/
    public static boolean deleteProduct (Product product){
        Alert productDeletion = new Alert(Alert.AlertType.CONFIRMATION);
        productDeletion.setTitle("Product Deletion");
        productDeletion.setHeaderText("Delete");
        productDeletion.setContentText("Are you sure you wish to delete selected product?");

        Optional<ButtonType> result = productDeletion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            return true;
        }else{return false;}
    }

    /** An observable list that obtains all parts. */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** An observable list that obtains all products. */
    public static ObservableList<Product> getAllProducts () {
        return allProducts;
    }

}
