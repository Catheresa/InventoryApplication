package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/** A class for products. */
public class Product{
    /** Observable list to house associated parts to a product*/
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    //Product variables:
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Product constructor:
    public Product(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {return id;}

    /**
     * @param id the id to set
     */
    public void setId(int id) {this.id = id;}

    /**
     * @return the name
     */
    public String getName() {return name;}

    /**
     * @param name the name to set
     */
    public void setName(String name) {this.name = name;}

    /**
     * @return the price
     */
    public double getPrice() {return price;}

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * @return the stock
     */
    public int getStock() {return stock;}

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {this.stock = stock;}

    /**
     * @return the min
     */
    public int getMin() {return min;}

    /**
     * @param min the min to set
     */
    public void setMin(int min) {this.min = min;}

    /**
     * @return the max
     */
    public int getMax() {return max;}

    /**
     * @param max the max to set
     */
    public void setMax(int max) {this.max = max;}


    /** Observable list to obtain all associated parts to a product. */
    public final ObservableList<Part> getAllAssociatedParts = FXCollections.observableArrayList();

    /** Method to add an associated part to a product.
     * @param part get and add part. */
    public void addAssociatedPart(Part part){
        getAllAssociatedParts.add(part);
    }
    /** Method to confirm deletion of an associated part from a product is ok.
     * @param partId confirm if associated part should be deleted. */
    public boolean deleteAssociatedPart(int partId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Are you sure you wish to remove the associated part?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
