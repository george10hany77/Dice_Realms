package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsSceneController{
    private Stage stage;
    private MediaPlayer mediaPlayer;

    @FXML
    private Button ReturnBtn;

    @FXML
    private Slider MusicSlider;

    private Scene mainScene;

    @FXML
    void toMainScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
        Parent root = loader.load();

        // Pass the MediaPlayer back to the main scene controller
        MainSceneController mainController = loader.getController();
        mainController.setMediaPlayer(mediaPlayer);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
    }
}
