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
/** A class that allows modifications to parts.
   *FUTURE ENHANCEMENT: Build class to check for certain exception to reduce repeat code. */
public class ModifyPartController implements Initializable {
    //Text area for error notifications
    @FXML private TextArea modifyPartTextArea;

    //These two radio buttons are part of a toggle group.
    @FXML private RadioButton modifyPartOutsourced;
    @FXML private RadioButton modifyPartInHouse;

    //User input fields to modify parts.
    @FXML private TextField modifyPartIDAutoGen;
    @FXML private TextField modifyPartNameInput;
    @FXML private TextField modifyPartInvInput;
    @FXML private TextField modifyPartPriceCostInput;
    @FXML private TextField modifyPartMaxInput;
    @FXML private TextField modifyPartMinInput;
    @FXML private TextField modifyPartSourceInput;
    @FXML private TextField modifyPartSource;

    //Variables to utilize throughout the "ModifyPartController."
    Part identifiedPart;
    int identifyPartIndex;
    boolean error;

    /** A method utilized to send part data to the "ModifyPart" screen.
     * @param part send part to the modify part screen. */
    public void sendPart(Part part){
        identifiedPart = part;
        modifyPartIDAutoGen.setText(String.valueOf(identifiedPart.getId()));
        modifyPartNameInput.setText(identifiedPart.getName());
        modifyPartInvInput.setText(String.valueOf(identifiedPart.getStock()));
        modifyPartPriceCostInput.setText(String.valueOf(identifiedPart.getPrice()));
        modifyPartMaxInput.setText(String.valueOf(identifiedPart.getMax()));
        modifyPartMinInput.setText(String.valueOf(identifiedPart.getMin()));
        if(identifiedPart instanceof InHouse) {
           this.modifyPartSource.setText("Machine ID");
           modifyPartSourceInput.setText(String.valueOf(((InHouse) identifiedPart).getMachineID()));
           modifyPartInHouse.setSelected(true);
        }else if(identifiedPart instanceof Outsourced) {
           this.modifyPartSource.setText("Company Name");
           modifyPartSourceInput.setText(String.valueOf(((Outsourced) identifiedPart).getCompanyName()));
           modifyPartOutsourced.setSelected(true);
        }
    }

    /** A method utilize to cancel and exit the part modification screen.
     * @param actionEvent cancel part modification and go back to inventory screen. */
    @FXML
    void onClickModifyPartCancel(ActionEvent actionEvent) throws IOException {
        Alert modifyPartCancelled = new Alert(Alert.AlertType.CONFIRMATION);
        modifyPartCancelled.setTitle("Cancellation Confirmation");
        modifyPartCancelled.setHeaderText("Please Confirm");
        modifyPartCancelled.setContentText("Are you sure you wish to cancel?");

        Optional<ButtonType> result = modifyPartCancelled.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent inventoryScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryScreen);
            Stage inventoryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        }
    }
    /** A method that identifies if a part is an in-house part and then changes the associated field based on the selection of radio button.
     * @param event sets text as "Machine ID" if addPartInHouse radio button has been selected.
     */
    @FXML
    void onClickModifyPartInHouse(ActionEvent event) {
        RadioButton addPartInHouse = new RadioButton("In-House");
        this.modifyPartSource.setText("Machine ID");
    }

    /** A method that identifies if a part is an outsourced part and then changes the associated field based on the selection of radio button.
     * @param event sets text as "Company Name" if addPartOutsourced radio button has been selected.
     */
    @FXML
    void onClickModifyPartOutsourced(ActionEvent event) {
        RadioButton addPartOutsourced = new RadioButton("Outsourced");
        this.modifyPartSource.setText("Company Name");
    }

    /** A method to save part data that has been modified.
     * @param actionEvent save modified part data. */
    @FXML
    void onClickModifyPartSave(ActionEvent actionEvent) throws IOException {
        int tempId = 0;
        String tempName = "";
        double tempPrice = 0.0;
        int tempStock = 0;
        int tempMin = 0;
        int tempMax = 0;
        int tempMachineID = 0;
        String tempCompanyName = "";

        try{
            if ((modifyPartNameInput.getText().isEmpty()) || (modifyPartInvInput.getText().isEmpty()) ||
                    (modifyPartPriceCostInput.getText().isEmpty()) || (modifyPartMinInput.getText().isEmpty()) || (modifyPartMaxInput.getText().isEmpty())){
                modifyPartTextArea.setWrapText(true);
                modifyPartTextArea.setText("All fields must be populated." + "\n");
                error = true;
            }else if(Double.parseDouble(modifyPartPriceCostInput.getText()) <= 0.0){
                modifyPartTextArea.setWrapText(true);
                modifyPartTextArea.appendText("Price must be a positive number and must be greater than $0.0." + "\n");
                error = true;
            }else if ((Integer.parseInt(modifyPartMinInput.getText()) >= Integer.parseInt(modifyPartMaxInput.getText()))){
                modifyPartTextArea.setWrapText(true);
                modifyPartTextArea.appendText("Min must be less than Max." + "\n");
                error = true;
            }else if ((Integer.parseInt(modifyPartInvInput.getText()) >= (Integer.parseInt(modifyPartMaxInput.getText()))) ||
                    ((Integer.parseInt(modifyPartInvInput.getText())) <= (Integer.parseInt(modifyPartMinInput.getText())))){
                modifyPartTextArea.setWrapText(true);
                modifyPartTextArea.appendText("Inventory should be between min/max quantities." + "\n");
                error = true;
            }else{error = false;}

            tempId = Integer.parseInt(modifyPartIDAutoGen.getText());
            tempName = modifyPartNameInput.getText();
            tempPrice = Double.parseDouble(modifyPartPriceCostInput.getText());
            tempStock = Integer.parseInt(modifyPartInvInput.getText());
            tempMin = Integer.parseInt(modifyPartMinInput.getText());
            tempMax = Integer.parseInt(modifyPartMaxInput.getText());

            if(modifyPartInHouse.isSelected()){
                identifiedPart = new InHouse(Integer.parseInt(modifyPartIDAutoGen.getText()),
                        modifyPartNameInput.getText(),
                        Double.parseDouble(modifyPartPriceCostInput.getText()),
                        Integer.parseInt(modifyPartInvInput.getText()),
                        Integer.parseInt(modifyPartMinInput.getText()),
                        Integer.parseInt(modifyPartMaxInput.getText()),
                        Integer.parseInt(modifyPartSourceInput.getText()));
            }else if(modifyPartOutsourced.isSelected()){
                identifiedPart = new Outsourced(Integer.parseInt(modifyPartIDAutoGen.getText()),
                        modifyPartNameInput.getText(),
                        Double.parseDouble(modifyPartPriceCostInput.getText()),
                        Integer.parseInt(modifyPartInvInput.getText()),
                        Integer.parseInt(modifyPartMinInput.getText()),
                        Integer.parseInt(modifyPartMaxInput.getText()),
                        modifyPartSourceInput.getText());
            }
        }catch(Exception e){
            modifyPartTextArea.setWrapText(true);
            modifyPartTextArea.appendText("Error" + e + '\n');
            error = true;
        }
        if(!error){

            Inventory.updatePart(Integer.parseInt(modifyPartIDAutoGen.getText())-1, identifiedPart);

            Parent inventoryScreen = FXMLLoader.load(getClass().getResource("/com/example/pa/Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryScreen);
            Stage inventoryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        }
    }

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    /** A method that overrides the superclass.  In this case, it is empty as the program requires this to be present in order to function.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

}
