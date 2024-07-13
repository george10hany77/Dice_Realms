package game.engine;

import game.creatures.*;
import game.dice.Dice;
import game.exceptions.InvalidPropertyKey;

public class ScoreSheet {
    // Individuals responsible for each realm are tasked with implementing the
    // `toString()` method for their specific creature or realm.
    // The `ScoreSheet` object then combines all these individual representations
    // into a cohesive whole.

    // MOVE HISTORY
    private Creature[] creatures;

    public ScoreSheet() throws InvalidPropertyKey {
        // You have to init an array first before you access any element.
        creatures = new Creature[5];
        Dragon d = new Dragon();
        Gaia g = new Gaia();
        Lion l = new Lion();
        Hydra h = new Hydra();
        Phoenix p = new Phoenix();
        creatures[RealmColor.RED.ordinal()] = d;
        creatures[RealmColor.GREEN.ordinal()] = g;
        creatures[RealmColor.YELLOW.ordinal()] = l;
        creatures[RealmColor.BLUE.ordinal()] = h;
        creatures[RealmColor.MAGENTA.ordinal()] = p;
    }

    public String toString() {
        // using stringBuilder is way faster.
        StringBuilder out = new StringBuilder();
        out.append("\n\nScoreSheet\n\n");
        for (int i = 0; i < creatures.length - 1; i++) {
            out.append(creatures[i].toString()).append("\n");
        }
        out.append(creatures[creatures.length-1].toString());
        return out.toString();
    }

    public Creature getCreatureByRealm(Dice dice) {
        return creatures[dice.getRealm().ordinal()];
    }

    public Creature getCreatureByRealm(RealmColor color) {
        return creatures[color.ordinal()];
    }
}
