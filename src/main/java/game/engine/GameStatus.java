package game.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import game.collectibles.RewardType;
import game.exceptions.InvalidPropertyKey;

public class GameStatus {
    private GameState gameState;
    private int roundNumber;
    private int turnNumber;
    private int maxTurnNumberActive;
    private int maxTurnNumberPassive;
    private int maxRoundNumber;
    private final int defaultTurnNumberActive = 3;
    private final int defaultTurnNumberPassive = 1;
    private final int defaultMaxRoundNumber = 6;
    private RewardType[] rewards;
    private RewardType[] defaultRewards = {RewardType.TW,RewardType.AB,RewardType.TW,RewardType.EB,RewardType.EMPTY,RewardType.EMPTY};

    public  GameStatus()
    {
        gameState = GameState.IN_PROGRESS;
        roundNumber = 1;
        turnNumber = 1;
        String filePath = "src//main//resources//config/RoundsRewards.properties";
        Properties pros;
        pros = new Properties();
        FileInputStream keyValues;
        keyValues = null;
        rewards = new RewardType[6];
        String[] keys = { "round1Reward", "round2Reward", "round3Reward", "round4Reward", "round5Reward", "round6Reward"};
        try {
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            int i = 0;
            while(i < keys.length){
            try {
                    rewards[i] = RewardType.getReward(pros.getProperty(keys[i]));
            } catch(InvalidPropertyKey e){
                rewards[i] = defaultRewards[i];
            }
            i++;
        }

        } catch (IOException e) {
             rewards = defaultRewards;
        }finally {
            try {
                keyValues.close();
            } catch (IOException e) {
            }
    }
    filePath = "src//main//resources//config/Round&Turn.properties";
    pros = new Properties();
    keyValues = null;
    try {
        keyValues = new FileInputStream(filePath);
        pros.load(keyValues);
        maxTurnNumberActive = Integer.parseInt(pros.getProperty("MaxTurnACTIVE"));
        maxTurnNumberPassive = Integer.parseInt(pros.getProperty("MaxTurnPASSIVE"));
        maxRoundNumber = Integer.parseInt(pros.getProperty("MaxRoundNumber"));
    } catch (IOException e) {

        maxRoundNumber = defaultMaxRoundNumber;
        maxTurnNumberActive = defaultTurnNumberActive;
        maxTurnNumberPassive = defaultTurnNumberPassive;
    }  finally {
        try {
            keyValues.close();
        } catch (IOException e) {
        }
    }
    }
    public boolean updateTurn() {
        turnNumber++;
        return true;
    }
    public void resetTurn()
    {
        turnNumber = 1;
    }

    public boolean updateRound() {
        if (roundNumber + 1 > maxRoundNumber)
            return false;
        // throw exception
        else
            roundNumber++;
        return true;
    }

    public int getroundNumber() {
        return roundNumber;
    }

    public int getturnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }
    public int getMaxRoundNumber() {
        return maxRoundNumber;
    }
    public int getMaxTurnNumberActive() {
        return maxTurnNumberActive;
    }
    public int getMaxTurnNumberPassive() {
        return maxTurnNumberPassive;
    }
    public RewardType[] getRewards() {
        return rewards;
    }
    public int getRoundNumber() {
        return roundNumber;
    }
    public GameState getGameState() {
        return gameState;
    }
    public int getTurnNumber() {
        return turnNumber;
    }
    public RewardType getRoundReward()
    {
        return rewards[roundNumber-1];
    }
}
