package game.gui;

import game.engine.AI_Move;
import game.collectibles.Powerup;
import game.collectibles.RewardType;
import game.creatures.*;
import game.dice.*;
import game.engine.*;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class PlaySceneController extends CLIGameController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private GridPane Player1Hydra;
    @FXML
    private GridPane Player2Hydra;
    @FXML
    private GridPane Player1Phoenix;
    @FXML
    private GridPane Player2Phoenix;

    @FXML
    private GridPane player1Dragon;

    @FXML
    private GridPane player2Dragon;

    @FXML
    private Label Player1Name;

    @FXML
    private Label Player2Name;
    @FXML
    private ImageView RedDice;
    @FXML
    private ImageView BlueDice;
    @FXML
    private ImageView GreenDice;
    @FXML
    private ImageView MagentaDice;
    @FXML
    private ImageView YellowDice;
    @FXML
    private ImageView WhiteDice;
    @FXML
    private HBox ActiveDices;
    @FXML
    private HBox ForgottenDices;
    @FXML
    private Label roundNumber;
    @FXML
    private Label player1State;
    @FXML
    private HBox UsedDicesPlayer1;
    @FXML
    private Label player2State;
    @FXML
    private HBox UsedDicesPlayer2;
    @FXML
    private GridPane gridGaia1;
    @FXML
    GridPane gridGaia2;
    @FXML
    private Label currentPlayerLabel;
    @FXML
    private GridPane Player1Lion;
    @FXML
    private GridPane Player2Lion;
    @FXML
    private Button rollDice;
    @FXML
    private Button timeWarp;
    @FXML
    private GridPane gridGaia1p;
    @FXML
    private GridPane gridGaia2p;
    @FXML
    private Label roundNumberLabel;
    @FXML
    private Label player1Counter;
    @FXML
    private Label player2Counter;
    boolean isRewardHandling = false;
    boolean isGameEnded = false;

    private GridPane[][] allGrids;
    private ImageView[] diceImages;
    private Player currentPlayer = getActivePlayer();
    private boolean isDiceSelected;
    private Dice currentDiceSelection;
    //1,2,3,4 player1 active,player2passive,player2active,player1passive
    private int switchPlayerCounter;
    private int maxTurnNumberActive;
    private int maxTurnNumberPassive;
    private boolean isDiceRolled;
    private int maxRoundNumber;

    private boolean isPVP = true;
    private boolean isPaused = false;

    private RewardType[] roundRewards;
    private ArrayList<RewardType> playerCurrentRewards = new ArrayList<>();
    private boolean[] playerGainedRoundReward = {false, false};
    private boolean isArcaneSelection;
    private boolean player1ArcaneAccept = true;
    private boolean player2ArcaneAccept = true;

    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allGrids = new GridPane[][]{
                {player1Dragon, gridGaia1, Player1Hydra, Player1Phoenix, Player1Lion}, {player2Dragon, gridGaia2, Player2Hydra, Player2Phoenix, Player2Lion}
        };

        diceImages = new ImageView[]{RedDice, GreenDice, BlueDice, MagentaDice, YellowDice, WhiteDice};

        Node redDiceNode = diceImages[0];
        redDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverRed);
        redDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitRed);

        Node greenDiceNode = diceImages[1];
        greenDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverGreen);
        greenDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitGreen);

        Node blueDiceNode = diceImages[2];
        blueDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverBlue);
        blueDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitBlue);

        Node magentaDiceNode = diceImages[3];
        magentaDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverMagenta);
        magentaDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitMagenta);

        Node yellowDiceNode = diceImages[4];
        yellowDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverYellow);
        yellowDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitYellow);

        Node whiteDiceNode = diceImages[5];
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverYellow);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverWhiteMagenta);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverWhiteRed);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverWhiteBlue);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_ENTERED, this::onHoverGreen);

        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitYellow);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitWhiteMagenta);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitWhiteRed);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitWhiteBlue);
        whiteDiceNode.addEventHandler(MouseEvent.MOUSE_EXITED, this::onHoverExitGreen);

        DragonGUI.updateDragon(getGameBoard().getPlayer1(), this, player1Dragon, player2Dragon);
        DragonGUI.updateDragon(getGameBoard().getPlayer2(), this, player1Dragon, player2Dragon);
        LionGUI.updateLion(getGameBoard().getPlayer1(), this, Player1Lion, Player2Lion);
        LionGUI.updateLion(getGameBoard().getPlayer2(), this, Player1Lion, Player2Lion);
        updateHydra(this.getGameBoard().getPlayer1());
        updateHydra(this.getGameBoard().getPlayer2());
        updateGaia(getGameBoard().getPlayer1());
        updateGaia(getGameBoard().getPlayer2());
        updatePhoenix(getGameBoard().getPlayer1());
        updatePhoenix(getGameBoard().getPlayer2());
        disableGrids();
        disableAllDices();
        timeWarp.setDisable(true);
        maxTurnNumberActive = getGameStatus().getMaxTurnNumberActive();
        maxTurnNumberPassive = getGameStatus().getMaxTurnNumberPassive();
        maxRoundNumber = getGameStatus().getMaxRoundNumber();
        UsedDicesPlayer1.setDisable(true);
        UsedDicesPlayer2.setDisable(true);
        roundNumberLabel.setText("Round " + this.getGameStatus().getRoundNumber());
        updateDicePositions();
        roundRewards = this.getGameStatus().getRewards();
        this.getGameStatus().setTurnNumber(0);

        Platform.runLater(() -> {
            if (!isPVP) {
                setAIPlayer();
            }
            refreshPowerupsCounters();
            nextTurn();

        });
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setPVP(boolean PVP) {
        isPVP = PVP;
    }
    public void refreshPowerupsCounters()
    {
        player1Counter.setText("TimeWarps : "+this.getGameBoard().getPlayer1().getTimeWarpsCount()+"  ArcaneBoosts : "+this.getGameBoard().getPlayer1().getArcaneBoostCount());
        player2Counter.setText("TimeWarps : "+this.getGameBoard().getPlayer2().getTimeWarpsCount()+"  ArcaneBoosts : "+this.getGameBoard().getPlayer2().getArcaneBoostCount());
    }

    public void resetDiceSelection() {
        currentDiceSelection = null;
        isDiceSelected = false;
    }

    public void disableGrids() {
        for (GridPane[] grids : allGrids) {
            for (GridPane grid : grids) {
                grid.setDisable(true);
            }
        }
        gridGaia2p.setDisable(true);
        gridGaia1p.setDisable(true);
    }


    public void updateDicePositions() {
        if (isArcaneSelection) {
            return;
        }
        ActiveDices.getChildren().clear();
        ForgottenDices.getChildren().clear();
        Dice[] boardDices = this.getGameBoard().getDice();
        Label label = new Label("Forgotten dices");
        // Set background color
        label.setBackground(new Background(new BackgroundFill(Color.web("#8a2be2"), new CornerRadii(10), null)));
        // Set border color and style
        label.setBorder(new Border(new BorderStroke(
                Color.web("#9370DB"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(3)
        )));
        // Set padding
        label.setPadding(new Insets(5, 10, 5, 10));
        // Set font
        label.setFont(Font.font("Papyrus", FontWeight.BOLD, FontPosture.ITALIC, 18));
        label.setTextFill(Color.WHITE);
        ForgottenDices.setMargin(label, new Insets(-90, 0, 0, 0)); // Adjust the top inset to move the label up

        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        label.setEffect(dropShadow);
        ForgottenDices.getChildren().add(label);
        for (int i = 0; i < boardDices.length; i++) {
            if (boardDices[i].getDiceLocation() == DiceLocation.ACTIVE) {
                ActiveDices.getChildren().add(diceImages[i]);
            } else if (boardDices[i].getDiceLocation() == DiceLocation.FORGOTTEN) {
                ForgottenDices.getChildren().add(diceImages[i]);
            }
        }
    }

    public void updateGaia(Player player) throws NullPointerException {
        GaiaGUI.updateGaia(this, allGrids, player, gridGaia2p, gridGaia1p);
    }

    public void helperGaiamove(Player player, ActionEvent event) {
        GaiaGUI.helperGaiamove(player, event, currentDiceSelection, this, allGrids);
    }

    public void player1GaiaMove(ActionEvent event) {
        helperGaiamove(getGameBoard().getPlayer1(), event);

    }

    public void player2GaiaMove(ActionEvent event) {
        helperGaiamove(getGameBoard().getPlayer2(), event);

    }

    public void player1LionMove(ActionEvent event) {
        LionGUI.player1LionMove(event, this, Player1Lion, Player2Lion, currentDiceSelection);
    }

    public void player2LionMove(ActionEvent event) {
        LionGUI.player2LionMove(event, this, Player1Lion, Player2Lion, currentDiceSelection);
    }

    public void player1DragonMove(ActionEvent event) {
//        DragonGUI.player1DragonMove(event, this, player1Dragon, player2Dragon, currentDiceSelection);
        DragonGUI.dragonMove(event, this, player1Dragon, player2Dragon, currentDiceSelection, currentPlayer);
    }

    public void player2DragonMove(ActionEvent event) {
//        DragonGUI.player2DragonMove(event, this, player1Dragon, player2Dragon, currentDiceSelection);
        DragonGUI.dragonMove(event, this, player1Dragon, player2Dragon, currentDiceSelection, currentPlayer);
    }

    public void attackHydra(ActionEvent event) {
        HydraGUI.attackHydra(event, currentDiceSelection, this, currentPlayer, allGrids);
    }

    public void phoenixMove(ActionEvent event) {
        PhoenixGUI.phoenixMove(event, currentDiceSelection, this, currentPlayer, allGrids);
    }

    private void updatePhoenix(Player currentPlayer) {
        PhoenixGUI.updatePhoenix(currentDiceSelection, this, allGrids, currentPlayer);
    }

    private void rewardHandler() {
        if (playerCurrentRewards.isEmpty()) {
            nextTurn();
            return;
        }
        RewardType currentReward = playerCurrentRewards.get(0);
        int attackValue = 0;
        switch (currentReward) {
            case AB:
                currentPlayer.addArcaneBoost();
                refreshPowerupsCounters();
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained an Arcane Boost");
                playerCurrentRewards.remove(0);
                nextTurn();
                return;
            case TW:
                currentPlayer.addTimeWarp();
                refreshPowerupsCounters();
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Time Warp");
                playerCurrentRewards.remove(0);
                nextTurn();

                return;
            case RB:
                if (!isRewardHandling)
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Red Bonus");
                isRewardHandling = true;
                if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.RED).isAttackable()) {
                    playerCurrentRewards.remove(0);
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The red realm was fully conquered");
                    nextTurn();
                    return;
                }
                if ((!isPVP) && currentPlayer instanceof AI_Player){

                    Move[] moves = this.getPossibleMovesForRealm(currentPlayer, RealmColor.RED);
                    AI_Move[] AIMoves = new AI_Move[moves.length];
                    for (int i = 0; i < moves.length; i++) {
                        AIMoves[i] = new AI_Move(moves[i], this);
                    }
                    Arrays.sort(AIMoves);
                    makeMove(currentPlayer, AIMoves[0].getMove());
                    playerCurrentRewards.remove(0);
                    DragonGUI.updateDragon(currentPlayer, this, player1Dragon, player2Dragon);
                    nextTurn();
                    break;
                }
                attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Red Bonus Value:");
                RedDice redDice = new RedDice(attackValue);
                if (attackValue > 6 || attackValue <= 0 || getPossibleMovesForADie(currentPlayer, redDice).length == 0) {
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Invalid choice please choose a value that corresponds to the Dragons' scoresheet");
                    rewardHandler();
                } else {
                    playerCurrentRewards.remove(0);
                    currentDiceSelection = redDice;
                    showGrid(redDice, currentPlayer);
                }
                return;
            case BB:
                if (!isRewardHandling)
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Blue Bonus");
                isRewardHandling = true;
                if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE).isAttackable()) {
                    playerCurrentRewards.remove(0);
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The blue realm was fully conquered");
                    nextTurn();
                    return;
                }
                if ((!isPVP) && currentPlayer instanceof AI_Player){
                    makeMove(currentPlayer, new Move(new BlueDice(6),currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)));
                    playerCurrentRewards.remove(0);
                    updateHydra(currentPlayer);
                    nextTurn();
                    break;
                }

                    else{
                    attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Blue Bonus Value");
                BlueDice blueDice = new BlueDice(attackValue);
                if (attackValue > 6 || attackValue <= 0 || getPossibleMovesForADie(currentPlayer, blueDice).length == 0) {
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Invalid choice please choose a value that corresponds to the Hydras' scoresheet");
                    rewardHandler();
                } else {
                    playerCurrentRewards.remove(0);
                    currentDiceSelection = blueDice;
                    showGrid(blueDice, currentPlayer);

                }
                }
                break;
            case GB:
                if (!isRewardHandling)
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Green Bonus");
                isRewardHandling = true;
                if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.GREEN).isAttackable()) {
                    playerCurrentRewards.remove(0);
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The green realm was fully conquered");
                    nextTurn();
                    return;
                }
                if ((!isPVP) && currentPlayer instanceof AI_Player){

                    Move[] moves = this.getPossibleMovesForRealm(currentPlayer, RealmColor.GREEN);
                    AI_Move[] AIMoves = new AI_Move[moves.length];
                    for (int i = 0; i < moves.length; i++) {
                        AIMoves[i] = new AI_Move(moves[i], this);
                    }
                    Arrays.sort(AIMoves);
                    makeMove(currentPlayer, AIMoves[0].getMove());
                    playerCurrentRewards.remove(0);
                    updateGaia(currentPlayer);
                    nextTurn();
                    break;
                }

                else {
                    attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Green Bonus Value:");
                    GreenDice greenDice = new GreenDice(attackValue - this.getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue());
                    if (attackValue <= 0 || attackValue > 12 || getPossibleMovesForADie(currentPlayer, greenDice).length == 0) {
                        showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "choose a value between 2 and 12 according to the gaia you want to attack");
                        rewardHandler();
                    } else {
                        playerCurrentRewards.remove(0);
                        currentDiceSelection = greenDice;
                        showGrid(greenDice, currentPlayer);
                    }
                }
                break;
            case YB:
                if (!isRewardHandling)
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Yellow Bonus");
                isRewardHandling = true;
                if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.YELLOW).isAttackable()) {
                    playerCurrentRewards.remove(0);
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The yellow realm was fully conquered");
                    nextTurn();
                    return;
                }
                if ((!isPVP) && currentPlayer instanceof AI_Player){
                    makeMove(currentPlayer, new Move(new YellowDice(6),currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.YELLOW)));
                    playerCurrentRewards.remove(0);
                    LionGUI.updateLion(currentPlayer, this, Player1Lion, Player2Lion);
                    nextTurn();
                    break;
                }

                else {
                    attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Yellow Bonus Value:");
                    YellowDice yellowDice = new YellowDice(attackValue);
                    if (attackValue > 6 || attackValue <= 0 || getPossibleMovesForADie(currentPlayer, yellowDice).length == 0) {
                        showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Choose a valid value for the Lions' realm");
                        rewardHandler();
                    } else {
                        playerCurrentRewards.remove(0);
                        currentDiceSelection = yellowDice;
                        showGrid(yellowDice, currentPlayer);

                    }
                }
                break;
            case MB:
                if (!isRewardHandling)
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained a Magenta Bonus");
                isRewardHandling = true;
                if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA).isAttackable()) {
                    playerCurrentRewards.remove(0);
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The magenta realm was fully conquered");
                    nextTurn();
                    return;
                }
                if ((!isPVP) && currentPlayer instanceof AI_Player){
                    makeMove(currentPlayer, new Move(new MagentaDice(6),currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)));
                    playerCurrentRewards.remove(0);
                    updatePhoenix(currentPlayer);
                    nextTurn();
                    break;
                }

                else {
                    attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Magenta Bonus Value:");
                    MagentaDice magentaDice = new MagentaDice(attackValue);
                    if (attackValue > 6 || attackValue <= 0 || getPossibleMovesForADie(currentPlayer, magentaDice).length == 0) {
                        showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Choose a valid value for the Phoenix realm");
                        rewardHandler();
                    } else {
                        playerCurrentRewards.remove(0);
                        currentDiceSelection = magentaDice;
                        showGrid(magentaDice, currentPlayer);

                    }
                }
                break;
            case EC:
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained an Elemental Crest");
                currentPlayer.getGameScore().addElementalCrest();
                playerCurrentRewards.remove(0);
                nextTurn();
                return;
            case EB:
                if (!isRewardHandling)
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), currentPlayer.getName() + " You just gained an Essence Bonus");
                isRewardHandling = true;
                if ((!isPVP) && currentPlayer instanceof AI_Player){

                    Move[] moves = getAllPossibleMoves(currentPlayer);
                    AI_Move[] AIMoves = new AI_Move[moves.length];
                    for (int i = 0; i < moves.length; i++) {
                        AIMoves[i] = new AI_Move(moves[i], this);
                    }
                    Arrays.sort(AIMoves);
                    makeMove(currentPlayer, AIMoves[0].getMove());
                    playerCurrentRewards.remove(0);
                    DragonGUI.updateDragon(currentPlayer, this, player1Dragon, player2Dragon);
                    LionGUI.updateLion(currentPlayer, this, Player1Lion, Player2Lion);
                    updateHydra(currentPlayer);
                    updateGaia(currentPlayer);
                    updatePhoenix(currentPlayer);
                    nextTurn();
                    break;
                }
                else {
                    attackValue = getInputAttackValue((Stage) player1Dragon.getScene().getWindow(), "Enter Essence Bonus Value:");
                    ArcanePrism whiteDice = new ArcanePrism(attackValue);
                    if (attackValue > 0 && attackValue <= 6) {
                        if (getPossibleMovesForADie(currentPlayer, whiteDice).length == 0) {
                            showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Choose a valid value that can correspond to ANY realm");
                            rewardHandler();
                        } else {
                            playerCurrentRewards.remove(0);
                            currentDiceSelection = whiteDice;
                            showGrid(whiteDice, currentPlayer);
                        }
                    } else if (attackValue > 6 && attackValue <= 12) {
                        if (!currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.GREEN).isAttackable()) {
                        showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "The green realm was fully conquered , choose a value for another realm");
                            rewardHandler();
                        }
                    GreenDice greenDice2 = new GreenDice(attackValue - this.getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue());
                    if (attackValue <= 0 || attackValue > 12 || getPossibleMovesForADie(currentPlayer,greenDice2).length == 0) {
                            showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Choose a valid value that can correspond to ANY realm");
                            rewardHandler();
                        } else {
                            playerCurrentRewards.remove(0);
                            currentDiceSelection = greenDice2;
                            showGrid(greenDice2, currentPlayer);
                        }
                    } else {
                        showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Choose a valid value that can correspond to ANY realm");
                        rewardHandler();
                    }
                }
                break;
        }
    }

    public void roundRewardHandler() {
        if (this.getGameStatus().getTurnNumber() == 0 && playerGainedRoundReward[(currentPlayer == this.getGameBoard().getPlayer1()) ? 0 : 1] == false) {
            if (roundRewards[this.getGameStatus().getRoundNumber() - 1] != RewardType.EMPTY && roundRewards[this.getGameStatus().getRoundNumber() - 1] != RewardType.X) {
                playerCurrentRewards.add(roundRewards[this.getGameStatus().getRoundNumber() - 1]);
            }
            playerGainedRoundReward[(currentPlayer == this.getGameBoard().getPlayer1()) ? 0 : 1] = true;
        }
    }

    @Override
    public boolean makeMove(Player player, Move move) {
        if (player == null) {
            System.out.println("Player is null");
            return false;
        }
        if (move == null) {
            System.out.println("Move is null");
            return false;
        }
        RewardType[] rewardTypes;
        Creature currentCreature = player.getScoreSheet().getCreatureByRealm(move.getDice());
        RealmColor currentRealmColor = move.getDice().getRealm();
        try {
            rewardTypes = currentCreature.attack(move.getDice());
        } catch (NullPointerException | InvalidMoveException | DiceRollException e) {
            System.out.println(e.getMessage());
            return false;
        }
        player.getGameScore().updateRealmScore(currentRealmColor, currentCreature.getScore());
        // 2ol2of ya hany
        if (rewardTypes != null) {
            if (rewardTypes.length == 2) {
                if (rewardTypes[1].ordinal() < rewardTypes[0].ordinal()) {
                    RewardType temp = rewardTypes[0];
                    rewardTypes[0] = rewardTypes[1];
                    rewardTypes[1] = temp;
                }
            }
            for (int i = 0; i < rewardTypes.length; i++) {
                // process the reward as needed
                if (rewardTypes[i] != RewardType.EMPTY) {
                    playerCurrentRewards.add(rewardTypes[i]);
                }
            }
        }
        return true;
    }


    protected void nextTurn() {
        if (isArcaneSelection) {
            currentPlayerLabel.setText(currentPlayer.getName());
            if (!playerCurrentRewards.isEmpty()) {
                rollDice.setDisable(true);
                isRewardHandling = false;
                rewardHandler();
                return;
            }
            isArcaneSelection = false;
            resetDiceSelection();
            passiveTurn();
            return;
        }
        if (switchPlayerCounter > 1) {
            //arcane
            isGameEnded = !this.getGameStatus().updateRound();
            roundNumberLabel.setText("Round " + getGameStatus().getRoundNumber());
            playerGainedRoundReward[0] = false;
            playerGainedRoundReward[1] = false;
            switchPlayerCounter = 0;
        }
        if(!isGameEnded)
        roundRewardHandler();
        Label label = new Label("Passive turn(s)");

// Set background color with a gradient
        label.setBackground(new Background(new BackgroundFill(Color.web("#8a2be2"), new CornerRadii(10), null)));

// Set border color and style
        label.setBorder(new Border(new BorderStroke(
                Color.web("#9370DB"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(3)
        )));

// Set padding
        label.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));

