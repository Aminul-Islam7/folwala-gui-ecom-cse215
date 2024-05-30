package com.group8.folwala;

import java.io.IOException;

import com.group8.folwala.controllers.SceneController;
import com.group8.folwala.models.User;
import com.group8.folwala.services.ProductService;
import com.group8.folwala.services.UserService;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
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
}
