package game.engine;

import game.creatures.RealmColor;

public class GameScore {
    private int[] realmScores;
    private int elementalCount;
    private static final String reset = "\u001B[0m";
    public  GameScore()
    {
        realmScores = new int[5];
        elementalCount=0;
    }
    public int[] getRealmsScores()
    {
        return realmScores;
    }
    public int getLowestScore()
    {
        int out = realmScores[0];
        for (int i = 0; i < realmScores.length; i++) {
            if(realmScores[i] < out)
                out = realmScores[i];
        }
        return out;
    }
    // we still didn't show this
    public  int calcTotalScore()
    {
        int out = 0;
        for (int i : realmScores) {
            out += i;
        }
        int LowestScore = this.getLowestScore();
        int ElementalCrests = this.getElementalCount();
        out += LowestScore*ElementalCrests;
        return out;
    }
    public  boolean updateRealmScore(RealmColor color, int value)
    {
        realmScores[color.ordinal()] = value;
        return true;
    }
    public int getMagentaRealmScore()
    {
        return realmScores[RealmColor.MAGENTA.ordinal()];
    }
    public int getYellowRealmScore()
    {
        return realmScores[RealmColor.YELLOW.ordinal()];
    }
    public int getBlueRealmScore()
    {
        return realmScores[RealmColor.BLUE.ordinal()];
    }
    public int getRedRealmScore()
    {
        return realmScores[RealmColor.RED.ordinal()];
    }
    public int getGreenRealmScore()
    {
        return realmScores[RealmColor.GREEN.ordinal()];
    }
    public  boolean addElementalCrest() {
        if(elementalCount + 1 > 5)
        {   System.out.println("Can't add an elemental crest to player, Already reached max number of crests");
            return false;
        }
        else
            elementalCount++;
        return true;
    }

    public int getElementalCount() {
        return elementalCount;
    }
    public  int getTotalScore()
    {
        return calcTotalScore();
    }
    public void displayGameScore()
    {
        System.out.printf("+--------------------------+%n");
        System.out.printf("| %-16s | %-5s |%n","Realm","Score");
        System.out.printf("+--------------------------+%n");
        RealmColor[] realmColors = RealmColor.values();
        for(int i = 0; i < realmScores.length ;i++)
        {
            System.out.printf("| %-25s | %-5s |%n",(realmColors[i].getTextColor()+realmColors[i]+reset),realmScores[i]);
        }
        System.out.printf("+--------------------------+%n");
        System.out.printf("| %-16s | %-5s |%n","Elemental Crests",elementalCount);
        System.out.printf("+--------------------------+%n");
        System.out.printf("+--------------------------+%n");
        System.out.printf("| %-16s | %-5s |%n","TotalScore",calcTotalScore());
        System.out.printf("+--------------------------+%n%n");
    }

}
