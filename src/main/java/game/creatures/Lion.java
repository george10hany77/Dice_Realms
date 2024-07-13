package game.creatures;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import game.collectibles.RewardType;
import game.dice.Dice;
import game.dice.YellowDice;
import game.engine.Move;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import game.exceptions.InvalidPropertyKey;

public class Lion extends Creature {
    private int currentIndex;
    private int[] lionAttackValues;
    private int[] lionAttackMultiplier;
    private RewardType[] rewards;
    private final RewardType[] defaultRewards = { RewardType.EMPTY, RewardType.EMPTY, RewardType.TW, RewardType.EMPTY,
            RewardType.RB, RewardType.AB, RewardType.EMPTY, RewardType.EC, RewardType.EMPTY, RewardType.MB, RewardType.EMPTY };
    private final int[] defaultMultiplier = { 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 3 };

    public Lion() throws InvalidPropertyKey {
        currentIndex = 0;
        lionAttackValues = new int[11];
        lionAttackMultiplier = new int[11];
        rewards = new RewardType[11];
        String filePath = "src\\main\\resources\\config\\RadiantSvannaRewards.properties";
        Properties pros;
        pros = new Properties();
        FileInputStream keyValues;
        keyValues = null;
        String[] hitRewardStrings = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward",
                "hit6Reward",
                "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
        try {
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while (i < hitRewardStrings.length) {
                try {
                    rewards[i] = RewardType.getReward(pros.getProperty(hitRewardStrings[i]));
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
                    keyValues.close();
                }else System.out.println("the file was not opened to be closed");
            } catch (IOException e) {
                System.out.println("Can't close the reader");
            }
        }
        try {
            filePath = "src\\main\\resources\\config\\RadiantSvannaMultiplier.properties";
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            for (int i = 0; i < hitRewardStrings.length; i++) {
                try {
                    lionAttackMultiplier[i] = Integer.parseInt(pros.getProperty(hitRewardStrings[i]));
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Configuration file for 'RadiantSvannaMultiplier' contains invalid characters (not int)");
                            lionAttackMultiplier[i] = defaultMultiplier[i];
                }
            }
        } catch (IOException e) {
            System.out.println("Configuration file for 'RadiantSvannaMultiplier' was not found");
            lionAttackMultiplier = defaultMultiplier;
        } 
         finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }else System.out.println("the file was not opened to be closed");
            } catch (IOException e) {
                System.err.println("Failed to close FileInputStream");
            }
        }
    }

    @Override
    public RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException {
       if (!isAttackable()) {
           throw new InvalidMoveException(
                  "The Radiant Savanna has been conquered and the Lions roar no more");
       }

        else if (dice == null)
            throw new NullPointerException("Die not initialized, please choose a valid die");

        else if (dice.getValue() <= 0)
            throw new DiceRollException("Die value not initialized");

        else {
            updateScore(dice.getValue() * lionAttackMultiplier[currentIndex]);
            lionAttackValues[currentIndex] = dice.getValue() * lionAttackMultiplier[currentIndex];
            isAttackable = (currentIndex < 10);
            RewardType[] temp = { rewards[currentIndex] };
            if (rewards[currentIndex] != RewardType.EMPTY)
                rewards[currentIndex++] = RewardType.X;
            else
                currentIndex++;
            return temp;
        }
    }

    @Override
    void updateScore(int value) {
        score += value;
    }

    @Override
    public String toString() {
        String[] AM = new String[11];
        for (int i = 0; i < AM.length; i++) {
            if (lionAttackMultiplier[i] == 1) {
                AM[i] = "   ";
            } else {
                AM[i] = "x" + lionAttackMultiplier[i] + " ";
            }
        }

        String[] dV = new String[11];
        for (int i = 0; i < dV.length; i++) {
            dV[i] = lionAttackValues[i]+ ((lionAttackValues[i]<10) ? "  ":" ");
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

        String out = "Radiant Savanna: Solar Lion (YELLOW REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + dV[0] + "  |" + dV[1] + "  |" + dV[2] + "  |" + dV[3] + "  |" + dV[4] + "  |"
                + dV[5] + "  |" + dV[6] + "  |" + dV[7] + "  |" + dV[8] + "  |" + dV[9] + "  |" + dV[10]
                + "  |\n" +
                "|  M  |" + AM[0] + "  |" + AM[1] + "  |" + AM[2] + "  |" + AM[3] + "  |" + AM[4] + "  |"
                + AM[5] + "  |" + AM[6] + "  |" + AM[7] + "  |" + AM[8] + "  |" + AM[9] + "  |" + AM[10]
                + "  |\n" +
                "|  R  |" + rS[0] + "  |" + rS[1] + "  |" + rS[2] + "  |" + rS[3] + "  |" + rS[4] + "  |"
                + rS[5] + "  |" + rS[6] + "  |" + rS[7] + "  |" + rS[8] + "  |" + rS[9] + "  |" + rS[10]
                + "  |\n" +
                "+-----------------------------------------------------------------------+\n\n";

        return out;
    }

    @Override
    public Move[] getPossibleMovesForADie(Dice dice) throws DiceRollException {
        if (isAttackable()) {
            Move[] moves = new Move[1];
            if (dice == null)
                throw new NullPointerException("Dice was not instantiated");
            if (dice.getValue() <= 0)
                throw new DiceRollException("Dice value was not initialized");
            Move move = new Move(dice, this);
            moves[0] = move;

            return moves;
        }
        return new Move[0];
    }

    @Override
    public Move[] getPossibleMoves() {
        if (isAttackable()) {
            int diceRange = 6;
            Move[] out = new Move[diceRange];
            for (int i = 0; i < diceRange; i++) {
                out[i] = new Move(new YellowDice(i + 1), this);
            }
            return out;
        }
        return new Move[0];
    }
    
    public int[] getLionAttackValues(){
        return lionAttackValues;
    }
    public int[] getLionAttackMultiplier(){
        return lionAttackMultiplier;
    }
    public RewardType[] getRewardValues(){
        return rewards;
    }
}
