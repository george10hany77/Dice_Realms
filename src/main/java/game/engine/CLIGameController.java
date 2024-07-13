package game.engine;

import game.collectibles.ArcaneBoost;
import game.collectibles.RewardType;
import game.collectibles.TimeWarp;
import game.creatures.Creature;
import game.creatures.DragonNumber;
import game.creatures.RealmColor;
import game.dice.ArcanePrism;
import game.dice.BlueDice;
import game.dice.Dice;
import game.dice.DiceLocation;
import game.dice.GreenDice;
import game.dice.MagentaDice;
import game.dice.RedDice;
import game.dice.YellowDice;
import game.exceptions.DiceRollException;
import game.exceptions.InvalidMoveException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CLIGameController extends GameController {

    private Scanner sc = new Scanner(System.in);
    private GameBoard board;
    private final String reset = "\u001B[0m";

    public CLIGameController() {
        board = new GameBoard("Player1", "Player2");
    }

    @Override
    public void startGame() {
        System.out.println("Dice Realms: Quest for the Elemental Crests!");
        String name1;
        String name2;
        do {
            System.out.print("Enter your name Player1: ");
            name1 = sc.nextLine();
            System.out.print("Enter your name Player2: ");
            name2 = sc.nextLine();
            if (name1.equals(name2))
              System.out.println("Please enter different names for the 2 players");
            if (name1.length() < 1 || name2.length() < 1)
              System.out.println("Please enter a valid name");
        } while (name1.length() < 1 || name2.length() < 1 || name1.equals(name2));
        board.getPlayer1().setName(name1);
        board.getPlayer2().setName(name2);
        gameLoop();
    }

    @Override
    public boolean switchPlayer() {
        return board.getPlayer1().switchState() && board.getPlayer2().switchState();
    }

    @Override
    public Dice[] rollDice() {
        Dice[] dices = getAvailableDice();
        for (Dice dice : dices) {
            dice.roll();
        }
        return dices;
    }

    @Override
    public Dice[] getAvailableDice() {
        Dice[] dices = board.getDice();
        ArrayList<Dice> diceArrayList = new ArrayList<>();
        for (Dice dice : dices) {
            if (dice.getDiceLocation() == DiceLocation.ACTIVE) {
                diceArrayList.add(dice);
            }
        }
        int listSize = diceArrayList.size();
        Dice[] activeDices = new Dice[listSize];
        diceArrayList.toArray(activeDices);
        return activeDices;
    }

    @Override
    public Dice[] getAllDice() {
        return board.getDice();
    }

    @Override
    public Dice[] getForgottenRealmDice() {
        Dice[] dices = board.getDice();
        ArrayList<Dice> diceArrayList = new ArrayList<>();
        for (Dice dice : dices) {
            if (dice.getDiceLocation() == DiceLocation.FORGOTTEN) {
                diceArrayList.add(dice);
            }
        }
        int listSize = diceArrayList.size();
        Dice[] forgottenDices = new Dice[listSize];
        diceArrayList.toArray(forgottenDices);
        return forgottenDices;
    }

    @Override
    public Move[] getAllPossibleMoves(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return new Move[0];
        }
        RealmColor[] Colors = RealmColor.values();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Move[] temp = getPossibleMovesForRealm(player, Colors[i]);
            Collections.addAll(possibleMoves, temp);
        }
        Move[] Moves = new Move[possibleMoves.size()];
        Moves = possibleMoves.toArray(Moves);

        return Moves;
    }

    public Move[] getPossibleMovesForRealm(Player player, RealmColor color) {
        if (player == null){
            System.out.println("Player is null");
            return new Move[0];
        }
        if (color == null){
            System.out.println("Color is null");
            return new Move[0];
        }
        return player.getScoreSheet().getCreatureByRealm(color).getPossibleMoves();
    }

    public ArrayList<Integer> getPossibleAttackValuesForRealm(Player player, RealmColor color) {
        if (player == null){
            System.out.println("Player is null");
            return new ArrayList<>();
        }
        if (color == null){
            System.out.println("Color is null");
            return new ArrayList<>();
        }
        ArrayList<Integer> possibleAttackValues = new ArrayList<>();
        Move[] possibleMovesForRealm = null;
        possibleMovesForRealm = getPossibleMovesForRealm(player, color);
        for (Move move : possibleMovesForRealm) {
            possibleAttackValues.add(move.getDice().getValue());
        }
        return possibleAttackValues;
    }

    @Override
    public Move[] getPossibleMovesForAvailableDice(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return new Move[0];
        }
        Dice[] availableDice = this.getAvailableDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (Dice dice : availableDice) {
            if (dice instanceof GreenDice && board.getDice(RealmColor.WHITE.ordinal()).getDiceLocation()==DiceLocation.ACTIVE) {
                continue; // handle duplicate move of Green and white Important Condition Skip Green only if White is Active
            }
            Move[] temp = getPossibleMovesForADie(player, dice);
            Collections.addAll(possibleMoves, temp);
        }
        Move[] Moves = new Move[possibleMoves.size()];
        Moves = possibleMoves.toArray(Moves);

        return Moves;
    }

    public Move[] getPossibleMovesForForgottenDice(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return new Move[0];
        }
        Dice[] availableDice = this.getForgottenRealmDice();
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (Dice dice : availableDice) {
            if (dice instanceof GreenDice && board.getDice(RealmColor.WHITE.ordinal()).getDiceLocation()==DiceLocation.FORGOTTEN) {
                continue; // handle duplicate move of Green and white Important Condition Skip Green only if White is Active
            }
            Move[] temp = getPossibleMovesForADie(player, dice);
            Collections.addAll(possibleMoves, temp);
        }
        Move[] Moves = new Move[possibleMoves.size()];
        Moves = possibleMoves.toArray(Moves);

        return Moves;
    }

    @Override
    public Move[] getPossibleMovesForADie(Player player, Dice dice) {
        if (dice == null){
            System.out.println("Dice is not instantiated");
            return new Move[0];
        }
        try {
            switch (dice.getRealm()) {
                case RED:
                    
                case BLUE:

                case MAGENTA:

                case YELLOW:
                    Move[] tempMoves = player.getScoreSheet().getCreatureByRealm(dice).getPossibleMovesForADie(dice);
                    if (dice instanceof RedDice){ // Very important to reset dragon number.
                        ((RedDice) dice).resetDragonNumber();
                    }
                    return tempMoves;
                case GREEN:
                    Dice other = board.getDice(RealmColor.WHITE.ordinal());
                    GreenDice GreenPlusWhite = new GreenDice(dice.getValue() + other.getValue());
                    return player.getScoreSheet().getCreatureByRealm(dice).getPossibleMovesForADie(GreenPlusWhite);

                case WHITE:
                    ArrayList<Move> possibleMoves = new ArrayList<>();
                    RedDice red = new RedDice(dice.getValue());
                    Collections.addAll(possibleMoves,
                            player.getScoreSheet().getCreatureByRealm(red).getPossibleMovesForADie(red));

                    Dice Other = board.getDice(RealmColor.GREEN.ordinal());
                    GreenDice WhitePlusGreen = new GreenDice(dice.getValue() + Other.getValue());
                    Collections.addAll(possibleMoves, player.getScoreSheet().getCreatureByRealm(WhitePlusGreen)
                            .getPossibleMovesForADie(WhitePlusGreen));

                    BlueDice blue = new BlueDice(dice.getValue());
                    Collections.addAll(possibleMoves,
                            player.getScoreSheet().getCreatureByRealm(blue).getPossibleMovesForADie(blue));

                    MagentaDice magenta = new MagentaDice(dice.getValue());
                    Collections.addAll(possibleMoves,
                            player.getScoreSheet().getCreatureByRealm(magenta).getPossibleMovesForADie(magenta));

                    YellowDice yellow = new YellowDice(dice.getValue());
                    Collections.addAll(possibleMoves,
                            player.getScoreSheet().getCreatureByRealm(yellow).getPossibleMovesForADie(yellow));

                    Move[] Moves = new Move[possibleMoves.size()];
                    Moves = possibleMoves.toArray(Moves);
                    return Moves;

                default:
                    return new Move[0];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Move[0];
        }
    }

    @Override
    public GameBoard getGameBoard() {
        return board;
    }

    @Override
    public Player getActivePlayer() {
        Player p1 = board.getPlayer1();
        Player p2 = board.getPlayer2();
        return (p1.getPlayerStatus() == PlayerStatus.ACTIVE) ? p1 : p2;
    }

    @Override
    public Player getPassivePlayer() {
        Player p1 = board.getPlayer1();
        Player p2 = board.getPlayer2();
        return (p1.getPlayerStatus() == PlayerStatus.PASSIVE) ? p1 : p2;
    }

    @Override
    public ScoreSheet getScoreSheet(Player player) {
        if (player == null) {
            System.out.println("Player is null !");
            return null;
        }
        return player.getScoreSheet();
    }

    @Override
    public GameStatus getGameStatus() {
        return board.getStatus();
    }

    @Override
    public GameScore getGameScore(Player player) {
        if (player == null) {
            System.out.println("Player is null !");
            return null;
        }
        return player.getGameScore();
    }

    @Override
    public TimeWarp[] getTimeWarpPowers(Player player) {
        if (player == null) {
            System.out.println("Player is null !");
            return new TimeWarp[0];
        }
        return player.getTimeWarp();
    }

    @Override
    public ArcaneBoost[] getArcaneBoostPowers(Player player) {
        if (player == null) {
            System.out.println("Player is null !");
            return new ArcaneBoost[0];
        }
        return player.getArcaneBoost();
    }

    @Override
    public boolean selectDice(Dice dice, Player player) {
        // there were no available dices for selection for the player and so the method
        // select dice returns false when null is passed
        if (player == null){
            System.out.println("Player is null");
            return false;
        }
        if (dice == null){
            System.out.println("Dice is null");
            return false;
        }

        Dice[] dices;
        if (player.getPlayerStatus() == PlayerStatus.ACTIVE) {
            dices = getAvailableDice();

            for (Dice currDice : dices) {
                if (currDice.getValue() < dice.getValue()) {
                    currDice.updateDiceLocation(DiceLocation.FORGOTTEN);
                }

                else if (dice.getValue() == currDice.getValue() && dice.getRealm() == currDice.getRealm()) {
                    currDice.updateDiceLocation(DiceLocation.USED);
                }
            }
        } else {
            dices = getForgottenRealmDice();
            for (Dice curr : dices) {

                if (dice.getValue() == curr.getValue() && dice.getRealm() == curr.getRealm()) {
                    curr.updateDiceLocation(DiceLocation.USED);
                }
            }
        }
        return true;
    }

    @Override
    public boolean makeMove(Player player, Move move) {
        if (player == null){
            System.out.println("Player is null");
            return false;
        }
        if (move == null){
            System.out.println("Move is null");
            return false;
        }
        RewardType[] rewardTypes;
        Creature currentCreature = player.getScoreSheet().getCreatureByRealm(move.getDice());
        RealmColor currentRealmColor = move.getDice().getRealm();
        try {
            rewardTypes = currentCreature.attack(move.getDice());
        } catch (NullPointerException | InvalidMoveException | DiceRollException e) {
            System.out.println(e.getMessage());
            return false;
        }
        player.getGameScore().updateRealmScore(currentRealmColor, currentCreature.getScore());
        // 2ol2of ya hany
        if (rewardTypes != null) {
            if (rewardTypes.length == 2) {
                if (rewardTypes[1].ordinal() < rewardTypes[0].ordinal()) {
                    RewardType temp = rewardTypes[0];
                    rewardTypes[0] = rewardTypes[1];
                    rewardTypes[1] = temp;
                }
            }
            for (int i = 0; i < rewardTypes.length; i++) {
                // process the reward as needed
                rewardHandler(player, rewardTypes[i]);
            }
        }
        return true;
    }

    protected void rewardHandler(Player player, RewardType reward) {
        if (player == null){
            System.out.println("Player is null");
            return;
        }
        if (reward == null){
            System.out.println("Reward is null");
            return;
        }
        // process the reward as needed
        switch (reward) {

            case RB:
                if (!player.getScoreSheet().getCreatureByRealm(RealmColor.RED).isAttackable()) {
                    System.out.println("Dragons Are All Killed You Cannot Use The Bonus :() !!!");
                    return;
                }
                System.out.println(player.getName()+", You just gained a Red bonus!!");
                RedDice red = new RedDice();
                red.setValue(InputAttackValue(RealmColor.RED, player));
                red.selectsDragon(InputDragonNumber(player, red));
                makeMove(player, new Move(red, player.getScoreSheet().getCreatureByRealm(RealmColor.RED)));
                break;
            case GB:
                if (!player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN).isAttackable()) {
                    System.out.println("Gaias Are All Killed You Cannot Use The Bonus :() !!!");
                    return;
                }
                System.out.println(player.getName()+", You just gained a Green bonus!!");
                GreenDice green = new GreenDice();
                green.setValue(InputAttackValue(RealmColor.GREEN, player));
                makeMove(player, new Move(green, player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN)));
                break;
            case BB:
                if (!player.getScoreSheet().getCreatureByRealm(RealmColor.BLUE).isAttackable()) {
                    System.out.println("Hydras Are All Killed You Cannot Use The Bonus :() !!!");
                    return;
                }
                System.out.println(player.getName()+", You just gained a Blue bonus!!");
                BlueDice blue = new BlueDice();
                blue.setValue(InputAttackValue(RealmColor.BLUE, player));
                makeMove(player, new Move(blue, player.getScoreSheet().getCreatureByRealm(RealmColor.BLUE)));
                break;
            case MB:
                if (!player.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA).isAttackable()) {
                    System.out.println("Phoenix is Killed :() !!!");
                    return;
                }
                System.out.println(player.getName()+", You just gained a Magenta bonus!!");
                MagentaDice magenta = new MagentaDice();
                magenta.setValue(InputAttackValue(RealmColor.MAGENTA, player));
                makeMove(player, new Move(magenta, player.getScoreSheet().getCreatureByRealm(RealmColor.MAGENTA)));
                break;
            case YB:
                if (!player.getScoreSheet().getCreatureByRealm(RealmColor.YELLOW).isAttackable()) {
                    System.out.println("Lions Are All Killed You Cannot Use The Bonus :() !!!");
                    return;
                }
                System.out.println(player.getName()+", You just gained a Yellow bonus!!");
                YellowDice yellow = new YellowDice();
                yellow.setValue(InputAttackValue(RealmColor.YELLOW, player));
                makeMove(player, new Move(yellow, player.getScoreSheet().getCreatureByRealm(RealmColor.YELLOW)));
                break;
            case EB:
                System.out.println(player.getName()+", You just gained an Essence bonus!!");
                RealmColor setColor = this.InputAttackColor(player);
                if(setColor == null)
                    return;
                if (!(player.getScoreSheet().getCreatureByRealm(setColor).isAttackable())) {
                    System.out.println(setColor.getName()+" are All Killed You Cannot Use The Bonus :() !!!");
                    return;
                }
                RewardType temp = RewardType.values()[setColor.ordinal()];
                System.out.println("Enchanting color into the essence.....");
                rewardHandler(player, temp);
                
                break;
            case AB:
                if (player.addArcaneBoost()) {
                    System.out.println("Acquired an ArcaneBoost PowerUp");
                    System.out.println(player.getName()+", You currently have " + player.getArcaneBoostCount() + ((player.getArcaneBoostCount()==1)?" ArcaneBoost": " ArcaneBoosts"));
                }
                break;
            case EC:
                if (player.getGameScore().addElementalCrest()) {
                    System.out.println("Acquired an Elemental Crest!");
                    System.out.println(
                            player.getName()+", You currently have " + player.getGameScore().getElementalCount() + ((player.getGameScore().getElementalCount()==1)?" Elemental Crest": " Elemental Crests"));
                }
                break;
            case TW:
                if (player.addTimeWarp()) {
                    System.out.println("Acquired a TimeWarp PowerUp");
                    System.out.println(player.getName()+", You currently have " + player.getTimeWarpsCount() + ((player.getTimeWarpsCount()==1)?" TimeWarp": " TimeWarps"));
                }
                break;
            default:
                break;
        }
        System.out.println();
    }

    protected boolean useTimeWarp(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return false;
        }
        if (player.useTimeWarp()) {
            this.rollDice();
            return true;
        } else {
            return false;
        }
    }

    protected void useArcaneBoost(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return;
        }
        if (player.useArcaneBoost()) {
            turnArcane(player);
        }else {
            System.out.println("No available Arcane Boosts to be used !");
        }
    }

    private int InputDragonNumber(Player player, Dice red) {
        if (player == null){
            System.out.println("Player is null");
            return -1;
        }
        if (red == null){
            System.out.println("Red Dice is null");
            return -1;
        }
        Move[] possibleForRed = getPossibleMovesForADie(player, red);
        DragonNumber[] options = new DragonNumber[possibleForRed.length];
        int answer;
        for (int i = 0; i < possibleForRed.length; i++) {
            RedDice temp = (RedDice) possibleForRed[i].getDice();
            options[i] = temp.getDragonNumber();
            System.out.print(i + 1);
            System.out.print(": " + options[i].getName() + "  ");
        }
        System.out.println();
        do {
            System.out.println("Pick a Dragon to attack");
            String temp = sc.nextLine();
            while (!isValidNumber(temp)) {
                System.out.println("Pick a proper Dragon to attack");
                temp = sc.nextLine();
            }
            answer = Integer.parseInt(temp);
            if (answer < 1 || answer > options.length)
                System.out.println("Please Enter a valid number");
        } while (answer < 1 || answer > options.length);

        return (options[answer - 1].ordinal()) + 1;
    }

    private int InputAttackValue(RealmColor color, Player player) {
        if (player == null){
            System.out.println("Player is null");
            return -1;
        }
        if (color == null){
            System.out.println("Color is null");
            return -1;
        }
        String attackStr;
        ArrayList<Integer> attackValues = getPossibleAttackValuesForRealm(player, color);

        do {
            System.out.println("Enter an Attack Value");
            attackStr = sc.nextLine();
            if (!isValidNumber(attackStr) || !attackValues.contains(Integer.parseInt(attackStr))) {
                System.out.println("Please enter a valid value (integer) that is still available in the realm");
                System.out.println("Hint: look at the score sheet of the " + color + " realm");
            }
        } while (!isValidNumber(attackStr) || !attackValues.contains(Integer.parseInt(attackStr)));
        // takes color to then check the possible attack values according to the realm
        return Integer.parseInt(attackStr);
    }

    private boolean isValidNumber(String input) {
        if (input.length() < 1 || input.length() > 10)
            return false;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        // In case the input number is bigger than "Integer".
        long bigNum = Long.parseLong(input);
        if (bigNum < Integer.MIN_VALUE || bigNum > Integer.MAX_VALUE)
            return false;
        return true;
    }
    private RealmColor InputAttackColor(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return null;
        }
        ArrayList<Dice> possible = new ArrayList<>();
        Dice[] everySingleDice = this.getGameBoard().getDice();
        Dice[] allDice = new Dice[5];
        for (int i = 0; i < everySingleDice.length-1; i++) {
            allDice[i] = everySingleDice[i];
        }
        for (Dice dice : allDice) {
            if(player.getScoreSheet().getCreatureByRealm(dice).isAttackable())
                possible.add(dice);
        }
        String colorChoiceStr;
        int i = 0;
        do{
        for (i = 0; i < possible.size(); i++) {
            System.out.print(""+(i+1)+": "+possible.get(i).getTextColor()
            + possible.get(i).getRealm()+ "  " + reset);
        }
        if(possible.isEmpty())
        {
            System.out.println("No realms to attack");
            return null;
        }
        colorChoiceStr = sc.nextLine();
    }while(!isValidNumber(colorChoiceStr) || Integer.parseInt(colorChoiceStr) <= 0 ||
                Integer.parseInt(colorChoiceStr) > i);
        return possible.get(Integer.parseInt(colorChoiceStr)-1).getRealm();
    }

    private RealmColor InputAttackColor(Dice dice, Player player) {
        if (player == null){
            System.out.println("Player is null");
            return null;
        }
        if (dice == null){
            System.out.println("Dice is null");
            return null;
        }
        Move[] possible = getPossibleMovesForADie(player, dice);
        String colorChoiceStr;
        ArrayList<RealmColor> colors;
        RealmColor[] allcolor = RealmColor.values();
        int count;
        boolean condition;
        do {
            System.out.println("Choose an Attack Color");
            colors = new ArrayList<>();
            //count for the arraylist handling
            count = 1;
            int counter = 1;
            
            
            
            for (int i = 0; i < possible.length; i++) {    
                if (colors.isEmpty()) {       
                    colors.add(possible[i].getDice().getRealm());
                    count++;
                } else if (!(possible[i].getDice().getRealm().equals(colors.get(count - 2)))) {
                    colors.add(possible[i].getDice().getRealm());
                    count++;
                }
            }

            for (RealmColor realmColor : allcolor) {
                String output = "";
                //counter for display
                if(realmColor == RealmColor.WHITE)
                    continue;
                if(!colors.contains(realmColor))
                    {
                        output = "(Unplayable: no available moves)";
                    }
                String extraForGreen = "";
                if (realmColor == RealmColor.GREEN) {
                    extraForGreen = " (total attack value: "
                    + (board.getDice(RealmColor.GREEN.ordinal()).getValue() + dice.getValue()) + ")";
                }
                System.out.print("" + counter + ": " + realmColor.getTextColor()
                            + realmColor + extraForGreen + " " + reset+output+" ");
                counter++;
            }
            colorChoiceStr = sc.nextLine();
            //condition for wrong type of input (including limits)
            condition = (!isValidNumber(colorChoiceStr) || Integer.parseInt(colorChoiceStr) <= 0
            || Integer.parseInt(colorChoiceStr) >= 6);
            if(!condition && !colors.contains(allcolor[Integer.parseInt(colorChoiceStr)-1]))
            {
                System.out.println("No available moves for the "+ allcolor[Integer.parseInt(colorChoiceStr)-1] +" realm");
            }
        } while (condition || !colors.contains(allcolor[Integer.parseInt(colorChoiceStr)-1])); // this "do while" loop is checking the incoming
        return allcolor[Integer.parseInt(colorChoiceStr) - 1];
    }


    protected boolean askForArcane(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return false;
        }
        String answer;
        do {
            System.out.println(player.getName()+", You have " + player.getArcaneBoostCount() + " ArcaneBoosts");
            System.out.println("Do you want to use ArcaneBoost?\n 'y': yes 'n': no");
            answer = sc.nextLine();
            if (answer.length() != 1 || (answer.charAt(0) != 'y' && answer.charAt(0) != 'n'))
                System.out.println("Please Enter a valid character : 'y' or 'n'");
        } while (answer.length() != 1 || (answer.charAt(0) != 'y' && answer.charAt(0) != 'n'));
        return answer.charAt(0) == 'y';
    }

    private boolean askForTime(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return false;
        }
        String answer;
        do {
            System.out.println(player.getName()+", You have " + player.getTimeWarpsCount() + " TimeWarps");
            System.out.println("Do you want to use TimeWarp?\n 'y': yes 'n': no");
            answer = sc.nextLine();
            if (answer.length() != 1 || (answer.charAt(0) != 'y' && answer.charAt(0) != 'n'))
                System.out.println("Please Enter a valid character : 'y' or 'n'");
        } while (answer.length() != 1 || (answer.charAt(0) != 'y' && answer.charAt(0) != 'n'));
        return answer.charAt(0) == 'y';
    }

    // can be used for the ArcaneSelection by setting arcaneSelection as 'true'
    public Dice askForDiceSelection(Player player, boolean arcaneSelection) {
        if (player == null){
            System.out.println("Player is null");
            return null;
        }
        Dice[] boardDices;
        // if was called due to arcane powerup
        if (arcaneSelection) {
            boardDices = getAllDice();
            // for active player turns
        } else if (player.getPlayerStatus() == PlayerStatus.ACTIVE)
            boardDices = getAvailableDice();
        else
            // for passive player turn
            boardDices = getForgottenRealmDice();
        int selection = -1;
        ArrayList<Dice> availableDices = new ArrayList<>();
        ArrayList<Dice> validDiceOptions = new ArrayList<>();
        do {
            availableDices.clear();
            for (Dice dice : boardDices) {
                Move[] possibleMoves = getPossibleMovesForADie(player, dice);
                String output = " (Unplayable: no available moves)";
                availableDices.add(dice);
                if ((!arcaneSelection &&(possibleMoves.length != 0 && possibleMoves[0] != null)) ||
                        (arcaneSelection && (possibleMoves.length != 0 && possibleMoves[0] != null)
                                && !dice.isUsedByBoost())) //New condition for reused die in arcane
                {   validDiceOptions.add(dice);
                    String extraForGreen = "";
                    if (dice instanceof GreenDice) {
                        extraForGreen = board.getDice(RealmColor.GREEN.ordinal()).getTextColor()
                                + " (total attack value: "
                                + (board.getDice()[RealmColor.WHITE.ordinal()].getValue() + dice.getValue()) + ")"
                                + reset;
                    }
                    System.out.println(availableDices.size() + ": " + dice + extraForGreen);
                } else {
                    if(dice.isUsedByBoost())
                    {
                        output = " (Unplayable: Already used by boost)";
                    }
                    if(dice instanceof GreenDice)
                    {
                        output = board.getDice(RealmColor.GREEN.ordinal()).getTextColor()
                        + " (total attack value: "
                        + (board.getDice()[RealmColor.WHITE.ordinal()].getValue() + dice.getValue()) + ")"
                        + reset + output;
                    }
                    System.out.println(availableDices.size() + ": " +dice + output);
                }
            }
            System.out.println();
        } while (player.getPlayerStatus() != PlayerStatus.PASSIVE && !arcaneSelection && availableDices.size() != 0
                && player.hasTimeWarps() && askForTime(player) && useTimeWarp(player));
        
        if (validDiceOptions.size() != 0) {
            selection = checkScanner(availableDices);

            if (availableDices.get(selection - 1).getRealm() == RealmColor.RED && 
                getPossibleMovesForADie(player,availableDices.get(selection - 1)).length!=0 && !availableDices.get(selection - 1).isUsedByBoost()) {
                
                RedDice diceToBeConfigured = (RedDice) availableDices.get(selection - 1);
                diceToBeConfigured.selectsDragon(InputDragonNumber(player, diceToBeConfigured));

            } else if (availableDices.get(selection - 1).getRealm() == RealmColor.WHITE) {

            }
            System.out.println();
            return availableDices.get(selection - 1);
        } 
        else {
            System.out.println("No Playable Dice");
            return null;
        }
    }

    private int checkScanner(ArrayList<Dice> availableDices) {
        String inputStr;
        do {
            System.out.println("Select from the list above by entering number corresponding to the dice from 1 to "
                    + availableDices.size());
            inputStr = sc.nextLine();
        } while (!isValidNumber(inputStr) || Integer.parseInt(inputStr) < 1
                || Integer.parseInt(inputStr) > availableDices.size());
        return Integer.parseInt(inputStr);
    }

    protected void gameLoop() {
        System.out.println();
        System.out.println("ROLL YOUR DICE AND DRAW YOUR WANDS!!!");
        System.out.println(board.getPlayer1().getName() + " you start!");
        System.out.println();
        int maxRoundNumber = board.getStatus().getMaxRoundNumber();
        for (int i = 1; i <= maxRoundNumber; i++) {
            RewardType currentRoundReward = this.getGameStatus().getRoundReward();
            System.out.println("Round " + i + ":");
            // first part of the round
            System.out.println();
            System.out.println(this.getActivePlayer().getName() + "...you're the active wizard!!");
            System.out.println();
            halfRound(true, currentRoundReward);
            // switch players
            this.switchPlayer();
            board.resetAllDices();
            System.out.println(this.getActivePlayer().getName() + "...you're the active wizard!!");
            System.out.println();
            // second part of the round
            System.out.println();
            halfRound(true, currentRoundReward);
            this.switchPlayer();
            board.getStatus().resetTurn();
            board.getStatus().updateRound();
            board.resetAllDices();
        }
        System.out.println("Your realm scores "+this.getGameBoard().getPlayer1().getName()+":");
        this.getGameScore(this.getGameBoard().getPlayer1()).displayGameScore();
        System.out.println("Your realm scores "+this.getGameBoard().getPlayer2().getName()+":");
        this.getGameScore(this.getGameBoard().getPlayer2()).displayGameScore();
        int player1Score = this.getGameBoard().getPlayer1().getGameScore().calcTotalScore();
        int player2Score = this.getGameBoard().getPlayer2().getGameScore().calcTotalScore();
        if(player1Score > player2Score)
        {
            System.out.println(this.getGameBoard().getPlayer1().getName()+" is the SUPREME Sorcerer...THE PROTECTOR OF ELDORIA");
            this.getGameBoard().getStatus().setGameState(GameState.PLAYER_1_WINS);
        }
        else if(player2Score>player1Score)
        {
            System.out.println(this.getGameBoard().getPlayer2().getName()+" is the SUPREME Sorcerer...THE PROTECTOR OF ELDORIA");
            this.getGameBoard().getStatus().setGameState(GameState.PLAYER_2_WINS);
        }
        else{
            System.out.println("After ages of relentless battling, the two wizards stood at a stalemate, their powers so evenly matched that neither could claim victory nor concede defeat...");
            this.getGameBoard().getStatus().setGameState(GameState.DRAW);

        }
        sc.close();
    }

    protected void halfRound(boolean roundRewardUse, RewardType roundReward) {
        if (roundReward == null){
            System.out.println("Reward is null");
            return;
        }
        int maxTurnNumberActive = board.getStatus().getMaxTurnNumberActive();
        int maxTurnNumberPassive = board.getStatus().getMaxTurnNumberPassive();
        if (roundRewardUse && (roundReward != RewardType.EMPTY)) {
            rewardHandler(getActivePlayer(), roundReward);
        }
        for (int i = 0; i < maxTurnNumberActive; i++) {
            if (this.getAvailableDice().length == 0) {
                System.out.println("No available dices for you");
                System.out.println("End of your turn...");
                System.out.println();
                board.getStatus().resetTurn();
                break;
            }
            activeTurn();
            this.getGameStatus().updateTurn();
        }
        System.out.println(this.getActivePlayer().getName()+":");
        System.out.println(this.getActivePlayer().getScoreSheet());
        this.getGameStatus().resetTurn();
        this.getGameBoard().resetUseByBoost();
        System.out.println();
        System.out.println(this.getPassivePlayer().getName() + "...you're the passive wizard!!");
        System.out.println();
        // if (roundRewardUse && (roundReward != RewardType.EMPTY)) {
        //     rewardHandler(getPassivePlayer(), roundReward);
        // }
        Dice[] dices = getAllDice();
        for (Dice dice : dices) {
            if (dice.getDiceLocation()==DiceLocation.ACTIVE)
                dice.updateDiceLocation(DiceLocation.FORGOTTEN);
        }
        System.out.println("Your turn...");
        System.out.println();
        System.out.println("Your ScoreSheet:");
        System.out.println(this.getScoreSheet(getPassivePlayer()));
        for (int i = 0; i < maxTurnNumberPassive; i++) {
            if (this.getForgottenRealmDice().length == 0) {
                System.out.println("No available dice for you!");
                System.out.println("End of your turn...");
                System.out.println();
                this.getGameStatus().resetTurn();
                break;
            }
            forgottenTurn();
            this.getGameBoard().getStatus().updateTurn();
        }
        this.getGameStatus().resetTurn();
        board.resetUseByBoost();
        while (this.getActivePlayer().hasArcaneBoost()) {
            if (canUseArcane(getActivePlayer()) && askForArcane(getActivePlayer()))
                useArcaneBoost(getActivePlayer());
            // 7EWAR YET3MEL
            else if (!canUseArcane(getActivePlayer())){
                System.out.println(getActivePlayer().getName()+", No Available Dice to be used by ArcaneBoost" );
                System.out.println("You currently have " + getActivePlayer().getArcaneBoostCount() + ((getActivePlayer().getArcaneBoostCount()==1)?" ArcaneBoost": " ArcaneBoosts"));
                break;
            } else {// will return the answer "no"
                break;
            }
        }
        board.resetUseByBoost();
        while (this.getPassivePlayer().hasArcaneBoost() && canUseArcane(getPassivePlayer())) {
            if (canUseArcane(getPassivePlayer()) && askForArcane(getPassivePlayer()))
                useArcaneBoost(getPassivePlayer());
            // 7EWAR YET3MEL
            else if (!canUseArcane(getPassivePlayer())){
                System.out.println(getPassivePlayer().getName()+", No Available Dice to be used by ArcaneBoost" );
                System.out.println("You currently have " + getPassivePlayer().getArcaneBoostCount() + ((getPassivePlayer().getArcaneBoostCount()==1)?" ArcaneBoost": " ArcaneBoosts"));
                break;
            } else {// will return the answer "no"
                break;
            }
        }

    }

    protected boolean canUseArcane(Player player) {
        Dice[] allDice = getAllDice();
        for (Dice die : allDice) {
            Move[] possibleMoves = getPossibleMovesForADie(player, die);
            if (!die.isUsedByBoost() && possibleMoves.length!=0  && possibleMoves[0]!=null) {
                return true; 
            }
        }
        return false;
    }

    protected void activeTurn() {
        System.out.println(this.getScoreSheet(getActivePlayer()));
        System.out.println("Active Turn : " + this.getGameBoard().getStatus().getTurnNumber() + " for " + this.getActivePlayer().getName());
        System.out.println();
        rollDice();
        Dice d;
        Move Attack;
        boolean moveFlag=true;
        do {
        d = askForDiceSelection(getActivePlayer(), false);
        // Do while if make move is false
        if (d != null) {
           
            if (d.getRealm().equals(RealmColor.GREEN)) {
                Dice other = board.getDice(RealmColor.WHITE.ordinal());
                GreenDice GreenPlusWhite = new GreenDice(d.getValue() + other.getValue());
                Attack = new Move(GreenPlusWhite,
                        getActivePlayer().getScoreSheet().getCreatureByRealm(RealmColor.GREEN));
            } else if (d.getRealm().equals(RealmColor.WHITE)) {
                ArcanePrism whiteDie = (ArcanePrism) d;
                Attack = WhiteDieHandler(whiteDie, getActivePlayer());
            } else {
                Attack = new Move(d, getActivePlayer().getScoreSheet().getCreatureByRealm(d));
            }

                moveFlag = (makeMove(getActivePlayer(), Attack));
                if (!moveFlag) {
                    System.out.println("Move not done :(");
                }
        }
        else {
            return;
        }
    } while(!moveFlag);
                
    if (!this.selectDice(d, getActivePlayer())) {
                // dice already used
                System.out.println("error in active turn  :(");
        }
        
    }

    protected void forgottenTurn() {
        System.out.println("Passive Turn: " + this.getGameBoard().getStatus().getTurnNumber() + " for " + this.getPassivePlayer().getName());
        System.out.println();
        Dice d;
        Move Attack;
        boolean moveFlag=true;
        do {
        d = askForDiceSelection(getPassivePlayer(), false);
        // Do while if make move is false
        if (d != null) {
           
            if (d.getRealm().equals(RealmColor.GREEN)) {
                Dice other = board.getDice(RealmColor.WHITE.ordinal());
                GreenDice GreenPlusWhite = new GreenDice(d.getValue() + other.getValue());
                Attack = new Move(GreenPlusWhite,
                        getPassivePlayer().getScoreSheet().getCreatureByRealm(RealmColor.GREEN));
            } else if (d.getRealm().equals(RealmColor.WHITE)) {
                ArcanePrism whiteDie = (ArcanePrism) d;
                Attack = WhiteDieHandler(whiteDie, getPassivePlayer());
            } else {
                Attack = new Move(d, getPassivePlayer().getScoreSheet().getCreatureByRealm(d));
            }

                moveFlag = (makeMove(getPassivePlayer(), Attack));
                if (!moveFlag) {
                    System.out.println("Move not done :(");
                }
        }
        else {
            return;
        }
    } while(!moveFlag);
                
    if (!this.selectDice(d, getPassivePlayer())) {
                // dice already used
                System.out.println("error in forgotten turn  :(");
        }

    }

    private void turnArcane(Player player) {
        if (player == null){
            System.out.println("Player is null");
            return;
        }
        Dice d;
        Move Attack;
        boolean moveFlag=true;
        do {
        d = askForDiceSelection(player, true);
        // Do while if make move is false
        if (d != null) {
           
            if (d.getRealm().equals(RealmColor.GREEN)) {
                Dice other = board.getDice(RealmColor.WHITE.ordinal());
                GreenDice GreenPlusWhite = new GreenDice(d.getValue() + other.getValue());
                Attack = new Move(GreenPlusWhite,
                        player.getScoreSheet().getCreatureByRealm(RealmColor.GREEN));
            } else if (d.getRealm().equals(RealmColor.WHITE)) {
                ArcanePrism whiteDie = (ArcanePrism) d;
                Attack = WhiteDieHandler(whiteDie, player);
            } else {
                Attack = new Move(d, player.getScoreSheet().getCreatureByRealm(d));
            }
                if (d.isUsedByBoost())
                    System.out.println("Dice was already used by ArcaneBoost");
                
                else {    moveFlag = (makeMove(player, Attack));
                if (!moveFlag) {
                    System.out.println("Move not done :(");
                }
                }
        }
        else {
            return;
        }
    } while(!moveFlag || d.isUsedByBoost());
        
    d.useByBoost();         
    
        if (!this.selectDice(d, player)) {
                // dice already used
                System.out.println("error in arcane turn  :(");
        }
    }

    public Move WhiteDieHandler(ArcanePrism whiteDie, Player player) {
        if (player == null){
            System.out.println("Player is null");
            return null;
        }
        if (whiteDie == null){
            System.out.println("Dice is null");
            return null;
        }
        Move Attack;
        do {
            RealmColor choice = InputAttackColor(whiteDie, player);
            switch (choice) {
                case RED:
                    RedDice tempRed = new RedDice(whiteDie.getValue());
                    tempRed.selectsDragon(InputDragonNumber(player, tempRed));
                    Attack = new Move(tempRed, player.getScoreSheet().getCreatureByRealm(tempRed));
                    break;
                case GREEN:
                    Dice other = board.getDice(RealmColor.GREEN.ordinal());
                    GreenDice GreenPlusWhite = new GreenDice(whiteDie.getValue() + other.getValue());
                    Attack = new Move(GreenPlusWhite, player.getScoreSheet().getCreatureByRealm(GreenPlusWhite));
                    break;
                case BLUE:
                    BlueDice tempBlue = new BlueDice(whiteDie.getValue());
                    Attack = new Move(tempBlue, player.getScoreSheet().getCreatureByRealm(tempBlue));
                    break;
                case MAGENTA:
                    MagentaDice tempMagenta = new MagentaDice(whiteDie.getValue());
                    Attack = new Move(tempMagenta, player.getScoreSheet().getCreatureByRealm(tempMagenta));
                    break;
                case YELLOW:
                    YellowDice tempYellow = new YellowDice(whiteDie.getValue());
                    Attack = new Move(tempYellow, player.getScoreSheet().getCreatureByRealm(tempYellow));
                    break;
                default:
                    Attack = null;
                    System.out.println("Attack color not set");
                    break;
            }

        } while (Attack == null);
        return Attack;
    }
}