// Set font
        label.setFont(Font.font("Papyrus", FontWeight.BOLD, FontPosture.ITALIC, 18));
        label.setTextFill(Color.WHITE);

// Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        label.setEffect(dropShadow);

        //if(reward not empty)
        player1State.setText(getGameBoard().getPlayer1().getPlayerStatus() + "");
        player2State.setText(getGameBoard().getPlayer2().getPlayerStatus() + "");
        disableAllDices();
        resetDiceSelection();
        resetGridColor();
        if (currentPlayer instanceof AI_Player) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
                updateDicePositions();
            }));
            timeline.play();
        } else {
            updateDicePositions();
        }

        if (!playerCurrentRewards.isEmpty()) {
            rollDice.setDisable(true);
            isRewardHandling = false;
            rewardHandler();
            return;
        }

        if (isGameEnded) {
            try {
                endGame();
                return;
            } catch (Exception e) {
                System.out.println("error ending the game");
            }
        }
        if (currentPlayer.getPlayerStatus() == PlayerStatus.ACTIVE) {
            if (this.getGameStatus().getTurnNumber() >= maxTurnNumberActive || getAvailableDice().length == 0) {
                this.getGameStatus().resetTurn();
                currentPlayer = this.getPassivePlayer();
                if (currentPlayer == this.getGameBoard().getPlayer1()) {
                    UsedDicesPlayer1.getChildren().add(label);
                } else {
                    UsedDicesPlayer2.getChildren().add(label);
                }
                passiveTurn();
            } else {
                this.getGameStatus().updateTurn();
                isDiceRolled = false;
                currentDiceSelection = null;
                if ((!isPVP) && currentPlayer instanceof AI_Player) {
                    Dice[] availableDice = rollDice();
                    disableActiveDices();
                    int AI_Selection;
                    Move[] moves = this.getPossibleMovesForAvailableDice(currentPlayer);
                    AI_Move[] AIMoves = new AI_Move[moves.length];
                    for (int i = 0; i < moves.length; i++) {
                        AIMoves[i] = new AI_Move(moves[i], this);
                    }
                    Arrays.sort(AIMoves);

                    disableAllDices();
                    isDiceSelected = true;
                    AI_Move AImove = AIMoves[0];
                    currentDiceSelection = pickAI_Dice(availableDice, AImove);
                    selectDice(currentDiceSelection, currentPlayer);
                    timeWarp.setDisable(true);
                    if (getGameStatus().getTurnNumber() == maxTurnNumberActive) {
                        Dice[] dices = getAllDice();
                        for (Dice dice : dices) {
                            if (dice.getDiceLocation() == DiceLocation.ACTIVE)
                                dice.updateDiceLocation(DiceLocation.FORGOTTEN);
                        }
                    }
                    Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
                        updateDicePositions();
                    }));
                    timeline2.play();
