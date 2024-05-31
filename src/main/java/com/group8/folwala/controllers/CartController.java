package com.group8.folwala.controllers;

import java.net.URL;
import java.util.ArrayList;

import com.group8.folwala.models.Cart;
import com.group8.folwala.models.CartItem;
import com.group8.folwala.models.User;
import com.group8.folwala.services.CartService;
import com.group8.folwala.services.UserService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CartController {

  @FXML
  private FlowPane cartFlowPane;

  @FXML
  private Label nameLabel, quantityLabel, unitPriceLabel, itemsLabel, subtotalLabel, deliveryChargeLabel, totalLabel;

  @FXML
  private TextArea addressTextArea;

  @FXML
  private Button placeOrderButton;

  private ArrayList<CartItem> cartItems;

  private UserService userService;

  private static MainLayoutController mainLayoutController;

  public static void setMainLayoutController(MainLayoutController mainLayoutController) {
    CartController.mainLayoutController = mainLayoutController;
  }

  @FXML
  public void initialize() {
    userService = new UserService();
    cartItems = CartService.getCartItems(userService.getCurrentUser().getPhone());
    loadCartItems();
    updateOrderSummary();
  }

  public void loadCartItems() {
    cartFlowPane.getChildren().clear();
    for (CartItem cartItem : cartItems) {
      HBox cartItemPane = createCartItemBox(cartItem);
      cartFlowPane.getChildren().add(cartItemPane);
    }
  }

  public HBox createCartItemBox(CartItem cartItem) {
    HBox cartItemBox = new HBox(10);

    cartItemBox.setPrefWidth(520);
    cartItemBox.setAlignment(javafx.geometry.Pos.CENTER);
    cartItemBox.getStyleClass().add("cartitem-box");

    URL imageURL = getClass().getResource("/images/products/" + cartItem.getProduct().getImage());
    if (imageURL == null)
      imageURL = getClass().getResource("/images/logo.png");

    ImageView imageView = new ImageView(imageURL.toString());

    imageView.setFitWidth(60);
    imageView.setFitHeight(60);
    imageView.preserveRatioProperty().set(true);

    Label nameLabel = new Label(cartItem.getProduct().getName());
    nameLabel.getStyleClass().add("name");

    Label unitPriceLabel = new Label("৳ " + cartItem.getProduct().getPrice() + " / " + cartItem.getProduct().getUnit());
    unitPriceLabel.getStyleClass().add("unit-price");

    VBox detailsBox1 = new VBox(6);
    detailsBox1.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    detailsBox1.setPrefWidth(270);

    detailsBox1.getChildren().addAll(nameLabel, unitPriceLabel);

    VBox detailsBox2 = new VBox(5);
    detailsBox2.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
    detailsBox2.setPrefWidth(140);

    Label itemTotal = new Label("BDT " + (int) (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
    itemTotal.getStyleClass().add("item-total");

    HBox quantityBox = new HBox(5);
    quantityBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);

    Label quantityLabel = new Label(String.valueOf(cartItem.getQuantity()));
    quantityLabel.getStyleClass().add("quantity");

    Button subtractButton = new Button();
    Button addButton = new Button();

    FontAwesomeIcon minusIcon = new FontAwesomeIcon();
    minusIcon.glyphNameProperty().set("MINUS");
    subtractButton.setGraphic(minusIcon);

    FontAwesomeIcon plusIcon = new FontAwesomeIcon();
    plusIcon.glyphNameProperty().set("PLUS");
    addButton.setGraphic(plusIcon);

    subtractButton.getStyleClass().add("button");
    addButton.getStyleClass().add("button");

    subtractButton.setOnAction(e -> {
      if (cartItem.getQuantity() > 1) {
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        quantityLabel.setText(String.valueOf(cartItem.getQuantity()));
      } else {
        cartItems.remove(cartItem);
      }
      Cart cart = new Cart(userService.getCurrentUser().getPhone(), cartItems);
      CartService.saveCart(cart);
      loadCartItems();
      updateOrderSummary();
      mainLayoutController.updateCartItemCount();
    });

    addButton.setOnAction(e -> {
      cartItem.setQuantity(cartItem.getQuantity() + 1);
      Cart cart = new Cart(userService.getCurrentUser().getPhone(), cartItems);
      CartService.saveCart(cart);
      quantityLabel.setText(String.valueOf(cartItem.getQuantity()));
      loadCartItems();
      updateOrderSummary();
    });

    quantityBox.getChildren().addAll(subtractButton, quantityLabel, addButton);

    detailsBox2.getChildren().addAll(itemTotal, quantityBox);

    cartItemBox.getChildren().addAll(imageView, detailsBox1, detailsBox2);

    return cartItemBox;
  }

  public void updateOrderSummary() {
    double total = CartService.getCartTotalPrice(userService.getCurrentUser().getPhone());
    int items = CartService.getCartItemCount(userService.getCurrentUser().getPhone());

    itemsLabel.setText(String.valueOf(items));
    subtotalLabel.setText("৳ " + (int) total);
    int deliveryCharge = 50;
    if (items < 1)
      deliveryCharge = 0;
    deliveryChargeLabel.setText("৳ " + deliveryCharge);
    totalLabel.setText("৳ " + ((int) total + deliveryCharge));

    addressTextArea.setText(userService.getCurrentUser().getAddress());
  }

}
