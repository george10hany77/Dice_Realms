package game.engine;

import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.DiceLocation;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;

public class GameBoard {
    private Player Player1;
    private Player Player2;
    private Dice[] dices;
    private GameStatus status;
    private boolean isPVP = true;
    public GameBoard(String name1,String name2) {
        Player1 = new Player(PlayerStatus.ACTIVE,name1);
        Player2 = new Player(PlayerStatus.PASSIVE,name2);
        RedDice RD = new RedDice();
        BlueDice BD = new BlueDice();
        MagentaDice MD = new MagentaDice();
        YellowDice YD = new YellowDice();
        GreenDice GD = new GreenDice();
        ArcanePrism AP = new ArcanePrism();
        dices = new Dice[6];
        dices[RealmColor.RED.ordinal()] = RD;
        dices[RealmColor.BLUE.ordinal()] = BD;
        dices[RealmColor.MAGENTA.ordinal()] = MD;
        dices[RealmColor.YELLOW.ordinal()] = YD;
        dices[RealmColor.GREEN.ordinal()] = GD;
        dices[RealmColor.WHITE.ordinal()] = AP;
        status = new GameStatus();
    }
     public Player getPlayer1() {
        return Player1;
    }
    public Player getPlayer2() {
        return Player2;
    }
    public Dice[] getDice() {
        return dices;
    }
    public Dice getDice(int value) {
        return dices[value];
    }
    public GameStatus getStatus() {
        return status;
    }
   public  void resetUseByBoost()
    {
        for (Dice dice : dices) {
            dice.resetUseByBoost();
        }
    }
    public void resetAllDices() {
        for (Dice dice : dices) {
            dice.resetUseByBoost();
            dice.updateDiceLocation(DiceLocation.ACTIVE);
            dice.setValue(0);
        }
    }


    public boolean isPVP() {
        return isPVP;
    }

    public void setAIPlayer() {
        isPVP=false;
        Player2 = new AI_Player();
    }
}
