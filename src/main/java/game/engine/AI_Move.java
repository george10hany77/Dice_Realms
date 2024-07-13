package game.engine;
import com.google.gson.Gson;
import game.collectibles.RewardType;
import game.creatures.*;
import game.dice.*;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import game.gui.PlaySceneController;


public class AI_Move implements Comparable {

    private Move move;
    int EC_Count;
    int EB_Count;
    int forgotten_dice_count;
    int score_increase;
    int bonus;
    double Ultimate_Value;
    int AB_Count;
    PlaySceneController playSceneController;

    public AI_Move(Move move, PlaySceneController playSceneController){
        this.move=move;
        this.playSceneController=playSceneController;
        forgotten_dice_count =cal_forgotten_dice_count();
        score_increase = calc_score_increase(move);
        bonus = calc_bonus(move);
        Ultimate_Value = EC_Count+EB_Count+(bonus*2)+AB_Count + score_increase-(forgotten_dice_count*2)+5-move.getDice().getRealm().ordinal();
//        Ultimate_Value = EC_Count+EB_Count+(bonus*2) + (score_increase*playSceneController.getGameStatus().getRoundNumber())-(forgotten_dice_count*2)+5-move.getDice().getRealm().ordinal();

        if(move.getCreature() instanceof Dragon){
            Ultimate_Value+=2;
        }
        if(move.getCreature() instanceof Gaia){
            Ultimate_Value+=3;
            Ultimate_Value+=Math.abs(move.getDice().getValue()-7)*0.4;
//            if(move.getDice().getValue() ==2 ){
//                Ultimate_Value+=2;
//            }
//            if(move.getDice().getValue() ==12 ){
//                Ultimate_Value+=0.5;
//            }

        }
        if(move.getCreature() instanceof Hydra) {
            Ultimate_Value+=2;
        }if(move.getCreature() instanceof Phoenix){
            Ultimate_Value+=(move.getDice().getValue()==5||move.getDice().getValue()==4)?-100:(move.getDice().getValue()==6)?2:0;
        }
        if(move.getCreature() instanceof Lion){
            Ultimate_Value+=1;
        }
    }

    public  int cal_forgotten_dice_count() {
        Dice[] dice = playSceneController.getAvailableDice();
        int count = 0;
        for (Dice die : dice) {
            if (die.getValue() < (move.getDice().getValue())) {
                count++;
            }
        }
        if (move.getDice().getRealm() == RealmColor.GREEN) {
            count = count / 2;
        }
        if (playSceneController.getGameStatus().getTurnNumber() == playSceneController.getGameStatus().getMaxTurnNumberActive()) {
            return 0;
        }
        if (playSceneController.getCurrentPlayer() == playSceneController.getPassivePlayer() || playSceneController.isArcaneSelection()){
            return 0;
        }

        return count;
    }
//    public  int cal_forgotten_dice_count(){
//        Dice[] dice = playSceneController.getAvailableDice();
//        int count = 0;
//        int subt = move.getDice().getValue();
//        if(move.getDice().getRealm() == RealmColor.GREEN){
//            subt = subt/2;
//        }
//        for(Dice die : dice){
//            if(die.getValue()<(subt)){
//                count++;
//            }
//        }
//           if(playSceneController.getGameStatus().getTurnNumber() == playSceneController.getGameStatus().getMaxTurnNumberActive()){
//        return 0;
//    }

//
//        return count;
//    }



