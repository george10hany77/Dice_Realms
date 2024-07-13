package game.gui;

import game.collectibles.RewardType;
import game.creatures.Hydra;
import game.creatures.Phoenix;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;
import game.engine.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PhoenixGUI {
    public static void phoenixMove(ActionEvent event, Dice currentDiceSelection, PlaySceneController playSceneController, Player currentPlayer, GridPane[][] allGrids)
    {
        int i = (currentPlayer == playSceneController.getGameBoard().getPlayer1())? 0 : 1;
        GridPane phoenix = allGrids[i][RealmColor.MAGENTA.ordinal()];
        Button source = (Button) event.getSource();
        int colIndex = GridPane.getColumnIndex(source);
        if(currentDiceSelection instanceof ArcanePrism)
        {
            currentDiceSelection = new MagentaDice(currentDiceSelection.getValue());
        }
        if(colIndex-1 != ((Phoenix)currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)).getCurrentIndex())
        {
            System.out.println("previous pheonixes weren't attacked!!!");
            return;
        }
        if(currentDiceSelection instanceof MagentaDice)
        {
            if(((Phoenix)currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)).getPrevAttackValue() < currentDiceSelection.getValue())
            {
                playSceneController.makeMove(currentPlayer, new Move(currentDiceSelection, currentPlayer.getScoreSheet().getCreatureByRealm(currentDiceSelection)));
                source.setDisable(true);
                //remember to update the grids given that rewards (bonuses) may start from the make move method
                updatePhoenix(currentDiceSelection,playSceneController,allGrids,currentPlayer);
                playSceneController.disableGrids();
                playSceneController.resetDiceSelection();
                playSceneController.nextTurn();
            }
            else
            {
                //handle error
                System.out.println("value of dice smaller than needed value");
            }
        }
    }
    public static void updatePhoenix(Dice currentDiceSelection, PlaySceneController playSceneController,GridPane[][] allGrids,Player currentPlayer)
    {
        int i = (currentPlayer == playSceneController.getGameBoard().getPlayer1())? 0 : 1;
        GridPane currentGrid = allGrids[i][RealmColor.MAGENTA.ordinal()];
        int[] phoenixAttackValues = ((Phoenix)currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)).getPhoenixAttackValues();
        RewardType[] rewards = ((Phoenix)currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)).getRewards();
        ObservableList<Node> children = currentGrid.getChildren();
        for(Node child : children) {
            if (child != null) {
                Integer rowIndex = GridPane.getRowIndex(child);
                Integer colIndex = GridPane.getColumnIndex(child);
                if (rowIndex != null && rowIndex == 3 && colIndex != null && child instanceof Label) {
                    ((Label)child).setText((rewards[colIndex-1]==RewardType.EMPTY)? "" :String.valueOf(rewards[colIndex-1]));
                }
                if (rowIndex != null && rowIndex == 1 && colIndex != null && child instanceof Button) {
                    ((Button)child).setText(String.valueOf(phoenixAttackValues[colIndex-1]));
                }

            }
        }
    }
}
