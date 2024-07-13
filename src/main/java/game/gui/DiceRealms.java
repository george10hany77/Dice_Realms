package game.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class DiceRealms extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("عجايب يا دنيا: السيد البدوي");

        // Load Main Scene
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot);
        MainSceneController mainController = mainLoader.getController();

        // Load Settings Scene
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/SettingsScene.fxml"));
        Parent settingsRoot = settingsLoader.load();
        Scene settingsScene = new Scene(settingsRoot);
        SettingsSceneController settingsController = settingsLoader.getController();


        // Set up the main scene
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/myStyle.css")).toExternalForm());

        // Set application icon
        URL iconUrl = getClass().getResource("/images/icon.png");
        if (iconUrl != null) {
            primaryStage.getIcons().add(new Image(iconUrl.toExternalForm()));
        } else {
            System.out.println("Icon resource not found");
        }

        // Handle close request
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            terminateGame(primaryStage);
        });

        // Set stage properties
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1000);
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void terminateGame(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit time :(");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("We will miss you.");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
