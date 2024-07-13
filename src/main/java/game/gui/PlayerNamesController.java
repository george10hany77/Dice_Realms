package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerNamesController implements Initializable {
    private String player1Name;
    private String player2Name = "Badawayyy";
    private boolean isPVP;
    private int playerNumChecker = 1;
    private Stage stage;
    private Parent root;

    public void setPVP(boolean PVP) {
        isPVP = PVP;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    @FXML
    private TextField nameField;
    @FXML
    private Label announcementLabel;
    @FXML
    private AnchorPane nameAnchorPane;

    private MediaPlayer mediaPlayer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    enterName(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void enterName(ActionEvent event) throws IOException {
        // Handle if textfield is empty
        if(playerNumChecker == 1) {
            player1Name = nameField.getText();
            player1Name = player1Name.trim();
            if (player1Name.isEmpty()){
                announcementLabel.setText("Please Enter a name!");
                return;
            }
        } else {
            player2Name = nameField.getText();
            player2Name = player2Name.trim();
            // Handle if textfield is empty
            if (player2Name.isEmpty()){
                announcementLabel.setText("Please Enter a name!");
                return;
            } else if (player2Name.equals(player1Name)) { // Handle if the name is the same as player1
                announcementLabel.setText("Please Enter a different name!");
                return;
            }
        }
        if (isPVP && playerNumChecker == 1) {
            playerNumChecker++;
            nameField.clear();
            announcementLabel.setText("Enter your name Player 2");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayScene.fxml"));
        root = loader.load();
        PlaySceneController playSceneController = loader.getController();
        // Setting the names for each player
        playSceneController.setNamePlayer1(player1Name);
        playSceneController.setNamePlayer2(player2Name);
        playSceneController.setPVP(isPVP);
        // Get the stage from any UI element reference (nameField in this case)
        Stage stage = (Stage) nameField.getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set the stage to full screen
        stage.setFullScreen(true);
        PlaySceneController controller = loader.getController();
        controller.setMediaPlayer(mediaPlayer);

    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

}
