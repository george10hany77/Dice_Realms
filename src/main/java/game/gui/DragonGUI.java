package game.gui;

import game.collectibles.RewardType;
import game.creatures.Dragon;
import game.creatures.DragonNumber;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.CLIGameController;
import game.engine.GameBoard;
import game.engine.Move;
import game.engine.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Method;

public class DragonGUI {
    public static void updateDragon(Player player, PlaySceneController playSceneController, GridPane player1Dragon, GridPane player2Dragon){
        GridPane currGrid = (player == playSceneController.getGameBoard().getPlayer1()) ? player1Dragon : player2Dragon;
        ObservableList<Node> children = currGrid.getChildren();
        Dragon dragon = (Dragon) player.getScoreSheet().getCreatureByRealm(RealmColor.RED);
        for (Node node : children) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row == null) row = 0;
            if (col == null) col = 0;
            if (node instanceof Button) {
                Button button = (Button) node;
                row--;
                col--;
                if (dragon.getDragonGrid()[row][col] == 0)
                    button.setText("X");
                else
                    button.setText(dragon.getDragonGrid()[row][col]+"");
            }else if (node instanceof Label){
                Label label = (Label) node;
                if (row > 0 && col == currGrid.getColumnCount()-1){// rewards column
                    if (dragon.getDragonReward()[row-1] == RewardType.EMPTY)
                        label.setText("X");
                    else
                        label.setText(dragon.getDragonReward()[row-1]+"");
                } else if (row == currGrid.getRowCount()-1 && col < currGrid.getColumnCount()-1 && col > 0) { // scores column
                    label.setText(dragon.getDragonScores()[col-1]+"");
                }
            }
        }
    }

    public static void dragonMove(ActionEvent event, PlaySceneController playSceneController, GridPane player1Dragon, GridPane player2Dragon, Dice currentDice, Player currPlayer){
        if(currentDice instanceof ArcanePrism)
        {
            currentDice = new RedDice(currentDice.getValue());
        }
        if (!(currentDice instanceof RedDice)){
            System.out.println("Not a red Dice !!");
            return;
        }
        RedDice redDice = (RedDice) currentDice;
        Button button = ((Button) event.getSource());
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);
        if (row == null) row = 0;
        if (col == null) col = 0;
        Dragon dragon = (Dragon) currPlayer.getScoreSheet().getCreatureByRealm(RealmColor.RED);
        row --;
        col --;
        int val;
        DragonNumber dragonNumber;
        if (dragon.getDragonGrid()[row][col] == 0)
            val = 0; // will test the exceptions
        else val = dragon.getDragonGrid()[row][col];
        dragonNumber = DragonNumber.getDragon(col+1);
        if (redDice.getValue() == val) {
            playSceneController.makeMove(currPlayer, new Move(new RedDice(val, dragonNumber), dragon));
            DragonGUI.updateDragon(currPlayer, playSceneController, player1Dragon, player2Dragon);
            playSceneController.disableGrids();
            playSceneController.nextTurn();
        }else {

        }
    }

//    public static void player1DragonMove(ActionEvent event, PlaySceneController playSceneController, GridPane player1Dragon, GridPane player2Dragon, Dice currentDice){
//        if(currentDice instanceof ArcanePrism)
//        {
//            currentDice = new RedDice(currentDice.getValue());
//        }
//        if (!(currentDice instanceof RedDice)){
//            System.out.println("Not a red Dice !!");
//            return;
//        }
//        RedDice redDice = (RedDice) currentDice;
//        Button button = ((Button) event.getSource());
//        System.out.println(button.getText() + "\n" + playSceneController.getGameBoard().getPlayer1().getName());
//        Integer row = GridPane.getRowIndex(button);
//        Integer col = GridPane.getColumnIndex(button);
//        if (row == null) row = 0;
//        if (col == null) col = 0;
//        Dragon dragon = (Dragon) playSceneController.getGameBoard().getPlayer1().getScoreSheet().getCreatureByRealm(RealmColor.RED);
//        row --;
//        col --;
//        int val;
//        DragonNumber dragonNumber;
//        if (dragon.getDragonGrid()[row][col] == 0)
//            val = 0; // will test the exceptions
//        else val = dragon.getDragonGrid()[row][col];
//        dragonNumber = DragonNumber.getDragon(col+1);
//        if (redDice.getValue() == val) {
//            playSceneController.makeMove(playSceneController.getGameBoard().getPlayer1(), new Move(new RedDice(val, dragonNumber), dragon));
//            DragonGUI.updateDragon(playSceneController.getGameBoard().getPlayer1(), playSceneController, player1Dragon, player2Dragon);
//            playSceneController.disableGrids();
//            playSceneController.nextTurn();
//        }else {
//            System.out.println("Not a correct attack value ! " + redDice.getValue());
//        }
//    }
//
//    public static void player2DragonMove(ActionEvent event, PlaySceneController playSceneController, GridPane player1Dragon, GridPane player2Dragon, Dice currentDice){
//        if(currentDice instanceof ArcanePrism)
//        {
//            currentDice = new RedDice(currentDice.getValue());
//        }
//        if (!(currentDice instanceof RedDice)){
//            System.out.println("Not a red Dice !!");
//            return;
//        }
//        RedDice redDice = (RedDice) currentDice;
//        Button button = ((Button) event.getSource());
//        System.out.println(button.getText() + "\n" + playSceneController.getGameBoard().getPlayer2().getName());
//        Integer row = GridPane.getRowIndex(button);
//        Integer col = GridPane.getColumnIndex(button);
//        if (row == null) row = 0;
//        if (col == null) col = 0;
//        Dragon dragon = (Dragon) playSceneController.getGameBoard().getPlayer2().getScoreSheet().getCreatureByRealm(RealmColor.RED);
//        row --;
//        col --;
//        int val;
//        DragonNumber dragonNumber;
//        if (dragon.getDragonGrid()[row][col] == 0)
//            val = 0; // will test the exceptions
//        else val = dragon.getDragonGrid()[row][col];
//        dragonNumber = DragonNumber.getDragon(col+1);
//        if (val != 0 && redDice.getValue() == val) {
//            playSceneController.makeMove(playSceneController.getGameBoard().getPlayer2(), new Move(new RedDice(val, dragonNumber), dragon));
//            DragonGUI.updateDragon(playSceneController.getGameBoard().getPlayer2(), playSceneController, player1Dragon, player2Dragon);
//            playSceneController.disableGrids();
//            playSceneController.nextTurn();
//        }else {
//            System.out.println("Not a correct attack value ! " + redDice.getValue());
//        }
//    }

}
