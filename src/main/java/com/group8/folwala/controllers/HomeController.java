package com.group8.folwala.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Action;

import com.group8.folwala.models.Product;

public class HomeController {

  @FXML
  private ListView<Product> productList;

  @FXML
  private Label productName;

  @FXML
  private Label productPrice;

  @FXML
  public void initialize() {
    ObservableList<Product> products = FXCollections.observableArrayList(Product.getAllProducts());

    productList.getItems().addAll(products);

    productList.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> printProductId(newValue));
  }

  public void printProductId(Product product) {
    System.out.println(product.getName());
  }

}
