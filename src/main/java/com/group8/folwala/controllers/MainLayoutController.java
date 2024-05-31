package com.group8.folwala.controllers;

import com.group8.folwala.CustomUtils;
import com.group8.folwala.services.CartService;
import com.group8.folwala.services.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainLayoutController {

  @FXML
  private AnchorPane contentPane, adminPane;

  @FXML
  private Button backButton, forwardButton, cartButton;

  @FXML
  private Label sceneLabel, cartItemCountLabel;

  @FXML
  private TextField searchField;

  private int cartSize;

  private static ProductListController productController;

  @FXML
  public void initialize() {

    SceneController.setContentPane(contentPane);
    SceneController.setMainLayoutController(this);
    AuthenticationController.setMainLayoutController(this);
    ProductListController.setMainLayoutController(this);
    CartController.setMainLayoutController(this);
    PaymentController.setMainLayoutController(this);

    updateCartItemCount();

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
  public void handleSearch() {
    productController.searchProducts(searchField.getText());
  }

  public void updateCartItemCount() {
    UserService userService = new UserService();
    cartSize = CartService.getCartItems(userService.getCurrentUser().getPhone()).size();
    if (cartSize > 0) {
      cartItemCountLabel.setText(String.valueOf(cartSize));
      cartItemCountLabel.setVisible(true);
    } else {
      cartItemCountLabel.setText("0");
      cartItemCountLabel.setVisible(false);
    }
  }

  @FXML
  public void loadAllProducts() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "All Items");
    productController.loadAllProducts();
    setSceneLabel("All Items");
  }

  @FXML
  public void loadFleshyFruits() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Fleshy Fruits");
    productController.getProductsByCategory("Fleshy Fruits");
    setSceneLabel("Fleshy Fruits");
  }

  @FXML
  public void loadDryFruits() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Dry Fruits");
    productController.getProductsByCategory("Dry Fruits");
    setSceneLabel("Dry Fruits");
  }

  @FXML
  public void loadFruitSeeds() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Fruit Seeds");
    productController.getProductsByCategory("Fruit Seeds");
    setSceneLabel("Fruit Seeds");
  }

  @FXML
  public void loadFruitJuice() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Fruit Juice");
    productController.getProductsByCategory("Fruit Juice");
    setSceneLabel("Fruit Juice");
  }

  @FXML
  public void loadVegetables() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Fresh Vegetables");
    productController.getProductsByCategory("Vegetables");
    setSceneLabel("Fresh Vegetables");
  }

  @FXML
  public void visitHomeScene() {
    if (!SceneController.getCurrentView().equals("ProductList.fxml"))
      SceneController.setScene("ProductList.fxml", "Welcome, " + new UserService().getCurrentUser().getName());
  }

  @FXML
  public void visitCartScene() {
    SceneController.setScene("Cart.fxml", "Cart");
  }

  @FXML
  public void visitAddProductsScene() {
    SceneController.setScene("AddProducts.fxml", "Add Products");
  }

  @FXML
  public void visitInventoryScene() {
    SceneController.setScene("Inventory.fxml", "Inventory");
  }

  @FXML
  public void visitOrderHistoryScene() {
    SceneController.setScene("OrderHistory.fxml", "Order History");
  }

}
