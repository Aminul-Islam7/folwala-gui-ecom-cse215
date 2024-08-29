package com.group8.folwala;

import java.io.File;
import java.io.IOException;

import com.group8.folwala.controllers.SceneController;
import com.group8.folwala.models.User;
import com.group8.folwala.services.UserService;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        createDataFolders();
        SceneController.setStages();
        UserService userService = new UserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser != null)
            SceneController.showMainStage();
        else
            SceneController.showAuthenticationStage();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void createDataFolders() {
        String dataFolderPath = System.getenv("APPDATA") + "\\Folwala";
        File dataFolder = new File(dataFolderPath);
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        String productsFolderPath = dataFolderPath + "\\products";
        File productsFolder = new File(productsFolderPath);
        if (!productsFolder.exists()) {
            productsFolder.mkdirs();
        }

        String cartsFolderPath = dataFolderPath + "\\carts";
        File cartsFolder = new File(cartsFolderPath);
        if (!cartsFolder.exists()) {
            cartsFolder.mkdirs();
        }
    }
}
