package com.group8.folwala.controllers;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class AlertController {

  private Image icon = new Image(SceneController.class.getResource("/images/logo.png").toExternalForm());

  public static void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void showError(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void showWarning(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void showConfirmation(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public static void showConfirmation(String title, String message, Runnable callback) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    alert.getButtonTypes().clear();
    alert.getButtonTypes().addAll(javafx.scene.control.ButtonType.YES, javafx.scene.control.ButtonType.NO);

    alert.showAndWait().ifPresent(response -> {
      if (response == javafx.scene.control.ButtonType.YES) {
        callback.run();
      }
    });

  }

}
