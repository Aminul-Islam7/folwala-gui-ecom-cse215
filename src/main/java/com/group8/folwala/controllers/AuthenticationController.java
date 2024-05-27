package com.group8.folwala.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.group8.folwala.services.UserService;
import com.group8.folwala.models.User;

public class AuthenticationController {

  @FXML
  private TextField nameField, phoneField, addressField;

  @FXML
  private PasswordField passwordField, adminPasswordField;

  @FXML
  private Button registerButton, loginButton;

  @FXML
  private Label errorLabel;

  @FXML
  private CheckBox isAdminCheckbox;

  private UserService userService;

  private static MainLayoutController mainLayoutController;

  @FXML
  public void initialize() {
    userService = new UserService();
    if (adminPasswordField != null)
      adminPasswordField.setVisible(false);
  }

  @FXML
  private void handleRegister() {
    String name = nameField.getText();
    String phone = phoneField.getText();
    String password = passwordField.getText();
    String address = addressField.getText();
    boolean isAdmin = false;

    if (isAdminCheckbox.isSelected()) {
      String adminPassword = adminPasswordField.getText();
      if (!adminPassword.equals("admin")) {
        errorLabel.setText("Invalid admin password");
        return;
      } else
        isAdmin = true;
    }

    if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || address.isEmpty()) {
      errorLabel.setText("Please fill in all the fields");
      return;
    }

    boolean success = userService.registerUser(name, phone, password, address, isAdmin);
    if (success) {
      UserService userService = new UserService();
      userService.loginUser(phone, password);
      SceneController.showMainStage();
      mainLayoutController.initialize();
    } else
      errorLabel.setText("This phone number is already associated with an account");

  }

  @FXML
  private void handleLogin(ActionEvent event) {
    String phone = phoneField.getText();
    String password = passwordField.getText();

    User user = userService.loginUser(phone, password);
    if (user != null) {
      SceneController.showMainStage();
      mainLayoutController.initialize();
    } else
      errorLabel.setText("Invalid phone number or password");

  }

  @FXML
  private void switchToRegisterScene(ActionEvent event) {
    SceneController.switchToScene("register.fxml");
  }

  @FXML
  private void switchToLoginScene(ActionEvent event) {
    SceneController.switchToScene("login.fxml");
  }

  @FXML
  private void showAdminPasswordField(ActionEvent event) {
    if (isAdminCheckbox.isSelected()) {
      adminPasswordField.setVisible(true);
    } else {
      adminPasswordField.setVisible(false);
    }
  }

  public static void setMainLayoutController(MainLayoutController controller) {
    mainLayoutController = controller;
  }
}
