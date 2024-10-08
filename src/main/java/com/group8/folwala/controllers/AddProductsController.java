package com.group8.folwala.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.group8.folwala.models.Product;
import com.group8.folwala.services.ProductService;

public class AddProductsController {

    @FXML
    private Button chooseImageButton, chooseCSVButton;

    @FXML
    private TextField nameField, priceField, unitField;

    @FXML
    private ImageView productImageView, loaderImageView;

    @FXML
    private Label errorLabel1, errorLabel2;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private static final String[] categories = { "Fleshy Fruits", "Dry Fruits", "Fruit Seeds", "Fruit Juice",
            "Vegetables" };

    private File selectedImageFile, destFile, selectedCSVFile;

    private static AddProductsSuccessController addProductsSuccessController;

    @FXML
    public void initialize() {
        categoryChoiceBox.getItems().addAll(categories);
    }

    public static void setAddProductsSuccessController(AddProductsSuccessController addProductsSuccessController) {
        AddProductsController.addProductsSuccessController = addProductsSuccessController;
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) chooseImageButton.getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);

        if (selectedImageFile != null) {
            chooseImageButton.setText(selectedImageFile.getName());

            final String PRODUCT_IMAGES_PATH = System.getenv("APPDATA") + "\\Folwala\\products\\";
            File destDir = new File(PRODUCT_IMAGES_PATH);
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
    }

    @FXML
    private void handleUploadCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = (Stage) chooseCSVButton.getScene().getWindow();
        selectedCSVFile = fileChooser.showOpenDialog(stage);

        if (selectedCSVFile != null) {
            chooseCSVButton.setText(selectedCSVFile.getName());
        }
    }

    @FXML
    private void handleBulkUploadProducts() {
        if (selectedCSVFile == null) {
            errorLabel2.setText("Please select a CSV file to upload");
            return;
        }

        List<Product> products = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(selectedCSVFile))) {
            String line;
            int id = ProductService.generateProductID();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                lineNumber++;
                String[] data = line.split(",");

                if (data.length != 5) {
                    errorLabel2.setText("Invalid CSV format at line " + lineNumber);
                    return;
                }

                try {
                    Product product = new Product(
                            id++,
                            data[0], // name
                            data[1], // unit
                            Double.parseDouble(data[2]), // price
                            data[3], // category
                            data[4] // image
                    );
                    products.add(product);
                } catch (NumberFormatException e) {
                    errorLabel2.setText("Invalid data format at line " + lineNumber);
                }
            }

            for (Product product : products)
                ProductService.saveProduct(product);

            SceneController.setScene("Inventory.fxml", "Products Added Successfully");
        } catch (IOException e) {
            errorLabel2.setText("Error reading CSV file");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        String price = priceField.getText();
        String unit = unitField.getText();
        String category = categoryChoiceBox.getValue();

        if (name.isEmpty() || price.isEmpty() || unit.isEmpty() || category == null) {
            loaderImageView.setVisible(false);
            errorLabel1.setText("Please fill in all the fields");
            return;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            errorLabel1.setText("Price must be a number");
            return;
        }

        if (selectedImageFile == null) {
            errorLabel1.setText("Please upload an image");
            return;
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
                addProductsSuccessController.setImageURL(destFile.toURI().toString());
            }
        });

        new Thread(copyTask).start();

        Product product = new Product(
                ProductService.generateProductID(),
                name,
                unit,
                Double.parseDouble(price),
                category,
                destFile.getName());
        ProductService.saveProduct(product);

        SceneController.setScene("AddProductSuccess.fxml", "Add Product Successful");
        addProductsSuccessController.setProductDetails(product);
    }

}
