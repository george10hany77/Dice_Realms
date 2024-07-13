package game.creatures;

import game.collectibles.RewardType;
import game.dice.Dice;
import game.dice.RedDice;
import game.engine.Move;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidDragonNumber;
import game.exceptions.InvalidMoveException;
import game.exceptions.InvalidPropertyKey;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Dragon extends Creature{

    private int[][] dragonGrid;
    private final int[][] dragonGridDefault = {
            {3,6,5,0},
            {2,1,0,5},
            {1,0,2,4},
            {0,3,4,6}
    };
    private int[] scores;
    private final int[] scoresDefault = {10,14,16,20};
    private RewardType[] rewards;
    private final RewardType[] rewardsDefault = {RewardType.GB, RewardType.YB, RewardType.BB, RewardType.EC, RewardType.AB};
    private boolean isDiagonalBonusAvailable = true;
    private boolean isHeadBonusAvailable = true;
    private boolean isWingBonusAvailable = true;
    private boolean isTailBonusAvailable = true;
    private boolean isHeartBonusAvailable = true;

    public Dragon(){
        dragonGrid = new int[4][4];
        setDragonGrid();
        scores = new int[4];
        setScores();
        rewards = new RewardType[5];
        setRewards();
        handleIfRedRealmIsAttackableAnyMore();
    }
    private void setRewards(){
        String[] keys = {"row1Reward", "row2Reward", "row3Reward", "row4Reward", "diagonalReward"};
        String valuesPath = "src//main//resources//config/EmberfallDominionRewards.properties";
        Properties properties = new Properties();
        FileInputStream keyValues = null;
        try {
            keyValues = new FileInputStream(valuesPath);
            properties.load(keyValues);
            for (int i = 0; i < rewards.length; i++) {
                try {
                    rewards[i] = RewardType.getReward(properties.getProperty(keys[i]));
                }catch (InvalidPropertyKey e){ // If the read reward is not valid
                    rewards[i] = rewardsDefault[i];
                    System.out.println(rewards[i] + " has been set !");
                }
            }
        }catch (IOException e) {
            System.out.println("File cannot be open EmberfallDominionRewards !\nSetting Rewards by default values...");
            rewards = rewardsDefault;
        }finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }else System.out.println("the file was not opened to be closed");
            } catch (IOException e) {
                System.err.println("Failed to close FIleInputStream");
            }

        }
    }
    private void setScores() {
        String[] keys = {"dragon1Score", "dragon2Score", "dragon3Score", "dragon4Score"};
        String valuesPath = "src//main//resources//config/EmberfallDominionScores.properties";
        Properties properties = new Properties();
        FileInputStream keyValues = null;
        try {
            keyValues = new FileInputStream(valuesPath);
            properties.load(keyValues);
            for (int i = 0; i < scores.length; i++) {
                try {
                    scores[i] = Integer.parseInt(properties.getProperty(keys[i]));
                }catch (NumberFormatException e){ // If the value read from the config file is a non number string.
                    scores[i] = scoresDefault[i];
                    System.out.println(scores[i] + " has been set !");
                }
            }

        } catch (IOException e) {
            System.out.println("File cannot be open EmberfallDominionValues !\nSetting Scores by default values...");
            scores = scoresDefault;
        } finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }else System.out.println("the file was not opened to be closed");
            } catch (IOException e) {
                System.err.println("Failed to close FIleInputStream");
            }

        }
    }
    private void setDragonGrid(){
        String[][] keys = {{"dragon1Heads", "dragon2Heads", "dragon3Heads", "dragon4Heads"},
                           {"dragon1Wings", "dragon2Wings", "dragon3Wings", "dragon4Wings"},
                           {"dragon1Tail" , "dragon2Tail" , "dragon3Tail" , "dragon4Tail"},
                           {"dragon1Heart", "dragon2Heart", "dragon3Heart", "dragon4Heart"}};

        String valuesPath = "src//main//resources//config/EmberfallDominionValues.properties";
        Properties properties = new Properties();
        FileInputStream keyValues = null;

        try {
            keyValues = new FileInputStream(valuesPath);
            properties.load(keyValues);
            for (int i = 0; i < dragonGrid.length; i++) {
                for (int j = 0; j < dragonGrid[i].length; j++) {
                    try {
                        dragonGrid[i][j] = Integer.parseInt(properties.getProperty(keys[i][j]));
                        if (dragonGrid[i][j] < 0 || dragonGrid[i][j] > 6){ // If The hit number was bigger than 6 or smaller than 1.
                            dragonGrid[i][j] = dragonGridDefault[i][j];
                            System.out.println(dragonGrid[i][j] + " has been set !");
                        }
                    }catch (NumberFormatException e){ // If the string from config file is not a valid number.
                        dragonGrid[i][j] = dragonGridDefault[i][j];
                        System.out.println(dragonGrid[i][j] + " has been set !");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("File cannot be open EmberfallDominionValues !\nSetting Dragon Grid by default values...");
            dragonGrid = dragonGridDefault;
        } finally {
            try {
                if (keyValues != null) {
                    keyValues.close();
                }else System.out.println("the file was not opened to be closed");
            } catch (IOException e) {
                System.out.println("Failed to close FileInputStream");
            }
        }

    }

    @Override
    public RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException {
        // Remember to check four dragons are dead at the end : DONE !
        if (!isAttackable())
            throw new InvalidMoveException("All Dragons are dead !");
        if (dice == null)
            throw new NullPointerException("Pass a valid dice !");
        if (dice.getRealm() != RealmColor.RED)
            throw new InvalidMoveException("The realm must be red !");
        if (dice.getValue() <= 0 || dice.getValue() > 6)
            throw new InvalidMoveException("Dice value is not valid !");

        ArrayList<RewardType> rewardTypeArrayList = new ArrayList<>();
        RewardType[] tempRewards;
        RedDice myDice = (RedDice) dice;
        if (myDice.getDragonNumber()==null)
//            throw new InvalidMoveException("Dragon number is not set !");
            throw new InvalidMoveException("This part of the dragon is already dead !");

        int currIndex = myDice.getDragonNumber().ordinal();
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[i][currIndex] == myDice.getValue()){
                // to declare that this part is dead.
                dragonGrid[i][currIndex] = 0;
                // after the attack I have to check whether the player got a bonus or not then terminate.
                switch (myDice.getDragonNumber()){
                    case Dragon1:
                        if (isDragon1Dead()){
                            updateScore(scores[0]);
                        }
                        break;
                    case Dragon2:
                        if (isDragon2Dead()){
                            updateScore(scores[1]);
                        }
                        break;
                    case Dragon3:
                        if (isDragon3Dead()){
                            updateScore(scores[2]);
                        }
                        break;
                    case Dragon4:
                        if (isDragon4Dead()){
                            updateScore(scores[3]);
                        }
                        break;
                }
                if (isHeadBonusAvailable && checkHeadBonus()){
                    isHeadBonusAvailable = false;
                    rewardTypeArrayList.add(rewards[0]);
                    rewards[0] = RewardType.X;
                }
                if (isWingBonusAvailable && checkWingBonus()){
                    isWingBonusAvailable = false;
                    rewardTypeArrayList.add(rewards[1]);
                    rewards[1] = RewardType.X;
                }
                if (isTailBonusAvailable && checkTailBonus()){
                    isTailBonusAvailable = false;
                    rewardTypeArrayList.add(rewards[2]);
                    rewards[2] = RewardType.X;
                }
                if (isHeartBonusAvailable && checkHeartBonus()){
                    isHeartBonusAvailable = false;
                    rewardTypeArrayList.add(rewards[3]);
                    rewards[3] = RewardType.X;
                }
                if (isDiagonalBonusAvailable && checkDiagonalBonus()){
                    isDiagonalBonusAvailable = false;
                    rewardTypeArrayList.add(rewards[4]);
                    rewards[4] = RewardType.X;
                }
                tempRewards = new RewardType[rewardTypeArrayList.size()];
                rewardTypeArrayList.toArray(tempRewards);
                // To check whether the realm is attackable anymore or not. It is not efficient but bruhhh.
                handleIfRedRealmIsAttackableAnyMore();
                return tempRewards;
            }
        }
        throw new InvalidMoveException("This part of the dragon is already dead !");
    }

    private void handleIfRedRealmIsAttackableAnyMore(){
        if (isDragon1Dead() && isDragon2Dead() && isDragon3Dead() && isDragon4Dead())
            isAttackable = false;
    }

    boolean isDragon1Dead(){
        for (int[] ints : dragonGrid) {
            if (ints[DragonNumber.Dragon1.ordinal()] > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isDragon2Dead(){
        for (int[] ints : dragonGrid) {
            if (ints[DragonNumber.Dragon2.ordinal()] > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isDragon3Dead(){
        for (int[] ints : dragonGrid) {
            if (ints[DragonNumber.Dragon3.ordinal()] > 0) {
                return false;
            }
        }
        return true;
    }

    boolean isDragon4Dead(){
        for (int[] ints : dragonGrid) {
            if (ints[DragonNumber.Dragon4.ordinal()] > 0) {
                return false;
            }
        }
        return true;
    }

    boolean checkHeadBonus(){
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[DragonNumber.Dragon1.ordinal()][i] > 0){
                return false;
            }
        }
        return true;
    }

    boolean checkWingBonus(){
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[DragonNumber.Dragon2.ordinal()][i] > 0){
                return false;
            }
        }
        return true;
    }

    boolean checkTailBonus(){
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[DragonNumber.Dragon3.ordinal()][i] > 0){
                return false;
            }
        }
        return true;
    }

    boolean checkHeartBonus(){
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[DragonNumber.Dragon4.ordinal()][i] > 0){
                return false;
            }
        }
        return true;
    }

    boolean checkDiagonalBonus(){
        for (int i = 0; i < dragonGrid.length; i++) {
            if (dragonGrid[i][i] > 0){
                return false;
            }
        }
        return true;
    }

    @Override
     void updateScore(int value) {this.score += value;}

    private String dts(int i, int j){ // to convert the integer value into its corresponding string
        // I don't have to check on the indices as I put them correctly :)
        if (dragonGrid[i][j] == 0)
            return RewardType.X+"";
        else return dragonGrid[i][j]+"";
    }

    private String sts(int i){ // to convert the integer value into its corresponding string
        // I don't have to check on the indices as I put them correctly :)
        return scores[i]+"";
    }

    private String rts(int i){ // to convert the integer value into its corresponding string
        // I don't have to check on the indices as I put them correctly :)
        if (rewards[i] == RewardType.X)
            return RewardType.X+" ";
        else return rewards[i]+"";
    }

    @Override
    public String toString() {
        return "Emberfall Dominion: Pyroclast Dragon (RED REALM):\n" +
                "+-----------------------------------+\n" +
                "|  #  |D1   |D2   |D3   |D4   |R    |\n" +
                "+-----------------------------------+\n" +
                "|  F  |"+dts(0,0)+"    |"+dts(0,1)+"    |"+dts(0,2)+"    |"+dts(0,3)+"    |"+rts(0)+"   |\n" +
                "|  W  |"+dts(1,0)+"    |"+dts(1,1)+"    |"+dts(1,2)+"    |"+dts(1,3)+"    |"+rts(1)+"   |\n" +
                "|  T  |"+dts(2,0)+"    |"+dts(2,1)+"    |"+dts(2,2)+"    |"+dts(2,3)+"    |"+rts(2)+"   |\n" +
                "|  H  |"+dts(3,0)+"    |"+dts(3,1)+"    |"+dts(3,2)+"    |"+dts(3,3)+"    |"+rts(3)+"   |\n" +
                "+-----------------------------------+\n" +
                "|  S  |"+sts(0)+"   |"+sts(1)+"   |"+sts(2)+"   |"+sts(3)+"   |"+rts(4)+"   |\n" +
                "+-----------------------------------+\n\n";
    }

    @Override
    public Move[] getPossibleMovesForADie(Dice dice) throws NullPointerException, DiceRollException, InvalidDragonNumber {
        if (!isAttackable())
            return new Move[0];
        if (dice == null)
            throw new NullPointerException("Dice was not instantiated !");
        if (!(dice instanceof RedDice))
            throw new ClassCastException("Pass Red Dice !");
        RedDice ourDice = (RedDice) dice;
        if(ourDice.getValue() <= 0 || ourDice.getValue() > 6)
            throw new DiceRollException("Dice value is invalid");
        ArrayList<Move> moveArrayList = new ArrayList<>();
        Move[] moves;
        // Alternate Fix for getpossiblemovesforadie
        // if (ourDice.getDragonNumber()!=null){
        //     for (int i = 0; i < dragonGrid.length; i++) {
        //        if  (dragonGrid[i][ourDice.getDragonNumber().ordinal()] == ourDice.getValue()) {
        //             moves = new Move[1];
        //             moves[0] = new Move(ourDice, this);
        //             return moves;
        //        }
        //     }
        //     return new Move[0];
        // }

        for (int i = 0; i < dragonGrid.length; i++) {
            for (int j = 0; j < dragonGrid[i].length; j++) {
                // just tweaked the indices of the iteration to check on a dragon per iteration
                int currNum = dragonGrid[j][i];
                if (currNum == ourDice.getValue()){
                    if (ourDice.getDragonNumber() == null) { // if dragon number is not initialized
                    moveArrayList.add(new Move(new RedDice(currNum, DragonNumber.getDragon(i+1)), this));
                    } else if (ourDice.getDragonNumber().ordinal() == (i)){ // important condition to check if we are in same dragon
                        moveArrayList.add(new Move(new RedDice(currNum, ourDice.getDragonNumber()), this));
                        break;
                    }
                }
            }
        }
        moves = new Move[moveArrayList.size()];
        moveArrayList.toArray(moves);
        // will return an empty array if there are no moves.
        return moves;
    }

    @Override
    public Move[] getPossibleMoves() {
        if (!isAttackable())
            return new Move[0];
        ArrayList<Move> moveArrayList = new ArrayList<>();  
        Move[] moves;
        for (int i = 0; i < dragonGrid.length; i++) {
            for (int j = 0; j < dragonGrid[i].length; j++) {
                // just tweaked the indices of the iteration to check on a dragon per iteration
                int currNum = dragonGrid[j][i];
                if (currNum != 0) {
                    moveArrayList.add(new Move(new RedDice(currNum, DragonNumber.getDragon(i + 1)), this));
                }
            }
        }
        moves = new Move[moveArrayList.size()];
        moveArrayList.toArray(moves);
        // will return an empty array if there are no moves.
        return moves;
    }

    public int[][] getDragonGrid() {
        return dragonGrid;
    }

    public RewardType[] getDragonReward() {
        return rewards;
    }

    public int[] getDragonScores() {
        return scores;
    }
}
