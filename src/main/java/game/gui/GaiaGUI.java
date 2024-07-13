package game.gui;

import game.collectibles.RewardType;
import game.creatures.Gaia;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.Move;
import game.engine.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GaiaGUI {
    public static void updateGaia(PlaySceneController playSceneController, GridPane[][] allGrids, Player player,GridPane gridGaia2p,GridPane gridGaia1p) throws NullPointerException{

        GridPane temp = allGrids[(player == playSceneController.getGameBoard().getPlayer1())? 0 : 1][RealmColor.GREEN.ordinal()];

        ObservableList<Node> no= temp.getChildren();

        for(Node node : no){
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null) row =0;
            if(col == null) col =0;
            if(node instanceof Label){
                if(row>=1 && row<=3 && col == 5){
                    RewardType rew = ((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)).getSide()[row-1];
                    ((Label)node).setText(rew.toString());
                }
                if(row == 4 && col>=1){
                    RewardType rew = ((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)).getBottom()[col-1];
                    ((Label)node).setText(rew.toString());
                }
                if(row == 5 && col>=1){
                    ((Label)node).setText(((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)).getProgress()[col-1]+"");
                }
            }
            if(node instanceof Button){
                ((Button)node).setText(((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)).getGrid()[row-1][col-1]);
            }
        }
        if(player == playSceneController.getGameBoard().getPlayer2()){
            temp = gridGaia2p;
        }else {
            temp = gridGaia1p;
        }

        no= temp.getChildren();

        for(Node node : no){
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if(row == null) row =0;
            if(col == null) col =0;
            if(node instanceof Label){
                if(col>=1){
                    ((Label)node).setText(((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)).getProgress()[col-1]+"");
                }
            }
        }
    }

    public static void helperGaiamove(Player player, ActionEvent event, Dice currentDiceSelection, PlaySceneController playSceneController, GridPane[][] allGrids) {
        Button button = (Button) event.getSource();
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);
        if (row == null)row = 0;
        if (col == null)col = 0;
        if (currentDiceSelection instanceof GreenDice){
            // getting the value of the white die and add it to the attack value
            currentDiceSelection = new GreenDice(currentDiceSelection.getValue() + playSceneController.getGameBoard().getDice()[RealmColor.WHITE.ordinal()].getValue());
        } else if(currentDiceSelection instanceof ArcanePrism)
        {
            currentDiceSelection = new GreenDice(currentDiceSelection.getValue() + playSceneController.getGameBoard().getDice()[RealmColor.GREEN.ordinal()].getValue());
        }else {
            System.out.println("Current dice selection is not GreenDice nor ArcanePrism");
            return;
        }

        // we can delete the other two conditions
        if(button.getText().trim().equals("X")){
            System.out.println("This part is already attacked !!");
        }else {
            if(Integer.parseInt(button.getText()) != (currentDiceSelection.getValue())){
                System.out.println(currentDiceSelection.getValue());
                System.out.println("Current dice selection is not right");
                return;
            }
            button.getStyleClass().removeAll("attackable");
            try {
                playSceneController.makeMove(player,new Move(new GreenDice(currentDiceSelection.getValue()),((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN))));
            }catch (NumberFormatException e) {
                playSceneController.makeMove(player,new Move(new GreenDice(col+(4*(row-1))),((Gaia)player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN))));
            }
            // You should update the go to the next turn
            playSceneController.updateGaia(player);
            button.setDisable(true);
            playSceneController.disableGrids();
            playSceneController.resetDiceSelection();
            playSceneController.nextTurn();
        }
    }

}
