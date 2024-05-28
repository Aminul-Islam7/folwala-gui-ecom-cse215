package com.group8.folwala.controllers;

import com.group8.folwala.models.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddProductsSuccessController {

  @FXML
  private Label categoryLabel, nameLabel, priceLabel, unitLabel;

  @FXML
  private ImageView imageView;

  public void initialize() {
    AddProductsController.setAddProductsSuccessController(this);
  }

  public void setProductDetails(Product product) {
    categoryLabel.setText(product.getCategory());
    nameLabel.setText(product.getName());
    priceLabel.setText(String.valueOf((int) product.getPrice()));
    unitLabel.setText(product.getUnit());
  }

  public void setImageURL(String string) {
    imageView.setImage(new Image(string));
  }

}
