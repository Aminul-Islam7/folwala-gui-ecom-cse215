package com.group8.folwala.controllers;

import java.util.ArrayList;
import java.util.Collections;

import com.group8.folwala.models.Order;
import com.group8.folwala.models.OrderItem;
import com.group8.folwala.services.CartService;
import com.group8.folwala.services.OrderService;
import com.group8.folwala.services.UserService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrderHistoryController {

  @FXML
  private FlowPane orderFlowPane;

  private ArrayList<Order> orders;
  private UserService userService;

  @FXML
  public void initialize() {
    userService = new UserService();
    orders = OrderService.getOrders(userService.getCurrentUser().getPhone());
    Collections.reverse(orders);
    loadOrders();
  }

  public void loadOrders() {
    orderFlowPane.getChildren().clear();
    for (Order order : orders) {
      HBox orderPane = createOrderBox(order);
      orderFlowPane.getChildren().add(orderPane);
    }
  }

  public HBox createOrderBox(Order order) {
    HBox orderPane = new HBox();
    orderPane.getStyleClass().add("order-box");

    orderPane.getChildren().addAll(createOrderLeftBox(order), createOrderRightBox(order));

    return orderPane;
  }

  public VBox createOrderLeftBox(Order order) {
    VBox orderLeftBox = new VBox(10);
    orderLeftBox.setPrefWidth(450);

    VBox orderDetailsBox = new VBox();

    Label orderIDLabel = new Label("Order # " + order.getOrderID());
    Label orderDateLabel = new Label("Placed on " + order.getOrderDate());
    Label orderUserLabel = new Label("by " + order.getUser().getName() + " (" + order.getUser().getPhone() + ")");

    orderIDLabel.getStyleClass().add("highlight");
    orderDateLabel.getStyleClass().add("lowlight");
    orderUserLabel.getStyleClass().add("lowlight");

    orderDetailsBox.getChildren().addAll(orderIDLabel, orderDateLabel, orderUserLabel);

    VBox orderedItemsBox = new VBox();

    Label orderedItemsLabel = new Label("Ordered Items:");
    orderedItemsLabel.setStyle("-fx-font-weight: bold;");
    orderedItemsBox.getChildren().add(orderedItemsLabel);

    for (int i = 0; i < order.getOrderItems().size(); i++) {
      OrderItem orderedItem = order.getOrderItems().get(i);
      Label orderedItems = new Label(
          (i + 1) + ". " +
              orderedItem.getProduct().getName()
              + " [৳ " + (int) orderedItem.getProduct().getPrice()
              + " / " + orderedItem.getProduct().getUnit() + "]"
              + " x " + orderedItem.getQuantity() +
              " = " + (int) orderedItem.getTotalPrice() + " BDT");

      orderedItemsBox.getChildren().add(orderedItems);
    }

    orderLeftBox.getChildren().addAll(orderDetailsBox, orderedItemsBox);

    return orderLeftBox;
  }

  public VBox createOrderRightBox(Order order) {
    VBox orderRightBox = new VBox(10);
    orderRightBox.setPrefWidth(285);
    orderRightBox.alignmentProperty().set(javafx.geometry.Pos.TOP_RIGHT);

    Label orderStatusLabel = new Label(order.isDelivered() ? "Delivered" : "Processing");
    orderStatusLabel.getStyleClass().add(order.isDelivered() ? "delivered" : "processing");

    VBox orderSummaryBox = new VBox(5);

    VBox shippingAddressBox = new VBox();
    Label shippingAddressLabel = new Label("Shipping Address:");
    Label shippingAddress = new Label(order.getShippingAddress());
    shippingAddressLabel.setStyle("-fx-font-weight: bold;");
    shippingAddress.setWrapText(true);
    shippingAddressBox.getChildren().addAll(shippingAddressLabel, shippingAddress);

    HBox paymentMethodBox = new HBox();
    Label paymentMethodLabel = new Label("Payment Method: ");
    Label paymentMethod = new Label(order.getPaymentMethod());
    paymentMethodLabel.setStyle("-fx-font-weight: bold;");
    paymentMethodBox.getChildren().addAll(paymentMethodLabel, paymentMethod);

    HBox subtotalBox = new HBox();
    Label subtotalLabel = new Label("Subtotal: ");
    Label subtotal = new Label("৳ " + order.getTotalPrice());
    subtotalLabel.setStyle("-fx-font-weight: bold;");
    subtotalBox.getChildren().addAll(subtotalLabel, subtotal);

    HBox deliveryChargeBox = new HBox(2);
    Label deliveryChargeLabel = new Label("Delivery Charge: ");
    Label deliveryCharge = new Label("৳ 50");
    deliveryChargeLabel.setStyle("-fx-font-weight: bold;");
    deliveryChargeBox.getChildren().addAll(deliveryChargeLabel, deliveryCharge);

    orderSummaryBox.getChildren().addAll(shippingAddressBox, paymentMethodBox, subtotalBox, deliveryChargeBox);

    Label totalLabel = new Label("Total: ৳ " + (order.getTotalPrice() + 50));
    totalLabel.getStyleClass().add("highlight");

    orderRightBox.getChildren().addAll(
        orderStatusLabel,
        orderSummaryBox,
        totalLabel);

    return orderRightBox;
  }
}
