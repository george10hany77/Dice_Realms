package game.creatures;

import game.collectibles.RewardType;
import game.dice.Dice;
import game.dice.GreenDice;
import game.engine.Move;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import game.exceptions.InvalidPropertyKey;

//properties
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Gaia extends Creature {
    private int pointer = 0;
    private int[] progress = new int[] { 1, 2, 4, 7, 11, 16, 22, 29, 37, 46, 56 };
    private String[][] grid = {
            { "X", "2", "3", "4" },
            { "5", "6", "7", "8" },
            { "9", "10", "11", "12" }
    };
    private boolean[][] hit = new boolean[3][4];
    // hit[0][0]= true;
    private RewardType[] side = new RewardType[] { RewardType.YB, RewardType.RB, RewardType.EC} ;
    private RewardType[] bottom = new RewardType[] {RewardType.TW,RewardType.BB,RewardType.MB,RewardType.AB};

    public Gaia() {
        super();
        hit[0][0] = true;
        String[] keys = { "hit1Reward", "hit2Reward", "hit3Reward", "hit4Reward", "hit5Reward",
                "hit6Reward", "hit7Reward", "hit8Reward", "hit9Reward", "hit10Reward", "hit11Reward" };
        try (InputStream input = new FileInputStream(
                "src\\main\\resources\\config\\TerrasHeartlandRewards.properties")) {
            Properties props = new Properties();
            props.load(input);
            String[] coloumnn= new String[] {"column1Reward","column2Reward","column3Reward","column4Reward"};
            for(int i= 0;i<coloumnn.length;i++){
                try{
                    bottom[i]= RewardType.getReward(props.getProperty(coloumnn[i]));
                }catch(InvalidPropertyKey e){
                    System.out.println("InvalidPropertyKey");
                }
                
            }
            String[] sidenn = new String[] {"row1Reward","row2Reward","row3Reward"};
            for(int i =0; i<sidenn.length;i++){
                try{
                    side[i] = RewardType.getReward(props.getProperty(sidenn[i]));
                }catch(InvalidPropertyKey e){
                    System.out.println("Config key not valid, switching to default values");
                }
            }
        } catch (IOException ex) {
            side = new RewardType[] { RewardType.YB, RewardType.RB, RewardType.EC };
            bottom = new RewardType[] {RewardType.TW,RewardType.BB,RewardType.MB,RewardType.AB};
        }
        try (InputStream input = new FileInputStream(
                "src\\main\\resources\\config\\TerrasHeartlandScores.properties")) {
            Properties props = new Properties();
            props.load(input);
            for (int i = 0; i < progress.length; i++) {
                try{
                    progress[i] = Integer.parseInt(props.getProperty(keys[i]));
                }catch(NumberFormatException e){
                    System.out.println("Config property not valid, switching to default values");
                }
            }
        } catch (IOException ex) {
            progress = new int[] { 1, 2, 4, 7, 11, 16, 22, 29, 37, 46, 56 };
        }
        try (InputStream input = new FileInputStream(
                "src\\main\\resources\\config\\TerrasHeartlandValues.properties")) {
            Properties props = new Properties();
            props.load(input);
            int counter = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (j == 0 && i == 0) {
                        continue;
                    }
                    try{
                        if(Integer.parseInt(props.getProperty(keys[counter]))>12){
                            grid[i][j] = grid[i][j];
                        }
                        else{
                            grid[i][j] = props.getProperty(keys[counter]);
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Config property not valid, switching to default values");
                    }
                    counter++;
                }
            }
        } catch (IOException ex) {
            grid = new String[][] {
                    { "X", "2", "3", "4" },
                    { "5", "6", "7", "8" },
                    { "9", "10", "11", "12" }
            };
        }
        
        int cc = 0;
        for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j].equals("0")){
                        cc++;
                    }
                }}
        if (cc == 11 ){
            isAttackable = false;
        }

    }
    public int[] getProgress() {
        return progress;
    }
    public String[][] getGrid() {
        return grid;
    }
    private String X_space(RewardType inp) {
        if (inp == RewardType.X) {
            return "X ";
        }
        return inp.toString();

    }

    public String toString() {
        // int counter = 1;
        String ans = String.format(
                "Terra's Heartland: Gaia Guardians (GREEN REALM):\n" +
                        "+-----------------------------------+\n" +
                        "|  #  |1    |2    |3    |4    |R    |\n" +
                        "+-----------------------------------+\n" +
                        "|  1  |%s    |%s    |%s    |%s    |" + X_space(side[0]) + "   |\n" +
                        "|  2  |%s    |%s    |%s    |%s    |" + X_space(side[1]) + "   |\n" +
                        "|  3  |%s    |%s   |%s   |%s   |" + X_space(side[2]) + "   |\n" +
                        "+-----------------------------------+\n" +
                        "|  R  |" + X_space(bottom[0]) + "   |" + X_space(bottom[1]) + "   |" + X_space(bottom[2])
                        + "   |" + X_space(bottom[3])
                        + "   |     |\n" +
                        "+-----------------------------------------------------------------------+\n" +
                        "|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |\n" +
                        "+-----------------------------------------------------------------------+\n\n",
                grid[0][0], grid[0][1], grid[0][2], grid[0][3], grid[1][0], grid[1][1], grid[1][2], grid[1][3],
                grid[2][0], grid[2][1], grid[2][2], grid[2][3]);

        return ans;
    }
    public RewardType[] getSide(){
        return side;
    }

    public RewardType[] getBottom() {
        return bottom;
    }

    @Override
    public RewardType[] attack(Dice dice) throws InvalidMoveException, NullPointerException, DiceRollException {
        if (!isAttackable())
            throw new InvalidMoveException("All Gaia Guardians are dead !");
        if (dice == null)
            throw new NullPointerException("Die not initialized, please choose a valid die");
        if (dice.getValue() <= 0)
            throw new DiceRollException("Die value not initialized");
        if(this.getPossibleMovesForADie(dice).length==0){
            throw new InvalidMoveException("Gaia is already attacked");
        }
        RewardType re_side = null;
        RewardType re_bottom = null;
        RewardType[] ans = null;
        int attack = dice.getValue();
        for (int i = 0; i < hit.length; i++) {
            for (int j = 0; j < hit[0].length; j++) {
                if (!hit[i][j]) {
                    if (attack == Integer.parseInt(grid[i][j])) {
                        hit[i][j] = true;
                        if (Integer.parseInt(grid[i][j]) >= 10)
                            grid[i][j] = "X ";
                        else
                            grid[i][j] = "X";
                        if (rowEmpty(i)) {
                            re_side = side[i];
                            side[i] = RewardType.X;
                        }
                        if (coloumEmpty(j)) {
                            re_bottom = bottom[j];
                            bottom[j] = RewardType.X;
                        }
                    }
                }
            }
        }

        updateScore(progress[pointer]);
        if (re_bottom != null && re_side != null) {
            ans = new RewardType[2];
            ans[0] = re_side;
            ans[1] = re_bottom;
        } else if (re_bottom != null) {
            ans = new RewardType[1];
            ans[0] = re_bottom;
        } else if (re_side != null) {
            ans = new RewardType[1];
            ans[0] = re_side;
        }
        pointer++;
        if (pointer == 11) {
            isAttackable = false;
        }
        return ans;
    }

    public boolean rowEmpty(int value) {
        for (boolean cell : hit[value]) {
            if (!cell)
                return false;
        }
        return true;
    }

    public boolean coloumEmpty(int value) {
        for (int i = 0; i < hit.length; i++) {
            for (int j = 0; j < hit[0].length; j++) {
                if (j == value) {
                    if (!hit[i][j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    void updateScore(int value) {
        score = value;
    }

    @Override
    public Move[] getPossibleMovesForADie(Dice dice) {
        if (!isAttackable())
            return new Move[0];
        ArrayList<Move> moveArrayList = new ArrayList<>();
        Move[] ans;
        int attack = dice.getValue();
        if (attack == 1) {
            return new Move[0];
        } else {
            for (int i = 0; i < hit.length; i++) {
                for (int j = 0; j < hit[0].length; j++) {
                    if (!hit[i][j]) {
                        if (Integer.parseInt(grid[i][j]) == attack) {
                            moveArrayList.add(new Move(dice, this));
                        }
                    }
                }
            }
            ans = new Move[moveArrayList.size()];
            moveArrayList.toArray(ans);
            return ans;
        }
    }

    @Override
    public Move[] getPossibleMoves() {
        if (!isAttackable())
            return new Move[0];
        int counter = 1;
        int place = 0;
        Move[] ans = new Move[11 - pointer];
        for (int i = 0; i < hit.length; i++) {
            for (int j = 0; j < hit[0].length; j++) {
                // System.out.println("do you come here often but now with rizz");

                if (i == 0 && j == 0) {
                    continue;
                }

                if (!hit[i][j]) {
                    // System.out.println("do you come here often");
                    ans[place] = new Move(new GreenDice(Integer.parseInt(grid[i][j])), this);
                    place++;
                }
                counter++;
            }
        }
        return ans;
    }

    public static void printBooleanArray(boolean[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] ? "true" : "false");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
