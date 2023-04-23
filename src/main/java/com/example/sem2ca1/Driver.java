package com.example.sem2ca1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;


import java.awt.*;
import java.io.IOException;

public class Driver extends Application {

public static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(Driver.class.getResourceAsStream("/star-spinning.gif")));

        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("mainView.fxml" +
         ""));
        Scene scene = new Scene(fxmlLoader.load(), 1707, 858);
        stage.setScene(scene);
        stage.show();
        primaryStage = stage;
    }

    public void reset() throws IOException{
    start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}