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

  private static ProductListController productController;

  @FXML
  public void initialize() {
    SceneController.setContentPane(contentPane);
    SceneController.setMainLayoutController(this);
    AuthenticationController.setMainLayoutController(this);

    UserService userService = new UserService();

    if (userService.getCurrentUser() != null) {
      adminPane.setVisible(userService.getCurrentUser().isAdmin());
      SceneController.setScene("ProductList.fxml", ("Welcome, " + userService.getCurrentUser().getName()));
    }
  }

  public static void setProductController(ProductListController productController) {
    MainLayoutController.productController = productController;
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

  @FXML
  public void goToHome() {
    SceneController.setScene("ProductList.fxml", "Welcome, " + new UserService().getCurrentUser().getName());
  }

  @FXML
  public void loadAllProducts() {
    productController.loadAllProducts();
    setSceneLabel("All Items");
  }

  @FXML
  public void loadFleshyFruits() {
    productController.getProductsByCategory("Fleshy Fruits");
    setSceneLabel("Fleshy Fruits");
  }

  @FXML
  public void loadDryFruits() {
    productController.getProductsByCategory("Dry Fruits");
    setSceneLabel("Dry Fruits");
  }

  @FXML
  public void loadFruitSeeds() {
    productController.getProductsByCategory("Fruit Seeds");
    setSceneLabel("Fruit Seeds");
  }

  @FXML
  public void loadFruitJuice() {
    productController.getProductsByCategory("Fruit Juice");
    setSceneLabel("Fruit Juice");
  }

  @FXML
  public void loadVegetables() {
    productController.getProductsByCategory("Vegetables");
    setSceneLabel("Fresh Vegetables");
  }

  @FXML
  public void visitAddProductsScene() {
    SceneController.setScene("AddProducts.fxml", "Add Products");
  }

  @FXML
  public void visitInventoryScene() {
    SceneController.setScene("Inventory.fxml", "Inventory");
  }

}
