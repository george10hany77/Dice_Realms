package game.gui;

import game.collectibles.RewardType;
import game.creatures.Hydra;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.GameBoard;
import game.engine.Move;
import game.engine.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HydraGUI {
    public static void attackHydra(ActionEvent event,Dice currentDiceSelection, PlaySceneController playSceneController,Player currentPlayer,GridPane[][] allGrids)
    {
        Button source = (Button) event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);//used to access the hydraattackvalue
        //Dummy value to be changed after adding the dice instances
        //must be the button in order...........-------------------------> HANDLE IT
        if(currentDiceSelection instanceof ArcanePrism)
        {
            currentDiceSelection = new BlueDice(currentDiceSelection.getValue());
        }
        if(colIndex-1 != ((Hydra)currentPlayer.getScoreSheet().getCreatureByRealm(currentDiceSelection)).getCurrentIndex())
        {
            System.out.println("Previous heads weren't attacked!!!");
            return;
        }
        if(currentDiceSelection instanceof BlueDice) {
            if(((Hydra)currentPlayer.getScoreSheet().getCreatureByRealm(currentDiceSelection)).getHydraAttackValues()[colIndex-1]<=currentDiceSelection.getValue())
            {
                playSceneController.makeMove(currentPlayer, new Move(currentDiceSelection, currentPlayer.getScoreSheet().getCreatureByRealm(currentDiceSelection)));
                source.setDisable(true);
                //remember to update the grids given that rewards (bonuses) may start from the make move method
                updateHydra(currentDiceSelection,playSceneController,allGrids,currentPlayer);
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
        else
        {
            // handle error
            System.out.println("dice selected not blue dice , actual dice:"+currentDiceSelection);
        }
    }
    public static void updateHydra(Dice currentDiceSelection, PlaySceneController playSceneController,GridPane[][] allGrids,Player currentPlayer)
    {
        GridPane gridPane = allGrids[(currentPlayer == playSceneController.getGameBoard().getPlayer1())? 0 : 1][RealmColor.BLUE.ordinal()];
        int[] hydraScores = ((Hydra)currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)).getHeadScores();
        RewardType[] rewards = ((Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)).getRewards();
        int[] hydraAttackValues = ((Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)).getHydraAttackValues();
        String[] hydraHeads = ((Hydra) currentPlayer.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)).getHydraHeads();
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (node != null) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                // Check for null values and ensure the node is a Label
                if (rowIndex != null && rowIndex == 4 && colIndex != null && node instanceof Label) {
                    ((Label)node).setText(String.valueOf(hydraScores[colIndex - 1]));
                }
                if (rowIndex != null && rowIndex == 3 && colIndex != null && node instanceof Label) {
                    ((Label)node).setText((rewards[colIndex-1]==RewardType.EMPTY)? "" :String.valueOf(rewards[colIndex-1]));
                }
                if (rowIndex != null && rowIndex == 2 && colIndex != null && node instanceof Label) {
                    ((Label)node).setText("≥"+hydraAttackValues[colIndex-1]);
                }
                if (rowIndex != null && rowIndex == 1 && colIndex != null && node instanceof Button) {
                    ((Button)node).setText(hydraHeads[colIndex-1]);
                }

            }
        }
    }
}
