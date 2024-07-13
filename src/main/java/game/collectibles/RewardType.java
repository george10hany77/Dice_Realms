package game.collectibles;
import game.creatures.RealmColor;
import game.exceptions.InvalidPropertyKey;


public enum RewardType {
    RB("RedBonus",RealmColor.RED),
    GB("GreenBonus",RealmColor.GREEN),
    BB("BlueBonus",RealmColor.BLUE),
    MB("MagentaBonus",RealmColor.MAGENTA),
    YB("YellowBonus",RealmColor.YELLOW),
    EB("EssenceBonus"),
    AB("ArcaneBoost"),
    TW("TimeWarp"),
    EC("ElementalCrest"),
    X("X"),
    EMPTY("null");

    String name;
    RealmColor realmColor;

    RewardType(String name) {
        this.name = name;
    }
    RewardType(String name,RealmColor realmColor) {
        this.name = name;
        this.realmColor = realmColor;
    }

    public static RewardType getReward(String name) throws InvalidPropertyKey {
        switch (name) {
            case "RedBonus":
                return RB;
            case "GreenBonus":
                return GB;
            case "BlueBonus":
                return BB;
            case "MagentaBonus":
                return MB;
            case "YellowBonus":
                return YB;
            case "EssenceBonus":
                return EB;
            case "ArcaneBoost":
                return AB;
            case "TimeWarp":
                return TW;
            case "ElementalCrest":
                return EC;
            case "null":
                return EMPTY;
            default:
                throw new InvalidPropertyKey("String not found in Reward Types");
        }
    }
    public static RealmColor getRealmColor(RewardType type)
    {
        switch(type)
        {
            case RB:return RealmColor.RED;
            case GB:return RealmColor.GREEN;
            case BB:return RealmColor.BLUE;
            case MB:return RealmColor.MAGENTA;
            case YB:return RealmColor.YELLOW;
        }
        System.out.println("invalid call for the getRealmColor for RewardType");
        return null;
    }


}