//                    Timeline timeline = new Timeline();
//                    for (Dice dice : getAvailableDice())
//                        reverseAnimate(timeline, dice);
//                    timeline.setCycleCount(1);
//                    timeline.play();
                    makeMove(currentPlayer, AImove.getMove());
                    DragonGUI.updateDragon(currentPlayer, this, player1Dragon, player2Dragon);
                    LionGUI.updateLion(currentPlayer, this, Player1Lion, Player2Lion);
                    updateHydra(currentPlayer);
                    updateGaia(currentPlayer);
                    updatePhoenix(currentPlayer);
                    disableGrids();
                    if (askYesNoQuestion((Stage) player1Dragon.getScene().getWindow(), "Press Yes for Badawayyy THE SUPREME WIZARD to continue\n (Note: Pressing no ends the game early)"))
                        nextTurn();
                    else {try {
                        isGameEnded=true;
                        endGame();
                        return;
                    } catch (Exception e) {
                        System.out.println("error ending the game");
                    }}

                } else {
                    rollDice.setDisable(false);
                }
            }
        } else {
            this.getGameStatus().updateTurn();
            passiveTurn();
        }

    }


    private void endGame() throws IOException {
        int player1Score = this.getGameBoard().getPlayer1().getGameScore().calcTotalScore();
        int player2Score = this.getGameBoard().getPlayer2().getGameScore().calcTotalScore();
        if (player1Score > player2Score) {
            this.getGameStatus().setGameState(GameState.PLAYER_1_WINS);
        } else if (player2Score > player1Score) {
            this.getGameStatus().setGameState(GameState.PLAYER_2_WINS);
        } else {
            this.getGameStatus().setGameState(GameState.DRAW);
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndScene.fxml"));
        Parent root = loader.load();
        EndSceneController control = loader.getController();
        control.setPlayers(this.getGameBoard().getPlayer1(), this.getGameBoard().getPlayer2());
        control.setGameState(this.getGameStatus().getGameState());
        stage = (Stage) RedDice.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    public void disableActiveDices() {
        ActiveDices.setDisable(true);
    }

    public void activateActiveDices() {
        ActiveDices.setDisable(false);
    }

    public void activateForgottenDices() {
        ForgottenDices.setDisable(false);
    }

    public void disableForgottenDices() {
        ForgottenDices.setDisable(true);
    }

    private void resetGridColor() {
    }

    private void disableAllDices() {
        disableForgottenDices();
        disableActiveDices();
    }

    public void passiveTurn() {
        currentPlayer = this.getPassivePlayer();
        currentPlayerLabel.setText(currentPlayer.getName());
        Dice[] forgottenDice = getForgottenRealmDice();
        boolean noAvailableMoves = true;
        for (Dice d : forgottenDice) {
            if (this.getPossibleMovesForADie(currentPlayer, d).length != 0) {
                noAvailableMoves = false;
                break;
            }
        }
        if (noAvailableMoves || this.getGameStatus().getTurnNumber() > maxTurnNumberPassive) {
            // playerRoleCounter++;

            UsedDicesPlayer1.getChildren().clear();
            UsedDicesPlayer2.getChildren().clear();

            if (this.getActivePlayer().hasArcaneBoost() && player1ArcaneAccept) {
                if (canUseArcane(this.getActivePlayer()) && askForArcane(this.getActivePlayer())) {
                    currentPlayer = this.getActivePlayer();
                    currentPlayerLabel.setText(currentPlayer.getName());
                    disableAllDices();
                    disableGrids();
                    rollDice.setDisable(true);
                    ActiveDices.getChildren().clear();
                    ForgottenDices.getChildren().clear();
                    for (ImageView imageView : diceImages) {
                        ActiveDices.getChildren().add(imageView);
                    }
                    useArcaneBoost(this.getActivePlayer());
                    return;
                }
            } else {
                player1ArcaneAccept = false;
            }
            this.getGameBoard().resetUseByBoost();
            if (this.getPassivePlayer().hasArcaneBoost()) {
                if (canUseArcane(this.getPassivePlayer()) && askForArcane(this.getPassivePlayer()) && player2ArcaneAccept) {
                    currentPlayer = this.getPassivePlayer();
                    currentPlayerLabel.setText(currentPlayer.getName());
                    disableAllDices();
                    disableGrids();
                    rollDice.setDisable(true);
                    ActiveDices.getChildren().clear();
                    ForgottenDices.getChildren().clear();
                    for (ImageView imageView : diceImages) {
                        ActiveDices.getChildren().add(imageView);
                    }
                    useArcaneBoost(this.getPassivePlayer());
                    return;
                }
            } else {
                player2ArcaneAccept = false;
            }


            player1State.setText(getGameBoard().getPlayer1().getPlayerStatus() + "");
            player2State.setText(getGameBoard().getPlayer2().getPlayerStatus() + "");
            switchPlayerCounter++;
            UsedDicesPlayer1.getChildren().clear();
            UsedDicesPlayer2.getChildren().clear();
            Timeline timeline = new Timeline();
            for (Dice dice : getAllDice())
                reverseAnimate(timeline, dice);
            timeline.setCycleCount(1);
            timeline.play();
            this.getGameStatus().setTurnNumber(0);
            switchPlayer();
            currentPlayerLabel.setText(currentPlayer.getName());
            player1ArcaneAccept = true;
            player2ArcaneAccept = true;
            currentPlayer = this.getActivePlayer();
            this.getGameBoard().resetAllDices();
            //handle for usebyboost too
            isDiceRolled = false;
            currentDiceSelection = null;
            if (currentPlayer instanceof AI_Player) {
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
                    updateDicePositions();
                }));
                timeline2.play();
                nextTurn();
            } else {
                nextTurn();
            }

        } else {
            if ((!isPVP) && currentPlayer instanceof AI_Player) {
                int AI_Selection;
                Move[] moves = this.getPossibleMovesForForgottenDice(currentPlayer);
                AI_Move[] AIMoves = new AI_Move[moves.length];
                for (int i = 0; i < moves.length; i++) {
                    AIMoves[i] = new AI_Move(moves[i], this);
                }
                Arrays.sort(AIMoves);

                AI_Move AImove = AIMoves[0];
                isDiceSelected = true;
                currentDiceSelection = pickAI_Dice(forgottenDice, AImove);
                selectDice(currentDiceSelection, currentPlayer);
                timeWarp.setDisable(true);
                Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
                    updateDicePositions();
                }));
                timeline3.play();
                Timeline timeline = new Timeline();
                for (Dice dice : getAvailableDice())
                    reverseAnimate(timeline, dice);
                timeline.setCycleCount(1);
                timeline.play();
                makeMove(currentPlayer, AImove.getMove());
                DragonGUI.updateDragon(currentPlayer, this, player1Dragon, player2Dragon);
                LionGUI.updateLion(currentPlayer, this, Player1Lion, Player2Lion);
                updateHydra(currentPlayer);
                updateGaia(currentPlayer);
                updatePhoenix(currentPlayer);
                disableGrids();
                if (askYesNoQuestion((Stage) player1Dragon.getScene().getWindow(), "Press Yes for Badawayyy THE SUPREME WIZARD to continue\n (Note: Pressing no ends the game early)"))
                    nextTurn();
                else {try {
                    isGameEnded=true;
                    endGame();
                    return;
                } catch (Exception e) {
                    System.out.println("error ending the game");
                }}


            } else {
                activateForgottenDices();
            }

        }
    }

    @Override
    public boolean askForArcane(Player player) {
        if (!isPVP && player instanceof AI_Player)
            return true;
        //return false;
        return askYesNoQuestion((Stage) player1Dragon.getScene().getWindow(), "Do you want to use Arcane Boost " + player.getName() + "\nYou have " + player.getArcaneBoostCount() + " Arcane Boosts");
    }

    public void useArcaneBoost(Player player) {
        player.useArcaneBoost();
        refreshPowerupsCounters();
        isArcaneSelection = true;
        if (!isPVP && currentPlayer instanceof AI_Player) {
            Dice[] alldice = getAllDice();
            ArrayList<Move> possibleMoves = new ArrayList<>();
            for (Dice dice : alldice) {
                if (dice instanceof GreenDice && !getGameBoard().getDice(RealmColor.WHITE.ordinal()).isUsedByBoost()) {
                    continue; // handle duplicate move of Green and white Important Condition Skip Green only if White is Active
                }
                if (!dice.isUsedByBoost()){
                Move[] temp = getPossibleMovesForADie(player, dice);
                Collections.addAll(possibleMoves, temp);}
            }
            Move[] Moves = new Move[possibleMoves.size()];
            Moves = possibleMoves.toArray(Moves);

            AI_Move[] AIMoves = new AI_Move[Moves.length];
            for (int i = 0; i < Moves.length; i++) {
                AIMoves[i] = new AI_Move(Moves[i], this);
            }
            Arrays.sort(AIMoves);

            disableAllDices();
            isDiceSelected = true;
            AI_Move AImove = AIMoves[0];
            currentDiceSelection = pickAI_Dice(alldice, AImove);
            selectDice(currentDiceSelection, currentPlayer);
            timeWarp.setDisable(true);

            Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                updateDicePositions();
            }));
            timeline2.play();

            makeMove(currentPlayer, AImove.getMove());
            DragonGUI.updateDragon(currentPlayer, this, player1Dragon, player2Dragon);
            LionGUI.updateLion(currentPlayer, this, Player1Lion, Player2Lion);
            updateHydra(currentPlayer);
            updateGaia(currentPlayer);
            updatePhoenix(currentPlayer);
            disableGrids();
            if (askYesNoQuestion((Stage) player1Dragon.getScene().getWindow(), "Press Yes for Badawayyy THE SUPREME WIZARD to continue\n (Note: Pressing no ends the game early"))
                nextTurn();
            else {try {
                isGameEnded=true;
                endGame();
                return;
            } catch (Exception e) {
                System.out.println("error ending the game");
            }}

        } else {
            ActiveDices.setDisable(false);
        }
    }

    public void updateHydra(Player player) {
        HydraGUI.updateHydra(currentDiceSelection, this, allGrids, player);
    }

    public void setNamePlayer1(String player1Name) {
        Player1Name.setText(player1Name);
        this.getGameBoard().getPlayer1().setName(player1Name);
        currentPlayerLabel.setText(player1Name);
    }

    public void setNamePlayer2(String player2Name) {
        Player2Name.setText(player2Name);
        this.getGameBoard().getPlayer2().setName(player2Name);
    }

    public void setAIPlayer() {
        this.getGameBoard().setAIPlayer();
        Player2Name.setText(this.getGameBoard().getPlayer2().getName());
    }

    public void animateDie(Timeline timeline, Dice die) {
        final int FRAME_COUNT = 120; // Total number of frames
        final int FRAME_RATE = 60; // Frame rate in frames per second
        List<Image> images = new ArrayList<>();
        ImageView imageView = null;
        String str = "";
        switch (die.getRealm()) {
            case RED:
                imageView = RedDice;
                str = "RedDie";
                break;
            case GREEN:
                imageView = GreenDice;
                str = "GreenDie";
                break;
            case BLUE:
                imageView = BlueDice;
                str = "BlueDie";
                break;
            case MAGENTA:
                imageView = MagentaDice;
                str = "MagentaDie";
                break;
            case YELLOW:
                imageView = YellowDice;
                str = "YellowDie";
                break;
            case WHITE:
                imageView = WhiteDice;
                str = "WhiteDie";
                break;
            default:
                break;
        }
        //LOADING IMAGES
        for (int i = 1; i <= FRAME_COUNT; i++) {
            try {
                String imagePath =
                        String.format("src\\main\\resources\\Dice\\%s\\%s" + die.getValue() + "\\%s" + die.getValue() + "%04d.png", str, str, str, i);

                Image image = new Image(new FileInputStream(new File(imagePath)));
                images.add(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //LOADING KEYFRAMES
        for (int i = 0; i < FRAME_COUNT; i++) {
            int frameIndex = i;
            ImageView finalImageView = imageView;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(1000 / FRAME_RATE * i), event -> {
                finalImageView.setImage(images.get(frameIndex));
            });
            timeline.getKeyFrames().add(keyFrame);
        }

    }

    private void reverseAnimate(Timeline timeline, Dice die) {
        final int FRAME_COUNT = 121; // Total number of frames
        final int FRAME_RATE = 60; // Frame rate in frames per second
        List<Image> images = new ArrayList<>();
        ImageView imageView = null;
        String str = "";
        switch (die.getRealm()) {
            case RED:
                imageView = RedDice;
                str = "RedDie";
                break;
            case GREEN:
                imageView = GreenDice;
                str = "GreenDie";
                break;
            case BLUE:
                imageView = BlueDice;
                str = "BlueDie";
                break;
            case MAGENTA:
                imageView = MagentaDice;
                str = "MagentaDie";
                break;
            case YELLOW:
                imageView = YellowDice;
                str = "YellowDie";
                break;
            case WHITE:
                imageView = WhiteDice;
                str = "WhiteDie";
                break;
            default:
                break;
        }
        //LOADING IMAGES
        for (int i = FRAME_COUNT - 1; i > 0; i--) {
            try {
                String imagePath =
                        String.format("src\\main\\resources\\Dice\\%s\\%s" + die.getValue() + "\\%s" + die.getValue() + "%04d.png", str, str, str, i);

                Image image = new Image(new FileInputStream(new File(imagePath)));
                images.add(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        String imagePath =
                String.format("src\\main\\resources\\Dice\\%s\\%sIdle.png", str, str);
        Image image = null;
        try {
            image = new Image(new FileInputStream(new File(imagePath)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        images.add(image);
        //LOADING KEYFRAMES
        for (int i = 0; i < FRAME_COUNT; i++) {
            int frameIndex = i;
            ImageView finalImageView = imageView;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(1000 / FRAME_RATE * i), event -> {
                finalImageView.setImage(images.get(frameIndex));
            });
            timeline.getKeyFrames().add(keyFrame);
        }

    }

    @FXML
    @Override
    public Dice[] rollDice() {
        if (isDiceRolled) {
            //add handling for already rolled dice
            showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Dice already rolled");
        } else {
            super.rollDice();
            isDiceRolled = true;
            rollDice.setDisable(true);
            Dice[] availableDices = getAvailableDice();
            boolean noAvailableMoves = true;
            for (Dice dice : availableDices) {
                if (this.getPossibleMovesForADie(currentPlayer, dice).length != 0) {
                    noAvailableMoves = false;
                }
            }
            //add animation for the dice rolling
            Timeline timeline = new Timeline();
            for (Dice dice : availableDices) {
                animateDie(timeline, dice);
            }
            timeline.setCycleCount(1);
            timeline.play();
            Timer timer = new Timer();
            if (noAvailableMoves) {
                showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "No available moves");
                if (currentPlayer.hasTimeWarps()) {
                    if (askForTimeWarp()) {
                        useTimeWarp(currentPlayer);
                    } else {
                        Dice[] availableDice = getAvailableDice();
                        for (Dice dice : availableDice) {
                            dice.updateDiceLocation(DiceLocation.FORGOTTEN);
                        }
                        nextTurn();
                    }

                } else {
                    nextTurn();
                }

            } else {

                TimerTask task = new TimerTask() {
                    public void run() {
                        activateActiveDices();
                        timer.cancel(); // Terminate the timer thread
                    }
                };
                timer.schedule(task, 2000);

                if (currentPlayer.hasTimeWarps())
                    timeWarp.setDisable((false));
            }
        }
        return getAvailableDice();
    }

    @FXML
    private boolean timeWarp() {
        if (isDiceRolled) {
            return useTimeWarp(currentPlayer);
        }
        return false;
    }

    @Override
    protected boolean useTimeWarp(Player player) {
        if (player == null) {
            System.out.println("Player is null");
            return false;
        }
        if (player.useTimeWarp()) {
            refreshPowerupsCounters();
            isDiceRolled = false;
            rollDice.setDisable(false);
            disableGrids();
            disableActiveDices();
            Timeline timeline = new Timeline();
            for (Dice dice : getAvailableDice())
                reverseAnimate(timeline, dice);
            timeline.setCycleCount(1);
            timeline.play();
            if (!player.hasTimeWarps())
                timeWarp.setDisable(true);
            return true;
            //reset the shapes of available dices
            //enable the rollbutton
        }
        return false;
    }


    private boolean askForTimeWarp() {
        if (currentPlayer instanceof AI_Player)
            return false;
        return askYesNoQuestion((Stage) player1Dragon.getScene().getWindow(), "Do you want to use Time warp " + currentPlayer.getName() + "\nYou have " + currentPlayer.getTimeWarpsCount() + " Time warps");
    }

    public void onDiceClick(javafx.scene.input.MouseEvent event) {
        if (isDiceSelected) {
            showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Dice already selected");
        } else {
            ImageView img = (ImageView) event.getSource();
            Dice d = null;
            for (int i = 0; i < diceImages.length; i++) {
                if (diceImages[i] == img) // i mean refrences
                {
                    d = getGameBoard().getDice(i);
                }
            }
            if (d == null) {
                //handle error
            } else {
                if (d.isUsedByBoost())
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "Dice was already used by ArcaneBoost");
                else if (getPossibleMovesForADie(currentPlayer, d).length != 0 && getPossibleMovesForADie(currentPlayer, d)[0] != null) {
                    if (selectDice(d, currentPlayer)) {
                        if (isArcaneSelection) {
                            d.useByBoost();
                        }
                        disableAllDices();
                        isDiceSelected = true;
                        currentDiceSelection = d;
                        showGrid(d, currentPlayer);
                        timeWarp.setDisable(true);
                        if (getGameStatus().getTurnNumber() == maxTurnNumberActive) {
                            Dice[] dices = getAllDice();
                            for (Dice dice : dices) {
                                if (dice.getDiceLocation() == DiceLocation.ACTIVE)
                                    dice.updateDiceLocation(DiceLocation.FORGOTTEN);
                            }
                        }
                        if (!isArcaneSelection)
                            updateDicePositions();
                        Timeline timeline = new Timeline();
                        for (Dice dice : getAvailableDice())
                            reverseAnimate(timeline, dice);
                        timeline.setCycleCount(1);
                        timeline.play();
                    }
                } else {
                    //ANNOUNCEMENT
                    showTimedPopup((Stage) player1Dragon.getScene().getWindow(), "No available moves for this dice");
                }
            }
        }
    }


    private void showGrid(Dice d, Player player) {
        //still need to handle case of white die
        RealmColor color = d.getRealm();
        int i = (currentPlayer == getGameBoard().getPlayer1()) ? 0 : 1;
        if (color != RealmColor.WHITE) {
            allGrids[i][color.ordinal()].setDisable(false);
            if (color== RealmColor.GREEN) {
            gridGaia1p.setDisable(false);
            gridGaia2p.setDisable(false);}
        } else {
            for (GridPane gridpane : allGrids[i]) {
                gridpane.setDisable(false);
            }
            gridGaia1p.setDisable(false);
            gridGaia2p.setDisable(false);
        }
    }

    @Override
    public boolean selectDice(Dice dice, Player player) {
        // Create and style the label for Player 1
        Label label = new Label("Turn " + this.getGameStatus().getTurnNumber() + ":");
        if (isArcaneSelection)
            label.setText("Arcane Turn :");
        // Set background color
        label.setBackground(new Background(new BackgroundFill(Color.web("#8a2be2"), new CornerRadii(10), null)));
        // Set border color and style
        label.setBorder(new Border(new BorderStroke(
                Color.web("#9370DB"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(3)
        )));
        // Set padding
        label.setPadding(new Insets(5, 10, 5, 10));
        // Set font
        label.setFont(Font.font("Papyrus", FontWeight.BOLD, FontPosture.ITALIC, 18));
        label.setTextFill(Color.WHITE);
        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        label.setEffect(dropShadow);
        //super changes the dice states behind the scenes to forgotten or used..
        // the rest of the code places the dice in the "used" VBox of the player
        //in case that this player was the active player
        if (super.selectDice(dice, player)) {
            RealmColor color = dice.getRealm();
            //think about arcane before publishing
            if (currentPlayer == getGameBoard().getPlayer1()) {
                UsedDicesPlayer1.getChildren().add(label);
                UsedDicesPlayer1.getChildren().add(diceImages[color.ordinal()]);
            } else if (currentPlayer == getGameBoard().getPlayer2() && !(currentPlayer instanceof AI_Player)) {
                UsedDicesPlayer2.getChildren().add(label);
                UsedDicesPlayer2.getChildren().add(diceImages[color.ordinal()]);
            }
            if (currentPlayer instanceof AI_Player) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
                    updateDicePositions();
                    UsedDicesPlayer2.getChildren().add(label);
                    UsedDicesPlayer2.getChildren().add(diceImages[color.ordinal()]);
                }));
                timeline.play();
            } else
                updateDicePositions();
            return true;
        }
        return false;

    }


    private boolean askYesNoQuestion(Stage owner, String question) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Yes/No Question");
        dialog.setHeaderText(question);

        // Set the modality and owner to keep the dialog on top of the primary stage
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);

        // Set the button types
        ButtonType yesButtonType = new ButtonType("Yes");
        ButtonType noButtonType = new ButtonType("No");
        dialog.getDialogPane().getButtonTypes().addAll(yesButtonType, noButtonType);

        // Show the dialog and wait for the result

        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() && result.get() == yesButtonType;


    }

    private int getInputAttackValue(Stage owner, String message) {
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("ElBadawayyy is asking");
        dialog.setHeaderText(message);

        // Set the modality and owner to keep the dialog on top of the primary stage
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);

        // Set the button types
        ButtonType submitButtonType = new ButtonType("Submit", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().add(submitButtonType);

        // Create a TextField for input
        TextField inputField = new TextField();
        inputField.setPromptText(message);

        // Create a VBox to hold the input field
        VBox vbox = new VBox(inputField);
        vbox.setSpacing(10);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(vbox);

        // Enable/Disable submit button depending on the input
        Button submitButton = (Button) dialog.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(!isValidInteger(newValue));
        });

        // Convert the result to an integer when the submit button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                try {
                    return Integer.parseInt(inputField.getText());
                } catch (NumberFormatException e) {
                    return null; // This should not happen because of the listener
                }
            }
            return null;
        });

        // Show the dialog and wait for the result
        Optional<Integer> result = Optional.empty();
        while (result.isEmpty()) {
            result = dialog.showAndWait();
            if (result.isEmpty() || !isValidInteger(inputField.getText())) {
                showAlert(owner, "Invalid input", "Please enter a valid integer.");
                result = Optional.empty(); // Reset result to force the loop
            }
        }
        return result.orElse(-1); // Return -1 if no valid input was provided
    }

    private boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(Stage owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showTimedPopup(Stage owner, String message) {
        // Create a new stage for the popup
        Stage popupStage = new Stage();

        // Set the stage properties
        popupStage.initModality(Modality.NONE);
        popupStage.initOwner(owner);
        popupStage.initStyle(StageStyle.DECORATED);

        // Create a label with the custom message
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        // Create a VBox layout to hold the label
        VBox vbox = new VBox(messageLabel);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20)); // Add margins
        vbox.setStyle("-fx-background-color: #333;");

        // Create a scene with the VBox layout
        Scene scene = new Scene(vbox, 300, 150); // Set fixed size

        // Set the scene to the popup stage
        popupStage.setScene(scene);

        // Show the popup stage
        popupStage.show();

        // Center the popup stage on the screen
        popupStage.centerOnScreen();

        // Create a timeline to close the popup after 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            popupStage.close();
        }));

        // Start the timeline
        timeline.play();
    }

    private Dice pickAI_Dice(Dice[] availableDices, AI_Move move) {
        switch (move.getMove().getDice().getRealm()) {
            case RED:
            case BLUE:

            case MAGENTA:

            case YELLOW:
                for (Dice dice : availableDices) {
                    if ((dice.getRealm() == move.getMove().getDice().getRealm() || dice.getRealm() == RealmColor.WHITE) &&
                            dice.getValue() == move.getMove().getDice().getValue()) {

                        return dice;
                    }
                }


            case GREEN:
                int value = 6;
                Dice lowervaluedie = availableDices[0];
                for (Dice dice : availableDices) {
                    if ((dice.getRealm() == move.getMove().getDice().getRealm() || dice.getRealm() == RealmColor.WHITE) &&
                            dice.getValue() <= value) {
                        lowervaluedie = dice;
                        value = dice.getValue();
                    }
                }
                return lowervaluedie;


            default:
                return availableDices[0];
        }


    }

    //override startgame
    //rollDice() may be used as a button
    //override select dice (or add a method to show the values and then
    //use select afterwards when clicking on the grid)
    //inputs must be overridden: InputAttackValue,InputAttackColor(Player player),InputAttackColor(PLayer player, Dice dice),InputDragonNumber
    //AskforArcane(),askforTime(),askForDiceSelection(),Gameloop(),halfRound(),active,forgotten,arcane
    // we need to get all possible moves from each realm and if the realm was not attachable we need to disable it
    // maybe it will be handled inside each GUIrealm
    //e

    @FXML
    void onHoverRed(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.RED.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? player1Dragon : player2Dragon;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    // do stuff
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitRed(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.RED.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? player1Dragon : player2Dragon;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    button.setStyle(""); // Reset the style to remove the border
                }
            }
        }
    }

    @FXML
    void onHoverGreen(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.GREEN.ordinal()].getValue() + getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? gridGaia1 : gridGaia2;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    // do stuff
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitGreen(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.GREEN.ordinal()].getValue() + getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? gridGaia1 : gridGaia2;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    button.setStyle(""); // Reset the style to remove the border
                }
            }
        }
    }

    @FXML
    void onHoverBlue(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.BLUE.ordinal()].getValue();
        Hydra hydra = (Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE);
        int constrain = hydra.getHydraAttackValues()[hydra.getCurrentIndex()];
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Hydra : Player2Hydra;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer index = GridPane.getColumnIndex(button);
                if (index == null) index = 0;
                if (!button.getText().trim().equals("X") && constrain <= attackNumber && index - 1 == hydra.getCurrentIndex()) {
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitBlue(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.BLUE.ordinal()].getValue();
        Hydra hydra = (Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE);
        int constrain = hydra.getHydraAttackValues()[hydra.getCurrentIndex()];
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Hydra : Player2Hydra;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer index = GridPane.getColumnIndex(button);
                if (index == null) index = 0;
                if (!button.getText().trim().equals("X") && constrain <= attackNumber && index - 1 == hydra.getCurrentIndex()) {
                    button.setStyle("");
                }
            }
        }
    }

    @FXML
    void onHoverYellow(MouseEvent event) {
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Lion : Player2Lion;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().trim().equals("0")) {
                    button.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitYellow(MouseEvent event) {
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Lion : Player2Lion;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().trim().equals("0")) {
                    button.setStyle(""); // Reset the style to remove the border
                }
            }
        }
    }

    @FXML
    void onHoverMagenta(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.MAGENTA.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Phoenix : Player2Phoenix;
        Phoenix phoenix = (Phoenix) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA);
        int constraint = phoenix.getPrevAttackValue();
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && constraint < attackNumber && Integer.parseInt(button.getText().trim()) == 0) {
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitMagenta(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.MAGENTA.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Phoenix : Player2Phoenix;
        Phoenix phoenix = (Phoenix) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA);
        int constraint = phoenix.getPrevAttackValue();
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && constraint < attackNumber && Integer.parseInt(button.getText().trim()) == 0) {
                    button.setStyle("");
                }
            }
        }
    }

    @FXML
    private void onHoverWhite(MouseEvent event) {
    }

    @FXML
    private void onHoverExitWhite(MouseEvent event) {
    }


    @FXML
    void onHoverWhiteRed(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? player1Dragon : player2Dragon;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    // do stuff
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitWhiteRed(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? player1Dragon : player2Dragon;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && Integer.parseInt(button.getText()) == attackNumber) {
                    button.setStyle(""); // Reset the style to remove the border
                }
            }
        }
    }


    @FXML
    void onHoverWhiteBlue(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        Hydra hydra = (Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE);
        int constrain = hydra.getHydraAttackValues()[hydra.getCurrentIndex()];
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Hydra : Player2Hydra;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer index = GridPane.getColumnIndex(button);
                if (index == null) index = 0;
                if (!button.getText().trim().equals("X") && constrain <= attackNumber && index - 1 == hydra.getCurrentIndex()) {
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitWhiteBlue(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        Hydra hydra = (Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE);
        int constrain = hydra.getHydraAttackValues()[hydra.getCurrentIndex()];
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Hydra : Player2Hydra;
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer index = GridPane.getColumnIndex(button);
                if (index == null) index = 0;
                if (!button.getText().trim().equals("X") && constrain <= attackNumber && index - 1 == hydra.getCurrentIndex()) {
                    button.setStyle("");
                }
            }
        }
    }

    @FXML
    void onHoverWhiteMagenta(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Phoenix : Player2Phoenix;
        Phoenix phoenix = (Phoenix) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA);
        int constraint = phoenix.getPrevAttackValue();
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && constraint < attackNumber && Integer.parseInt(button.getText().trim()) == 0) {
                    button.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
                }
            }
        }
    }

    @FXML
    private void onHoverExitWhiteMagenta(MouseEvent event) {
        int attackNumber = getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue();
        GridPane currGrid = (currentPlayer == getGameBoard().getPlayer1()) ? Player1Phoenix : Player2Phoenix;
        Phoenix phoenix = (Phoenix) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA);
        int constraint = phoenix.getPrevAttackValue();
        for (Node node : currGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (!button.getText().trim().equals("X") && constraint < attackNumber && Integer.parseInt(button.getText().trim()) == 0) {
                    button.setStyle("");
                }
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isArcaneSelection() {
        return isArcaneSelection;
    }

}