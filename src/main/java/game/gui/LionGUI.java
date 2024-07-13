package game.gui;

import game.collectibles.RewardType;
import game.creatures.Lion;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.YellowDice;
import game.engine.Move;
import game.engine.Player;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javax.swing.plaf.basic.BasicButtonUI;

public class LionGUI {
    public static void updateLion(Player player, PlaySceneController playSceneController, GridPane player1Lion, GridPane player2Lion){
        GridPane currGrid = (player == playSceneController.getGameBoard().getPlayer1()) ? player1Lion : player2Lion;
        ObservableList<Node> children = currGrid.getChildren();
        Lion lion = (Lion) player.getScoreSheet().getCreatureByRealm(RealmColor.YELLOW);
        for (Node node : children) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row == null) row = 0;
            if (col == null) col = 0;
            if (node instanceof Label) {
                Label label = (Label) node;
                if (row == 2 && col > 0) { // handle multipliers
                    if (lion.getLionAttackMultiplier()[col - 1] != 1)
                        label.setText("x" + lion.getLionAttackMultiplier()[col - 1]);
                    else label.setText(" ");
                } else if (row == 3 && col > 0) { // handle rewards
                    if (lion.getRewardValues()[col - 1].toString().equals(RewardType.EMPTY + ""))
                        label.setText(" ");
                    else
                        label.setText(lion.getRewardValues()[col - 1] + "");
                }
            } else if (node instanceof Button) {
                Button button = (Button) node;
                if (row == 1 && col > 0) { // handle hits
                    button.setText(lion.getLionAttackValues()[col - 1] + "");
                }
            }
        }
    }

    public static void player1LionMove(ActionEvent event, PlaySceneController playSceneController, GridPane player1Lion, GridPane player2Lion, Dice currentDice) {
        if(currentDice instanceof ArcanePrism)
        {
            currentDice = new YellowDice(currentDice.getValue());
        }
        if (!(currentDice instanceof YellowDice)) {
            System.out.println("Not a Yellow Dice !");
            return;
        }
        YellowDice yellowDice = (YellowDice) currentDice;
        Button button = (Button) event.getSource();
        Lion lion = (Lion) playSceneController.getGameBoard().getPlayer1().getScoreSheet().getCreatureByRealm(RealmColor.YELLOW);
        playSceneController.makeMove(playSceneController.getGameBoard().getPlayer1(), new Move(yellowDice, lion));
        LionGUI.updateLion(playSceneController.getGameBoard().getPlayer1(), playSceneController, player1Lion, player2Lion);
        System.out.println(button.getText() + playSceneController.getGameBoard().getPlayer1().getName());
        playSceneController.disableGrids();
        playSceneController.nextTurn();
    }

    public static void player2LionMove(ActionEvent event, PlaySceneController playSceneController, GridPane player1Lion, GridPane player2Lion, Dice currentDice) {
        if(currentDice instanceof ArcanePrism)
        {
            currentDice = new YellowDice(currentDice.getValue());
        }
        if (!(currentDice instanceof YellowDice)){
            System.out.println("Not a Yellow Dice !");
            return;
        }
        YellowDice yellowDice = (YellowDice) currentDice;
        Button button = (Button) event.getSource();
        Lion lion = (Lion) playSceneController.getGameBoard().getPlayer2().getScoreSheet().getCreatureByRealm(RealmColor.YELLOW);
        playSceneController.makeMove(playSceneController.getGameBoard().getPlayer2(), new Move(yellowDice, lion));
        LionGUI.updateLion(playSceneController.getGameBoard().getPlayer2(), playSceneController, player1Lion, player2Lion);
        System.out.println(button.getText() + playSceneController.getGameBoard().getPlayer2().getName());
        playSceneController.disableGrids();
        playSceneController.nextTurn();
    }
}
