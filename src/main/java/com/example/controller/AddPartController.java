package com.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert.AlertType;

/** A class that allows a user to add parts.
    *FUTURE ENHANCEMENT: Build class to check for certain exception to reduce repeat code.
    *
    *FUTURE ENHANCEMENT: Build out exception handling to prevent duplicate machine ID's.
    *
    *RUNTIME ERROR: The following lines of code didn't prevent the user from toggling multiple radio buttons so, I addressed it in AddPart.fxml.
        *ToggleGroup manufacturedSource = new ToggleGroup();
        *RadioButton addPartInHouse = new RadioButton("In-House");
        *addPartInHouse.setToggleGroup(manufacturedSource);
        *RadioButton addPartOutsourced = new RadioButton("Outsourced");
        *addPartInHouse.setToggleGroup(manufacturedSource);
        *addPartOutsourced.setToggleGroup(manufacturedSource);
    *
    *RUNTIME ERROR: "addPartCancel.getScene().getWindow().hide();" wouldn't take me back to the main screen. I had
    *placed it right after the if statement in the addPartCancel method.
 */

public class AddPartController implements Initializable {

    // Text area for error notifications.
    @FXML private TextArea addPartTextArea;

    //These two radio buttons are part of a toggle group.
    @FXML private RadioButton addPartInHouse;
    @FXML private RadioButton addPartOutsourced;

    //Text fields in the add part screen.
    @FXML private TextField addPartIDAutoGen;
    @FXML private TextField addPartNameInput;
    @FXML private TextField addPartInvInput;
    @FXML private TextField addPartPriceCostInput;
    @FXML private TextField addPartMaxInput;
    @FXML private TextField addPartMinInput;
    @FXML private TextField addPartSource;
    @FXML private TextField addPartSourceInput;

    //Variable to utilize in the "AddPartController."
    boolean error;

    /** A method that identifies if a part is an in-house part and then changes the associated field based on the selection of radio button.
     * @param actionEvent sets text to "Machine ID" if in-house part is selected. */
    public void onClickPartInHouse(ActionEvent actionEvent) throws IOException {
        RadioButton addPartInHouse = new RadioButton("In-House");
        this.addPartSource.setText("Machine ID");
    }

    /** A method that identifies if a part is an outsourced part and then changes the associated field based on the selection of radio button.
     * @param actionEvent sets text to "Company Name" if outsourced part is selected. */
    public void onClickPartOutsourced(ActionEvent actionEvent) throws IOException {
        RadioButton addPartOutsourced = new RadioButton("Outsourced");
        this.addPartSource.setText("Company Name");
    }

    /** A method to save a new part and data associated with that part.
     * @param actionEvent adds part and associated data. */
    public void onClickAddPartSave(ActionEvent actionEvent) throws IOException {
        int tempId = 0;
        String tempName = "";
        double tempPrice = 0.0;
        int tempStock = 0;
        int tempMin = 0;
        int tempMax = 0;
        int tempMachineID = 0;
        String tempCompanyName = "";

        try{
            if ((addPartNameInput.getText().isEmpty()) || (addPartInvInput.getText().isEmpty()) ||
                    (addPartPriceCostInput.getText().isEmpty()) || (addPartMinInput.getText().isEmpty()) || (addPartMaxInput.getText().isEmpty())){
                addPartTextArea.setWrapText(true);
                addPartTextArea.setText("All fields must be populated." + "\n");
                error = true;
            }else if(Double.parseDouble(addPartPriceCostInput.getText()) <= 0.0){
                addPartTextArea.setWrapText(true);
                addPartTextArea.appendText("Price must be a positive number and must be greater than $0.0." + "\n");
                error = true;
            }else if ((Integer.parseInt(addPartMinInput.getText()) >= Integer.parseInt(addPartMaxInput.getText()))){
                addPartTextArea.setWrapText(true);
                addPartTextArea.appendText("Min must be less than Max." + "\n");
                error = true;
            }else if ((Integer.parseInt(addPartInvInput.getText()) >= (Integer.parseInt(addPartMaxInput.getText()))) ||
                    ((Integer.parseInt(addPartInvInput.getText())) <= (Integer.parseInt(addPartMinInput.getText())))){
                addPartTextArea.setWrapText(true);
                addPartTextArea.appendText("Inventory should be between min/max quantities." + "\n");
                error = true;
            }else{error = false;}

            tempId = Integer.parseInt(addPartIDAutoGen.getText());
            tempName = addPartNameInput.getText();
            tempPrice = Double.parseDouble(addPartPriceCostInput.getText());
            tempStock = Integer.parseInt(addPartInvInput.getText());
            tempMin = Integer.parseInt(addPartMinInput.getText());
            tempMax = Integer.parseInt(addPartMaxInput.getText());

        }catch(Exception e){
            addPartTextArea.setWrapText(true);
            addPartTextArea.appendText("Error" + e + '\n');
            error = true;
        }

        if(!error){
            if (addPartInHouse.isSelected()) {
                tempMachineID = Integer.parseInt(String.valueOf(addPartSourceInput.getText()));
                InHouse inHousePart = new InHouse(tempId, tempName, tempPrice, tempStock, tempMin, tempMax, tempMachineID);
                Inventory.addPart(inHousePart);
            } else if(addPartOutsourced.isSelected()) {
                tempCompanyName = addPartSourceInput.getText();
                Outsourced outsourcedPart = new Outsourced(tempId, tempName, tempPrice, tempStock, tempMin, tempMax, tempCompanyName);
                Inventory.addPart(outsourcedPart);
            }
            Parent inventoryScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryScreen);
            Stage inventoryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        }
    }

    /** A method utilized to cancel and exit the part add screen.
     * @param actionEvent cancel add part.*/
    public void onClickAddPartCancel(ActionEvent actionEvent) throws IOException {
        Alert addPartCancelled = new Alert(AlertType.CONFIRMATION);
        addPartCancelled.setTitle("Cancellation Confirmation");
        addPartCancelled.setHeaderText("Confirm");
        addPartCancelled.setContentText("Are you sure you wish to cancel?");

        Optional<ButtonType> result = addPartCancelled.showAndWait();
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
    /** A method that overrides the superclass. In this case, the auto-generate feature for the part ID is being added. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartIDAutoGen.setText(String.valueOf(Inventory.getAllParts().size()+1));
    }
}

