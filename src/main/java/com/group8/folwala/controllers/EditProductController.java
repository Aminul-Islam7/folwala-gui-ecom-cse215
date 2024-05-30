package com.group8.folwala.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import com.group8.folwala.models.Product;
import com.group8.folwala.services.ProductService;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditProductController {

  @FXML
  private ChoiceBox<String> categoryChoiceBox;

  @FXML
  private Label errorLabel;

  @FXML
  private TextField nameField, priceField, unitField;

  @FXML
  private ImageView productImageView;

  @FXML
  private Button changeImageButton;

  private File selectedImageFile, destFile;

  private Product product;

  private static InventoryController inventoryController;

  private static final String[] categories = { "Fleshy Fruits", "Dry Fruits", "Fruit Seeds", "Fruit Juice",
      "Vegetables" };

  @FXML
  public void initialize() {
    InventoryController.setProductController(this);
    categoryChoiceBox.getItems().addAll(categories);
  }

  public static void setInventoryController(InventoryController inventoryController) {
    EditProductController.inventoryController = inventoryController;
  }

  @FXML
  private void handleUploadImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    Stage stage = (Stage) changeImageButton.getScene().getWindow();
    selectedImageFile = fileChooser.showOpenDialog(stage);

    if (selectedImageFile != null) {
      changeImageButton.setText(selectedImageFile.getName());

      File destDir = new File("src/main/resources/images/products");
      if (!destDir.exists()) {
        destDir.mkdirs();
      }

      destFile = new File(destDir, selectedImageFile.getName());
      int counter = 1;
      String originalName = destFile.getName();
      while (destFile.exists()) {
        String newName = originalName.substring(0, originalName.lastIndexOf('.'))
            + "_" + counter
            + originalName.substring(originalName.lastIndexOf('.'));
        destFile = new File(destDir, newName);
        counter++;
      }

      Image image = new Image(selectedImageFile.toURI().toString());
      productImageView.setImage(image);
    }

    Task<Void> copyTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        Files.copy(selectedImageFile.toPath(), destFile.toPath());
        return null;
      }
    };
    copyTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
      @Override
      public void handle(WorkerStateEvent event) {
        productImageView.setImage(new Image(destFile.toURI().toString()));
      }
    });

    new Thread(copyTask).start();
  }

  public void setProduct(Product product) {
    this.product = product;
    categoryChoiceBox.setValue(product.getCategory());
    nameField.setText(product.getName());
    nameField.setPromptText(product.getName());
    priceField.setText(String.valueOf(product.getPrice()));
    priceField.setPromptText(String.valueOf(product.getPrice()));
    unitField.setText(product.getUnit());
    unitField.setPromptText(product.getUnit());

    URL imageURL = getClass().getResource("/images/products/" + product.getImage());

    if (imageURL == null)
      imageURL = getClass().getResource("/images/logo.png");

    productImageView.setImage(new ImageView(imageURL.toString()).getImage());
  }

  @FXML
  public void handleSaveProduct(ActionEvent event) {
    String name = nameField.getText();
    String price = priceField.getText();
    String unit = unitField.getText();
    String category = categoryChoiceBox.getValue();

    if (name.isEmpty() || price.isEmpty() || unit.isEmpty() || category == null) {
      errorLabel.setText("Please fill in all fields");
      return;
    }

    try {
      Double.parseDouble(price);
    } catch (NumberFormatException e) {
      errorLabel.setText("Price must be a number");
      return;
    }

    String image = product.getImage();
    if (selectedImageFile != null) {
      image = destFile.getName();
    }

    product = new Product(product.getProductID(), name, unit, Double.parseDouble(price), category,
        image);
    ProductService.updateProduct(product);
    inventoryController.refreshProducts();
    SceneController.closeSecondaryStage();
  }

  @FXML
  public void handleCancel(ActionEvent event) {
    SceneController.closeSecondaryStage();
  }

}
