package com.example.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/** A main class that launches the program.  */
public class Main extends Application {
    /** A method that overrides the superclass taking user to the main inventory controller screen.
     * @param stage go to inventory screen. */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/pa/Inventory.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.show();
    }
    /** A main method that creates parts and product objects as well as launches the program. */
    public static void main(String[] args) {
        InHouse inHousePart1 = new InHouse(1,"Brakes",22.99, 7, 1, 20, 100);
        InHouse inHousePart2 = new InHouse(2, "Tire", 35.99, 9, 1, 15, 200);
        Outsourced outsourcedPart1 = new Outsourced(3, "Saddle", 15.99, 12, 1, 15,"TD Supply");
        Outsourced outsourcedPart2 = new Outsourced(4, "Big Horn", 15.99, 10, 1, 15,"Johnson Brothers Supply");

        Inventory.addPart(inHousePart1);
        Inventory.addPart(inHousePart2);
        Inventory.addPart(outsourcedPart1);
        Inventory.addPart(outsourcedPart2);

        Product product1 = new Product(1, "Giant Bicycle", 299.99, 300, 200, 400);
        Product product2 = new Product(2, "Scott Bicycle", 199.99, 300, 200, 400);
        Product product3 = new Product(3, "GT Bicycle", 99.99, 250, 200, 400);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        launch(args);
    }



}