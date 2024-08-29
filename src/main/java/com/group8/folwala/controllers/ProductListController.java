package com.group8.folwala.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

import com.group8.folwala.models.Product;
import com.group8.folwala.models.User;
import com.group8.folwala.services.CartService;
import com.group8.folwala.services.ProductService;
import com.group8.folwala.services.UserService;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public class ProductListController {

    @FXML
    private FlowPane productFlowPane;

    private ArrayList<Product> products;

    private User currentUser;

    private static MainLayoutController mainLayoutController;

    private final String PRODUCT_IMAGES_PATH = System.getenv("APPDATA") + "\\Folwala\\products\\";

    @FXML
    public void initialize() {
        MainLayoutController.setProductController(this);

        UserService userService = new UserService();
        currentUser = userService.getCurrentUser();

        products = ProductService.getAllProducts();
        loadAllProducts();
    }

    public static void setMainLayoutController(MainLayoutController controller) {
        mainLayoutController = controller;
    }

    public void loadAllProducts() {
        productFlowPane.getChildren().clear();
        // Collections.reverse(products);
        for (Product product : products) {
            VBox productBox = createProductBox(product);
            productFlowPane.getChildren().add(productBox);
        }
    }

    public void getProductsByCategory(String category) {
        productFlowPane.getChildren().clear();
        for (Product product : products)
            if (product.getCategory().equals(category)) {
                VBox productBox = createProductBox(product);
                productFlowPane.getChildren().add(productBox);
            }
    }

    private VBox createProductBox(Product product) {
        VBox productBox = new VBox();
        productBox.setAlignment(javafx.geometry.Pos.CENTER);
        productBox.setPrefWidth(235);
        productBox.setPrefHeight(330);
        productBox.setSpacing(1);
        productBox.getStyleClass().add("product-box");

        File imageFile = new File(PRODUCT_IMAGES_PATH + product.getImage());
        String imageURL = imageFile.toURI().toString();

        if (!imageFile.exists())
            imageURL = getClass().getResource("/images/logo.png").toString();

        ImageView imageView = new ImageView(imageURL);

        imageView.setFitWidth(170);
        imageView.setFitHeight(170);
        imageView.preserveRatioProperty().set(true);

        Label nameLabel = new Label(product.getName());
        Label unitLabel = new Label(product.getUnit());
        Label priceLabel = new Label("৳ " + (int) product.getPrice());
        TextField quantityField = new TextField("0");

        quantityField.minHeightProperty().set(40);
        quantityField.alignmentProperty().set(javafx.geometry.Pos.CENTER);

        nameLabel.getStyleClass().add("name");
        unitLabel.getStyleClass().add("unit");
        priceLabel.getStyleClass().add("price");

        HBox quantityBox = new HBox(5);
        quantityBox.minHeightProperty().set(45);

        Button addButton = new Button();
        Button subtractButton = new Button();
        Button addToCartButton = new Button("Add to Cart");

        FontAwesomeIcon minusIcon = new FontAwesomeIcon();
        minusIcon.glyphNameProperty().set("MINUS");
        subtractButton.setGraphic(minusIcon);

        FontAwesomeIcon plusIcon = new FontAwesomeIcon();
        plusIcon.glyphNameProperty().set("PLUS");
        addButton.setGraphic(plusIcon);

        addButton.setMinWidth(50);
        addButton.setMinHeight(40);
        subtractButton.setMinWidth(50);
        subtractButton.setMinHeight(40);

        addToCartButton.prefWidthProperty().set(250);
        addToCartButton.getStyleClass().add("action-btn");

        quantityBox.getChildren().addAll(subtractButton, quantityField, addButton);

        addButton.setOnAction(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            quantityField.setText(String.valueOf(++quantity));
        });

        subtractButton.setOnAction(e -> {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                quantityField.setText(String.valueOf(--quantity));
            }
        });

        addToCartButton.setOnAction(e -> {

            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                CartService.addItemToCart(currentUser.getPhone(), product, quantity);
                mainLayoutController.updateCartItemCount();
                quantityField.setText("0");
            }
        });

        productBox.getChildren().addAll(
                imageView,
                nameLabel,
                unitLabel,
                priceLabel,
                quantityBox,
                addToCartButton);

        return productBox;
    }

    public void searchProducts(String query) {
        if (query.isEmpty()) {
            loadAllProducts();
            return;
        }

        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(query) || product.getCategory().toLowerCase().contains(query)
                    || product.getUnit().toLowerCase().contains(query)
                    || String.valueOf(product.getPrice()).contains(query)
                    || String.valueOf(product.getProductID()).contains(query)) {
                searchResults.add(product);
            }
        }

        productFlowPane.getChildren().clear();
        for (Product product : searchResults) {
            VBox productBox = createProductBox(product);
            productFlowPane.getChildren().add(productBox);
        }
    }

}
