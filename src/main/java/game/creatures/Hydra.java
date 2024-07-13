package game.creatures;

import game.collectibles.RewardType;
import game.dice.BlueDice;
import game.dice.Dice;
import game.engine.Move;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import game.exceptions.InvalidPropertyKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Hydra extends Creature {
    private int currentIndex;
    private int[] hydraAttackValues;
    private String[] hydraHeads;
    private RewardType[] rewards;
    private int[] headScores;
    private final int[] defaultAttackValues = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6 };
    private final RewardType[] defaultRewards = { RewardType.EMPTY, RewardType.EMPTY, RewardType.EMPTY, RewardType.AB,
            RewardType.EMPTY, RewardType.GB, RewardType.EC, RewardType.EMPTY, RewardType.MB };
    private final int[] defaultHeadScores = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66};

    public Hydra() {
        currentIndex = 0;
        hydraAttackValues = new int[11];
        headScores = new int[11];
        rewards = new RewardType[11];
        hydraHeads = new String[11];
        Arrays.fill(hydraHeads, "---");
        String filePath = "src//main//resources//config/TideAbyssRewards.properties";
        Properties pros;
        pros = new Properties();
        FileInputStream keyValues;
        keyValues = null;
        String[] hitReward = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward", "hit6Reward",
                "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
        try {
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while (i < hitReward.length) {
                try {
                    rewards[i] = RewardType.getReward(pros.getProperty(hitReward[i]));

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
                }
            } catch (IOException e) {
            }
        }
        String[] attValues = { "hit1AttackValues", "hit2AttackValues", "hit3AttackValues", "hit4AttackValues",
                "hit5AttackValues", "hit6AttackValues", "hit7AttackValues", "hit8AttackValues", "hit9AttackValues",
                "hit10AttackValues", "hit11AttackValues" };

        try {
            filePath = "src//main//resources//config/TideAbyssAttackValues.properties";
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while (i < attValues.length) {
                try {
                    hydraAttackValues[i] = Integer.parseInt(pros.getProperty(attValues[i]));
                    if(hydraAttackValues[i] > 6 || hydraAttackValues[i] < 1)
                    {
                        hydraAttackValues[i] = defaultAttackValues[i];
                    }
                } catch (NumberFormatException e) {
                    hydraAttackValues[i] = defaultAttackValues[i];
                }
                i++;
            }

        } catch (IOException e) {
            hydraAttackValues = defaultAttackValues;
        } finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }
            } catch (IOException e) {
            }
        }
        String[] scores = { "hit1Score", "hit2Score", "hit3Score", "hit4Score",
                "hit5Score", "hit6Score", "hit7Score", "hit8Score", "hit9Score",
                "hit10Score", "hit11Score" };
        try {
            filePath = "src//main//resources//config/TideAbyssScores.properties";
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while (i < scores.length) {
                try {
                    headScores[i] = Integer.parseInt(pros.getProperty(scores[i]));
                } catch (NumberFormatException e) {
                    headScores[i] = defaultHeadScores[i];
                }
                i++;
            }

        } catch (IOException e) {
                    headScores = defaultHeadScores;
        } finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }
            } catch (IOException e) {
            }
        }

    }

    @Override
    public RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException {
        if (!isAttackable())
            throw new InvalidMoveException("All Hydras are dead !");
        if (dice == null)
            throw new NullPointerException("Die not initialized, please choose a valid die");
        if (dice.getValue() <= 0)
            throw new DiceRollException("Die value not initialized");
        if (!(dice.getValue() >= hydraAttackValues[currentIndex])) {
            throw new InvalidMoveException("Value of dice less than needed value : " + hydraAttackValues[currentIndex]);
        } else {
            hydraHeads[currentIndex] = dice.getValue() + "  ";
            updateScore(headScores[currentIndex]);
            if (currentIndex + 1 >= 11)
                isAttackable = false;
            RewardType[] temp = new RewardType[1];
            temp[0] = rewards[currentIndex];
            if (rewards[currentIndex] != null)
                rewards[currentIndex++] = RewardType.X;
            else
                currentIndex++;
            return temp;
        }

    }

    @Override
     void updateScore(int value) {
        score = value;
    }

    @Override
    public String toString() {
        String[] temp = new String[11];
        for (int i = 0; i < temp.length; i++) {
            if (rewards[i] == RewardType.EMPTY)
                temp[i] = "   ";
            else if (rewards[i] == RewardType.X)
                temp[i] = "X  ";
            else
                temp[i] = rewards[i] + " ";
        }

        return "Tide Abyss: Hydra Serpents (BLUE REALM):\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  H  |" + hydraHeads[0] + "  |" + hydraHeads[1] + "  |" + hydraHeads[2] + "  |" + hydraHeads[3]
                + "  |" + hydraHeads[4] + "  |" + hydraHeads[5] + "  |" + hydraHeads[6] + "  |" + hydraHeads[7] + "  |"
                + hydraHeads[8] + "  |" + hydraHeads[9] + "  |" + hydraHeads[10] + "  |\n" +
                "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |\n" +
                "|  R  |" + temp[0] + "  |" + temp[1] + "  |" + temp[2] + "  |" + temp[3] + "  |" + temp[4] + "  |"
                + temp[5] + "  |" + temp[6] + "  |" + temp[7] + "  |" + temp[8] + "  |" + temp[9] + "  |" + temp[10]
                + "  |\n" +
                "+-----------------------------------------------------------------------+\n" +
                "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |\n" +
                "+-----------------------------------------------------------------------+\n\n";
    }

    @Override
    public Move[] getPossibleMovesForADie(Dice dice) throws NullPointerException, DiceRollException {
        if (!isAttackable())
            return new Move[0];
        if (dice == null)
            throw new NullPointerException("Dice was not instantiated");
        ArrayList<Move> moveArrayList = new ArrayList<>();
        Move[] out;
        if (dice == null || dice.getValue() <= 0)
            throw new DiceRollException("Dice value was not initialized");
        if (hydraAttackValues[currentIndex] <= dice.getValue())
            moveArrayList.add(new Move(dice, this));
        out = new Move[moveArrayList.size()];
        moveArrayList.toArray(out);
        return out;
    }

    @Override
    public Move[] getPossibleMoves() {
        if (!isAttackable())
            return new Move[0];
        int currentMinAttackVal = hydraAttackValues[currentIndex];
        int looper = 7 - currentMinAttackVal;
        Move[] out = new Move[looper];
        for (int i = 0; i < looper; i++) {
            out[i] = new Move(new BlueDice(currentMinAttackVal + i), this);
        }
        return out;

    }

    public int[] getHeadScores() {
        return headScores;
    }

    public RewardType[] getRewards() {
        return rewards;
    }

    public int[] getHydraAttackValues() {
        return hydraAttackValues;
    }

    public String[] getHydraHeads() {
        return hydraHeads;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
