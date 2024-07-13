package game.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class GameModeController {
    private Stage stage;
    private boolean isPVP;
    private MediaPlayer mediaPlayer;

    public boolean getIsPVP()
    {
        return isPVP;
    }
    public void selectMode(ActionEvent event) throws IOException {
        Button source = (Button) event.getSource();
        if(source.getId().equals("PVP"))
        {
            isPVP = true;
        }
        else {
            isPVP = false;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayerNames.fxml"));
        Parent root = loader.load();
        PlayerNamesController playerNamesController = loader.getController();
        playerNamesController.setPVP(isPVP);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

            // to set the name field in full screen
            stage.setFullScreen(true);

        PlayerNamesController controller = loader.getController();
        controller.setMediaPlayer(mediaPlayer);

            System.out.println(stage.getHeight() + "  " + stage.getWidth());
        }
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
    }

