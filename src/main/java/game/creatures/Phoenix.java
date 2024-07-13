package game.creatures;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import game.collectibles.RewardType;
import game.dice.Dice;
import game.dice.MagentaDice;
import game.engine.Move;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import game.exceptions.InvalidPropertyKey;

public class Phoenix extends Creature {
    private int currentIndex;
    private int[] phoenixAttackValues;
    private int prevAttackValue; // prev_dice_val%6 to get the condition for each hit
    private RewardType[] rewards;
    private final RewardType[] defaultRewards = { RewardType.EMPTY, RewardType.EMPTY, RewardType.TW, RewardType.GB,
            RewardType.AB, RewardType.RB, RewardType.EC, RewardType.TW, RewardType.BB, RewardType.YB, RewardType.AB };

    public Phoenix() throws InvalidPropertyKey {
        currentIndex = 0;
        prevAttackValue = 0;
        phoenixAttackValues = new int[11];
        rewards = new RewardType[11];
        String filePath = "src//main//resources//config/MysticalSkyRewards.properties";
        Properties pros;
        pros = new Properties();
        FileInputStream keyValues;
        keyValues = null;
        String[] keys = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward", "hit6Reward",
                "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
        try {
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while (i < keys.length) {
                try {
                    rewards[i] = RewardType.getReward(pros.getProperty(keys[i]));

                } catch (InvalidPropertyKey e) {
                    rewards[i] = defaultRewards[i];
                }
                i++;
            }

        } catch (IOException e) {
            rewards = defaultRewards;
        } finally {
            try {
                if (keyValues != null) {
                    keyValues.close();}
             } catch (IOException e) {

            }
        }
    }

    public RewardType[] getRewards() {
        return rewards;
    }

    @Override
    public RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException {
        // if (!isAttackable()) {
        // throw new InvalidMoveException(
        // "The Phoenix has finished all its lives and ElSayed ElBadawy is not there to
        // save it once more...");
        // }
        if (!isAttackable())
            throw new InvalidMoveException("The Phoenix is dead !");
        else if (dice == null)
            throw new NullPointerException("Die not initialized, please choose a valid die");

        else if (dice.getValue() <= 0)
            throw new DiceRollException("Die value not initialized");

        else if (dice.getValue() <= prevAttackValue)
            throw new InvalidMoveException(
                    "Magenta Die value is too low to attack The Phoenix.\nMagenta Die value should be higher than "+prevAttackValue);

        else {
            updateScore(dice.getValue());
            prevAttackValue = dice.getValue() % 6;
            phoenixAttackValues[currentIndex] = (prevAttackValue == 0) ? 6 : prevAttackValue;
            isAttackable = (currentIndex < 10);
            RewardType[] temp = new RewardType[1];
            temp[0] = rewards[currentIndex++];
            if (temp[0] != RewardType.EMPTY)
                rewards[currentIndex - 1] = RewardType.X;
            return temp;
        }
    }

    public int[] getPhoenixAttackValues() {
        return phoenixAttackValues;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getPrevAttackValue() {
        return prevAttackValue;
    }

    @Override
    void updateScore(int value) {
        score += value;
    }

    @Override
    public String toString() {

        String[] dV = new String[11];
        for (int i = 0; i < dV.length; i++) {
            dV[i] = phoenixAttackValues[i] + "  ";
        }

        String[] rS = new String[11];
        for (int i = 0; i < rS.length; i++) {
            if (rewards[i] == RewardType.EMPTY)
                rS[i] = "   ";
            else if (rewards[i] == RewardType.X)
                rS[i] = "X  ";
            else
                rS[i] = rewards[i] + " ";
        }

        String out = "Mystical Sky: Majestic Phoenix (MAGENTA REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + dV[0] + "  |" + dV[1] + "  |" + dV[2] + "  |" + dV[3] + "  |" + dV[4] + "  |"
                + dV[5] + "  |" + dV[6] + "  |" + dV[7] + "  |" + dV[8] + "  |" + dV[9] + "  |" + dV[10]
                + "  |\n" +
                "|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |\n" +
                "|  R  |" + rS[0] + "  |" + rS[1] + "  |" + rS[2] + "  |" + rS[3] + "  |" + rS[4] + "  |"
                + rS[5] + "  |" + rS[6] + "  |" + rS[7] + "  |" + rS[8] + "  |" + rS[9] + "  |" + rS[10]
                + "  |\n" +
                "+-----------------------------------------------------------------------+\n\n";

        return out;
    }

    @Override
    public Move[] getPossibleMovesForADie(Dice dice) throws NullPointerException, DiceRollException {
        if (!isAttackable())
            return new Move[0];
        ArrayList<Move> moveArrayList = new ArrayList<>();
        Move[] moves;
        if (dice == null)
            throw new NullPointerException("Dice was not instantiated");
        if (dice.getValue() <= 0)
            throw new DiceRollException("Dice value was not initialized");

        if (dice.getValue() > prevAttackValue) {
            Move move = new Move(dice, this);
            moveArrayList.add(move);
        }
        moves = new Move[moveArrayList.size()];
        moveArrayList.toArray(moves);
        return moves;
    }

    @Override
    public Move[] getPossibleMoves() {
        if (!isAttackable())
            return new Move[0];
        int diceRange = 6 - prevAttackValue;
        Move[] out = new Move[diceRange];
        for (int i = 0; i < diceRange; i++) {
            out[i] = new Move(new MagentaDice(prevAttackValue + i + 1), this);
        }
        return out;
    }
    
}
