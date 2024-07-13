package game.gui;

import game.engine.GameState;
import game.engine.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EndSceneController implements Initializable {
    @FXML
    private Label red;
    @FXML
    private Label green;
    @FXML
    private Label blue;
    @FXML
    private Label magenta;
    @FXML
    private Label yellow;
    @FXML
    private Label EC;
    @FXML
    private Label total;
    @FXML
    private Label red1;
    @FXML
    private Label green1;
    @FXML
    private Label blue1;
    @FXML
    private Label magenta1;
    @FXML
    private Label yellow1;
    @FXML
    private Label EC1;
    @FXML
    private Label total1;
    @FXML
    private Label player1Title;
    @FXML
    private Label player2Title;
    @FXML
    private Label Title;
    @FXML
    private AnchorPane anchorPaneEnd;
    @FXML
    private Player player1;
    private Player player2;
    GameState gameState;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            setter();
        });
    }


    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private Player getPlayer1() {
        return player1;
    }

    private Player getPlayer2() {
        return player2;
    }
    private void setter()
    {
        red.setText(getPlayer1().getGameScore().getRedRealmScore() + "");
        green.setText(getPlayer1().getGameScore().getGreenRealmScore() + "");
        blue.setText(getPlayer1().getGameScore().getBlueRealmScore() + "");
        magenta.setText(getPlayer1().getGameScore().getMagentaRealmScore() + "");
        yellow.setText(getPlayer1().getGameScore().getYellowRealmScore() + "");
        EC.setText(getPlayer1().getGameScore().getElementalCount() + "");
        total.setText(getPlayer1().getGameScore().getTotalScore() + "");
        red1.setText(getPlayer2().getGameScore().getRedRealmScore() + "");
        green1.setText(getPlayer2().getGameScore().getGreenRealmScore() + "");
        blue1.setText(getPlayer2().getGameScore().getBlueRealmScore() + "");
        magenta1.setText(getPlayer2().getGameScore().getMagentaRealmScore() + "");
        yellow1.setText(getPlayer2().getGameScore().getYellowRealmScore() + "");
        EC1.setText(getPlayer2().getGameScore().getElementalCount() + "");
        total1.setText(getPlayer2().getGameScore().getTotalScore() + "");
        player1Title.setText(getPlayer1().getName() + "'s Realm Scores");
        player2Title.setText(getPlayer2().getName() + "'s Realm Scores");
        if (gameState == GameState.PLAYER_1_WINS) {
            Title.setText(getPlayer1().getName().toUpperCase() + " IS THE PROTECTOR OF ELDORIA ");
        } else if (gameState == GameState.PLAYER_2_WINS){
            Title.setText(getPlayer2().getName().toUpperCase() + " IS THE PROTECTOR OF ELDORIA ");
        }
        else
        {
            Title.setText("The two wizards are at a stalemate...");
        }
    }
}
