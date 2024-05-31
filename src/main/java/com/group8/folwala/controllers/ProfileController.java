package com.group8.folwala.controllers;

import com.group8.folwala.models.User;
import com.group8.folwala.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProfileController {

  @FXML
  private TextField nameField, phoneField, passwordField;

  @FXML
  private TextArea addressField;

  @FXML
  Label errorLabel;

  @FXML
  private Button updateProfileButton;

  @FXML
  public void initialize() {
    UserService userService = new UserService();
    User currentUser = userService.getCurrentUser();

    nameField.setText(currentUser.getName());
    nameField.setPromptText(currentUser.getName());
    phoneField.setText(currentUser.getPhone());
    phoneField.setPromptText(currentUser.getPhone());
    addressField.setText(currentUser.getAddress());
    addressField.setPromptText(currentUser.getAddress());
  }

  @FXML
  private void handleUpdateProfile(ActionEvent event) {
    if (nameField.getText().isEmpty() || addressField.getText().isEmpty()
        || passwordField.getText().isEmpty()) {
      errorLabel.setText("Please fill in all the fields");
      return;
    }

    UserService userService = new UserService();
    User currentUser = userService.getCurrentUser();

    userService.updateUser(currentUser, nameField.getText(), addressField.getText(),
        passwordField.getText());

    SceneController.setScene("Profile.fxml", "Profile");
  }

}
