package game.gui;

import game.engine.CLIGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController extends CLIGameController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane anchorPaneID;

    @FXML
    private ImageView backgroundIMG;

    @FXML
    private VBox buttonVBox;

    @FXML
    private Button exitBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Button settingsBtn;

    private Scene settingsScene;

    @FXML
    private Label musicLabel;

    @FXML
    private Slider musicSliderReal;

    private MediaPlayer mediaPlayer;
    private Scene mainScene;

    @FXML
    void show(ContextMenuEvent event) {
        backgroundIMG.setFitHeight(anchorPaneID.getHeight());
        backgroundIMG.setFitWidth(anchorPaneID.getWidth());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL resource = getClass().getResource("/music/music2.mp3");
        if (resource == null) {
            throw new IllegalArgumentException("Media file not found");
        }
        musicLabel.setVisible(false);
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(1.5);
        mediaPlayer.play();
        // Ensure the slider reflects the current volume
        musicSliderReal.setValue(mediaPlayer.getVolume() * 100);

        // Add a listener to the slider to update the media player volume
        musicSliderReal.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100;
            mediaPlayer.setVolume(volume);
            System.out.println("Volume changed to: " + volume);
        });

        musicSliderReal.setVisible(false); // Initially hidden
    }

    @FXML
    void terminateGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit time :(");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) anchorPaneID.getScene().getWindow();
            System.out.println("We will miss you.");
            stage.close();
        }
    }

    @FXML
    void toPlayScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameMode.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);

        GameModeController controller = loader.getController();
        controller.setMediaPlayer(mediaPlayer);
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

        // Ensure the slider reflects the current volume
        musicSliderReal.setValue(mediaPlayer.getVolume() * 100);

        // Add a listener to the slider to update the media player volume
        musicSliderReal.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100;
            mediaPlayer.setVolume(volume);
            System.out.println("Volume changed to: " + volume);
        });
    }

    @FXML
    void toSettingScene(ActionEvent event) throws IOException {
        musicSliderReal.setVisible(!musicSliderReal.isVisible());
        musicLabel.setVisible(!musicLabel.isVisible());
        playBtn.setVisible(!playBtn.isVisible());
        exitBtn.setVisible(!exitBtn.isVisible());
        if (!exitBtn.isVisible()) {
            settingsBtn.setText("Back");
        } else {
            settingsBtn.setText("Settings");
        }
    }
}
