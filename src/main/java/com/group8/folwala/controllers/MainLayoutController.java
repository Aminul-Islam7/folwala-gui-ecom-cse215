package com.group8.folwala.controllers;

import com.group8.folwala.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainLayoutController {

  @FXML
  private AnchorPane contentPane, adminPane;

  @FXML
  private Button backButton, forwardButton;

  @FXML
  private Label sceneLabel;

  @FXML
  public void initialize() {
    SceneController.setContentPane(contentPane);
    SceneController.setMainLayoutController(this);
    AuthenticationController.setMainLayoutController(this);

    UserService userService = new UserService();

    if (userService.getCurrentUser() != null) {
      adminPane.setVisible(userService.getCurrentUser().isAdmin());
      SceneController.setScene("Home.fxml", ("Welcome, " + userService.getCurrentUser().getName()));
    }
  }

  public void setSceneLabel(String label) {
    sceneLabel.setText(label);
  }

  public void goBack(ActionEvent e) {
    SceneController.back();
  }

  public void goForward(ActionEvent e) {
    SceneController.forward();
  }

  public void updateNavButtonsVisibility() {
    backButton.setVisible(SceneController.viewList.size() > 1);
    forwardButton.setVisible(SceneController.visitedViewList.size() > 0);
  }

  public void logoutUser() {
    UserService userService = new UserService();
    userService.logoutUser();
    SceneController.showAuthenticationStage();
  }

}
