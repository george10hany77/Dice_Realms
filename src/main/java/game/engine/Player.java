package game.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import game.collectibles.ArcaneBoost;
import game.collectibles.PowerUpState;
import game.collectibles.TimeWarp;
import game.exceptions.InvalidPropertyKey;

public  class Player {
    private String name;
    private PlayerStatus state;
    private TimeWarp[] timeWarps;
    private ArcaneBoost[] arcaneBoosts;
    private ScoreSheet scoreSheet;
    private GameScore gameScore;
    private int ArcaneBoostMaxNumber;
    private int TimeWarpMaxNumber;
    private int arcaneBoostsCount;
    private int timeWarpsCount;

     public Player(PlayerStatus state, String name){
        this.name = name;
        this.state = state;
        gameScore = new GameScore();
        arcaneBoostsCount = 0;
        timeWarpsCount = 0;
        String filePath = "src//main//resources//config/PowerUpsMaxSize.properties";
        Properties pros;
        pros = new Properties();
        FileInputStream keyValues;
        keyValues = null;
        try {
            keyValues = new FileInputStream(filePath);
            pros.load(keyValues);
            ArcaneBoostMaxNumber = Integer.parseInt(pros.getProperty("ArcaneBoostMaxNumber"));
            TimeWarpMaxNumber = Integer.parseInt(pros.getProperty("TimeWarpMaxNumber"));
        } catch (FileNotFoundException e) {

            System.out.println("Configuration file for 'PowerUpsMaxSize' was not found, Switching to default values");
            ArcaneBoostMaxNumber=7;
            TimeWarpMaxNumber=7;
        } catch (IOException e) {
            System.out.println("Failed to read configuration file for PowerUpsMaxSize, Switching to default values");
            ArcaneBoostMaxNumber=7;
            TimeWarpMaxNumber=7;
        } finally {
            try {
                keyValues.close();
            } catch (IOException e) {
                System.err.println("Failed to close FileInputStream");
            }
        }

        try {
            scoreSheet = new ScoreSheet();
        } catch (InvalidPropertyKey e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        timeWarps = new TimeWarp[TimeWarpMaxNumber];
        for (int i = 0; i < TimeWarpMaxNumber; i++) {
            timeWarps[i] = new TimeWarp();
        }
        arcaneBoosts = new ArcaneBoost[ArcaneBoostMaxNumber];
        for (int i = 0; i < ArcaneBoostMaxNumber; i++) {
            arcaneBoosts[i] = new ArcaneBoost();
        }
    }

    public boolean switchState() { // the method is changed to return boolean :)
        if (state == PlayerStatus.PASSIVE) {
            state = PlayerStatus.ACTIVE;
        } else {
            state = PlayerStatus.PASSIVE;
        }
        return true;
    }

    public  boolean addTimeWarp() {
        for (int i = 0; i < timeWarps.length; i++) {
            if (timeWarps[i].getPowerUpState() == PowerUpState.DISABLED) {
                timeWarps[i].Enable();
                timeWarpsCount++;
                return true;
            }
        }
        return false;
    }

    public boolean addArcaneBoost() {
        for (int i = 0; i < arcaneBoosts.length; i++) {
            if (arcaneBoosts[i].getPowerUpState() == PowerUpState.DISABLED) {
                arcaneBoosts[i].Enable();
                arcaneBoostsCount++;
                return true;
            }
        }
        return false;
    }

    public  boolean useArcaneBoost() {
        for (int i = 0; i < arcaneBoosts.length; i++) {
            if (arcaneBoosts[i].getPowerUpState() == PowerUpState.ENABLED) {
                arcaneBoosts[i].use();
                arcaneBoostsCount--;
                return true;
            }
        }
        return false;
    }

    public boolean useTimeWarp() {
        for (int i = 0; i < timeWarps.length; i++) {
            if (timeWarps[i].getPowerUpState() == PowerUpState.ENABLED) {
                timeWarps[i].use();
                timeWarpsCount--;
                return true;
            }
        }
        return false;
    }

    public TimeWarp[] getTimeWarp() {
        return timeWarps;
    }

    public ArcaneBoost[] getArcaneBoost() {
        return arcaneBoosts;
    }

    public  PlayerStatus getPlayerStatus() {
        return state;
    }

    public ScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public GameScore getGameScore() {
        return gameScore;
    }
    public boolean hasArcaneBoost()
    {
        return (arcaneBoostsCount>0);
    }
    public boolean hasTimeWarps()
    {
        return (timeWarpsCount>0);
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTimeWarpsCount()
    {
        return timeWarpsCount;
    }
    public int getArcaneBoostCount()
    {
        return arcaneBoostsCount;
    }

}
