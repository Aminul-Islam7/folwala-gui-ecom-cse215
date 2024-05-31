package com.group8.folwala.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaymentController {

  @FXML
  private TextField monthField, nameField, numberField, postalCodeField, securityCodeField, yearField;

  @FXML
  private Label errorLabel;

  private static MainLayoutController mainLayoutController;

  public static void setMainLayoutController(MainLayoutController mainLayoutController) {
    PaymentController.mainLayoutController = mainLayoutController;
  }

  @FXML
  private void visitOrderHistoryScene() {
    if (monthField.getText().isEmpty() || yearField.getText().isEmpty() || nameField.getText().isEmpty()
        || numberField.getText().isEmpty() || postalCodeField.getText().isEmpty()
        || securityCodeField.getText().isEmpty()) {
      errorLabel.setText("Please fill in all the fields");
      return;
    }
    mainLayoutController.updateCartItemCount();
    SceneController.setScene("OrderHistory.fxml", "Order History");
  }

}