        public int calc_score_increase (Move move){
            Gson gson = new Gson();
            Creature creature = move.getCreature();
            Creature copy = null;
            if (creature instanceof Dragon) {
                copy = gson.fromJson(gson.toJson(creature), Dragon.class);
            }
            if (creature instanceof Gaia) {
                copy = gson.fromJson(gson.toJson(creature), Gaia.class);
            }
            if (creature instanceof Hydra) {
                copy = gson.fromJson(gson.toJson(creature), Hydra.class);
            }
            if (creature instanceof Phoenix) {
                copy = gson.fromJson(gson.toJson(creature), Phoenix.class);
            }
            if (creature instanceof Lion) {
                copy = gson.fromJson(gson.toJson(creature), Lion.class);
            }
            int intital = creature.getScore();

            try {
                copy.attack(move.getDice());
                int end = copy.getScore();
                return end - intital;

            } catch (DiceRollException | InvalidMoveException | NullPointerException e) {
                e.printStackTrace();
            }


            return 0;
        }
        public int calc_bonus (Move move){
            Gson gson = new Gson();
            Creature creature = move.getCreature();
            Creature copy = null;
            if (creature instanceof Dragon) {
                copy = gson.fromJson(gson.toJson(creature), Dragon.class);
            }
            if (creature instanceof Gaia) {
                copy = gson.fromJson(gson.toJson(creature), Gaia.class);
            }
            if (creature instanceof Hydra) {
                copy = gson.fromJson(gson.toJson(creature), Hydra.class);
            }
            if (creature instanceof Phoenix) {
                copy = gson.fromJson(gson.toJson(creature), Phoenix.class);
            }
            if (creature instanceof Lion) {
                copy = gson.fromJson(gson.toJson(creature), Lion.class);
            }
            RewardType[] rewardTypes;
            try {

                rewardTypes = copy.attack(move.getDice());
                if (rewardTypes != null) {
                    for (RewardType rewardType : rewardTypes) {
                        if (rewardType == RewardType.EC) {
                            EC_Count++;
                        }
                        if (rewardType == RewardType.EB) {
                            EB_Count++;
                        }
                        if (rewardType == RewardType.AB ) {
                            AB_Count++;
                        }
                    }
                }
                if (rewardTypes != null) {
                    return rewardTypes.length;
                } else {
                    return 0;
                }

            } catch (DiceRollException | InvalidMoveException | NullPointerException e) {
                System.out.println("\033[96maimpve");
                e.printStackTrace();
            }


            return 0;
        }
        @Override
        public int compareTo (Object o){
            AI_Move other = (AI_Move) o;
            if (this.move.getDice().getRealm() == RealmColor.RED && other.move.getDice().getRealm() == RealmColor.RED) {
                Ultimate_Value += ((RedDice) this.move.getDice()).getDragonNumber().ordinal();
            }
            if (this.Ultimate_Value > other.Ultimate_Value) {
                return -1;
            } else if (this.Ultimate_Value < other.Ultimate_Value) {
                return 1;
            } else {
                return 0;
            }
//        if(move.getDice().getRealm() == RealmColor.GREEN && move.getDice().getValue() ==12 && other.move.getDice().getValue() != 12){
//            return 1;
//        }
//        if(EC_Count>other.EC_Count){
//            return 1;
//        }
//        if(EC_Count<other.EC_Count){
//            return -1;
//        }
//
//        if(forgotten_dice_count*2>other.forgotten_dice_count){
//            return -1;
//        }
//        if(forgotten_dice_count<other.forgotten_dice_count){
//            return 1;
//        }
//
//        if(bonus+(EB_Count*4)>other.bonus+(other.EB_Count*2)){
//            return 1;
//        }
//        if(move.getDice().getRealm() == RealmColor.MAGENTA && move.getDice().getValue() == 5){
//            return -1;
//        }
//
//
//        if(this.move.getDice().getRealm() == RealmColor.GREEN && other.move.getDice().getRealm() != RealmColor.GREEN){
//            return 1;
//        }
//        if(this.move.getDice().getRealm() == RealmColor.RED && other.move.getDice().getRealm() != RealmColor.RED){
//            return 1;
//        }
//        if(this.move.getDice().getRealm() == RealmColor.MAGENTA && other.move.getDice().getRealm() != RealmColor.MAGENTA){
//            return 1;
//        }
////        if(this.move.getDice().getRealm() != RealmColor.MAGENTA && other.move.getDice().getRealm() == RealmColor.MAGENTA){
////            return -1;
////        }
//
//        if(this.move.getDice().getRealm() == RealmColor.RED && other.move.getDice().getRealm() == RealmColor.RED){
//            if(((RedDice)move.getDice()).getDragonNumber().ordinal()>((RedDice)other.move.getDice()).getDragonNumber().ordinal()){
//                return 1;
//            }else if(((RedDice)move.getDice()).getDragonNumber().ordinal()==((RedDice)other.move.getDice()).getDragonNumber().ordinal()){
//                return 0;
//            }else{
//                return -1;
//            }
//        }
//        if(move.getDice().getRealm() == RealmColor.YELLOW){
//
//            if((score_increase-other.score_increase)<3){
//                return -1;
//            }
//
//        }else {
//            if (score_increase > other.score_increase) {
//                return 1;
//            }
//            if (score_increase < other.score_increase) {
//                return -1;
//            }
//        }
//        if(this.move.getDice().getRealm() == RealmColor.YELLOW && other.move.getDice().getRealm() != RealmColor.YELLOW){
//            return 1;
//        }
            // test 2
//
//
//        return 0;
        }

        public Move getMove () {
            return move;
        }
        public double getUltimate_Value(){
        return Ultimate_Value;
    }

        public static void main (String[]args){


        }


    }
