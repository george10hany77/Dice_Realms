# Project Skeleton

## Folder Structure

```css
dice-realms-game-badawayyy
|   Grades.md
|   pom.xml
|   ProjectSkeleton.md
|   
\---src
    +---main
    |   +---java
    |   |   |   module-info.java
    |   |   |   
    |   |   \---game
    |   |       |   Main.java
    |   |       |   
    |   |       +---collectibles
    |   |       |       ArcaneBoost.java
    |   |       |       Powerup.java
    |   |       |       PowerUpState.java
    |   |       |       RewardType.java
    |   |       |       TimeWarp.java
    |   |       |       
    |   |       +---creatures
    |   |       |       Creature.java
    |   |       |       Dragon.java
    |   |       |       DragonNumber.java
    |   |       |       Gaia.java
    |   |       |       Hydra.java
    |   |       |       Lion.java
    |   |       |       Phoenix.java
    |   |       |       RealmColor.java
    |   |       |       
    |   |       +---dice
    |   |       |       ArcanePrism.java
    |   |       |       BlueDice.java
    |   |       |       Dice.java
    |   |       |       DiceLocation.java
    |   |       |       GreenDice.java
    |   |       |       MagentaDice.java
    |   |       |       RedDice.java
    |   |       |       YellowDice.java
    |   |       |       
    |   |       +---engine
    |   |       |       AI_Move.java
    |   |       |       AI_Player.java
    |   |       |       CLIGameController.java
    |   |       |       GameBoard.java
    |   |       |       GameController.java
    |   |       |       GameScore.java
    |   |       |       GameState.java
    |   |       |       GameStatus.java
    |   |       |       Move.java
    |   |       |       Player.java
    |   |       |       PlayerStatus.java
    |   |       |       ScoreSheet.java
    |   |       |       
    |   |       +---exceptions
    |   |       |       DiceRollException.java
    |   |       |       InvalidDragonNumber.java
    |   |       |       InvalidMoveException.java
    |   |       |       InvalidPropertyKey.java
    |   |       |       UnavailablePowerup.java
    |   |       |       
    |   |       \---gui
    |   |               DiceRealms.java
    |   |               DragonGUI.java
    |   |               EndSceneController.java
    |   |               GaiaGUI.java
    |   |               GameModeController.java
    |   |               HydraGUI.java
    |   |               LionGUI.java
    |   |               MainSceneController.java
    |   |               PhoenixGUI.java
    |   |               PlayerNamesController.java
    |   |               PlaySceneController.java
    |   |               SettingsSceneController.java
    |   |               TofiiTestGUI.java
    |   |               
    |   \---resources
    |       |   EmptyScoreSheet.txt
    |       |   gaia.css
    |       |   gaia.fxml
    |       |   GameMode.fxml
    |       |   MainScene.fxml
    |       |   myStyle.css
    |       |   PlayerNames.fxml
    |       |   PlayScene.fxml
    |       |   SettingsScene.fxml
    |       |   
    |       +---config
    |       |       EmberfallDominionRewards.properties
    |       |       EmberfallDominionScores.properties
    |       |       EmberfallDominionValues.properties
    |       |       MysticalSkyRewards.properties
    |       |       PowerUpsMaxSize.properties
    |       |       RadiantSvannaMultiplier.properties
    |       |       RadiantSvannaRewards.properties
    |       |       Round&Turn.properties
    |       |       RoundsRewards.properties
    |       |       TerrasHeartlandRewards.properties
    |       |       TerrasHeartlandScores.properties
    |       |       TerrasHeartlandValues.properties
    |       |       TideAbyssAttackValues.properties
    |       |       TideAbyssRewards.properties
    |       |       TideAbyssScores.properties
    |       |       
    |       +---Dice
    |       |   +---BlueDie
    |       |   |   |   BlueDieIdle.png
    |       |   |   |   
    |       |   |   \---BlueDieAnimations
    |       |   +---GreenDie
    |       |   |   |   GreenDieIdle.png
    |       |   |   |   
    |       |   |   \---GreenDieAnimations
    |       |   +---MagentaDie
    |       |   |   |   MagentaDieIdle.png
    |       |   |   |   
    |       |   |   \---MagentaDieAnimations
    |       |   +---RedDie
    |       |   |   |   RedDieIdle.png
    |       |   |   |   
    |       |   |   \---RedDieAnimations
    |       |   +---WhiteDie
    |       |   |   |   WhiteDieIdle.png
    |       |   |   |   
    |       |   |   \---WhiteDieAnimations
    |       |   \---YellowDie
    |       |       |   YellowDieIdle.png
    |       |       |   
    |       |       \---YellowDieAnimations
    |       +---images
    |       |       Background_for_game.png
    |       |       finalbgrd.jpeg
    |       |       icon.png
    |       |       inbetween.jpeg
    |       |       NameAsking.jpeg
    |       |       playbtn.jpg
    |       |       Project-UML-Diagram.png
    |       |       SettingsBGR.jpeg
    |       |       _4373c687-a550-40bb-93be-a788e9fc0c37.jpeg
    |       |       
    |       \---music
    |               music.mp3
    |               
    \---test
        \---java
            \---game
                \---engine
                        CLIGameControllerTest.java
```

## Packages

### game.engine

This package serves as the core for all game mechanics, containing both abstract and concrete classes that manage game flow, player interactions, and the game board:

- `GameController`: Abstract class that sets the foundational methods for game controllers.
- `CLIGameController`: Extends GameController, implementing the CLI (Command Line Interface) version of the game controller.
- `GameBoard`: Manages the layout and state of the game board, including dice and players.
- `Player`: Represents a player in the game, managing their status, score, and actions.
- `PlayerStatus`: Denotes the only possible states for a player.
- `ScoreSheet`: Keeps track of a player's scores across different realms.
- `GameStatus`: Manages the current status of the game, including rounds and turns.
- `GameState`: Denotes the only possible states for the game.
- `GameScore`: Details the scoring logic and current scores for players.
- `Move`: Represents a player's move, including dice and creature interactions.

### game.dice

This package includes classes for different types of dice and their statuses:

- `Dice`: Abstract base class for all dice.
- `RedDice`, `GreenDice`, `BlueDice`, `MagentaDice`, `YellowDice`, `ArcanePrism`: Specific dice types used in the game, each with unique properties.
- `DiceLocation`: Denotes ther onbly possible locations for the dice.

### game.creatures

This package houses classes that represent various mythical creatures in the game:

- `Creature`: Abstract base class for all creatures.
- `Dragon`, `Gaia`, `Hydra`, `Phoenix`, `Lion`: Specific creature classes with unique attributes and behaviors.
- `DragonNumber`: Denotes the 4 dragons in the game and rejects any wrong dragon number.
- `RealmColor`: Denotes the 6 colors in the game.

### game.collectibles

This package includes classes for different types of game rewards and powers that players can collect:

- `RewardType`: Denotes all possible rewards and
- `Powerup`: Abstract base class for power-up abilities.
- `PowerUpState`: Denotes the only possible states for a PowerUp.
- `ArcaneBoost`, `TimeWarp`: Various collectible items of power-ups and bonuses that provide advantages in gameplay.

### game.exceptions

This package defines custom exceptions to handle various error scenarios specifically related to game actions:

- `InvalidMoveException`
- `DiceRollException`
- `InvalidDragonNumber`
- `InvalidPropertyKey`
- `UnavailablePowerup`

### game.gui

This package is designated for graphical user interface components, which are crucial for versions of the game that include a GUI.

- `DiceRealms.java`: Runs the program.
- `DragonGUI`, `GaiaGUI`, `HydraGUI`, `PhoenixGUI` and `LionGUI`: Handle the GUI for each realm.
- `GameModeController`, `MainSceneController`, `PlayerNamesController`, `PlaySceneController` and `SettingsSceneController`: Controllers for various scenes in the game.


## ***Implementation***

Here is the detailed implementation skeleton for each of the classes and their corresponding methods:

## `game.engine` package

### `GameController.java` class

- **Package**: `game.engine`
- **Type**: Abstract Class
- **Description**: This abstract class represents the controller for the game. It defines the common blueprint for different controllers used in the game.

#### Methods:

1. `void startGame()`

   - **Description**: Initializes necessary components and starts the game loop.

2. `boolean switchPlayer()`

   - **Description**: Switches the role of the current active player to passive and vice versa, ensuring that the turn-taking mechanism functions correctly.
   - **Return Type**: `boolean`
     - `true` if the switch was successful,
     - `false` otherwise.

3. `Dice[] rollDice()`

   - **Description**: Rolls all available dice for the current turn, assigning each a random number from 1 to 6.
   - **Return Type**: Array of `Dice`
     - An array of the currently rolled dice.

4. `Dice[] getAvailableDice()`

   - **Description**: Gets the dice available for rolling or rerolling.
   - **Return Type**: Array of `Dice`
     - An array of dice available for the current turn.

5. `Dice[] getAllDice()`

   - **Description**: Gets all six dice, providing their current state and value within the game regardless of their location or status.
   - **Return Type**: Array of `Dice`
     - An array of all six dice, with each die's state and value.

6. `Dice[] getForgottenRealmDice()`

   - **Description**: Gets the dice currently available in the Forgotten Realm.
   - **Return Type**: Array of `Dice`
     - An array of dice that are currently in the Forgotten Realm.

7. `Move[] getAllPossibleMoves(Player player)`

   - **Description**: Gets all possible moves for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
   - **Return Type**: Array of `Move`
     - An array of all possible moves for all rolled dice.

8. `Move[] getPossibleMovesForAvailableDice(Player player)`

   - **Description**: Gets possible moves for all currently rolled dice for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
   - **Return Type**: Array of `Move`
     - An array of all possible moves for all rolled dice.

9. `Move[] getPossibleMovesForADie(Player player, Dice dice)`

   - **Description**: Gets all possible moves for a given die for a given player.
   - **Parameters**:
     - `player`: The player for whom to determine possible moves.
     - `dice`: The dice to determine possible moves for.
   - **Return Type**: Array of `Move`
     - An array of possible moves for the given dice.

10. `GameBoard getGameBoard()`

    - **Description**: Gets the current game board, including all players and all score sheets.
    - **Return Type**: `GameBoard`
      - The current game board object.

11. `Player getActivePlayer()`

    - **Description**: Gets the current active player's information.
    - **Return Type**: `Player`
      - The active player object.

12. `Player getPassivePlayer()`

    - **Description**: Gets the current passive player's information.
    - **Return Type**: `Player`
      - The passive player object.

13. `ScoreSheet getScoreSheet(Player player)`

    - **Description**: Gets the score sheet for a given player.
    - **Parameters**:
      - `player`: The player to get the current score sheet for.
    - **Return Type**: `ScoreSheet`
      - The score sheet object for the given player.

14. `GameStatus getGameStatus()`

    - **Description**: Gets the current game status, including round and turn information for the current active player.
    - **Return Type**: `GameStatus`
      - The current game status object.

15. `GameScore getGameScore(Player player)`

    - **Description**: Gets the current score of the game for a given player.
    - **Parameters**:
      - `player`: The player to determine current score for.
    - **Return Type**: `GameScore`
      - The current game score object for the given player.

16. `TimeWarp[] getTimeWarpPowers(Player player)`

    - **Description**: Gets the array of TimeWarp powers and their status for a given player.
    - **Parameters**:
      - `player`: The player to get the current TimeWarp powers for.
    - **Return Type**: Array of `TimeWarp`
      - An array of `TimeWarp` objects representing the TimeWarp powers for the given player.

17. `ArcaneBoost[] getArcaneBoostPowers(Player player)`

    - **Description**: Gets the array of ArcaneBoost powers and their status for a given player.
    - **Parameters**:
      - `player`: The player to get the current ArcaneBoost powers for.
    - **Return Type**: Array of `ArcaneBoost`
      - An array of `ArcaneBoost` objects representing the ArcaneBoost powers for the given player.

18. `boolean selectDice(Dice dice, Player player)`

    - **Description**: Selects a die and adds it to the player's class, then moves all other dice with less value to the Forgotten Realm.
    - **Parameters**:
      - `player`: The player who selected the die.
      - `dice`: The dice to be selected.
    - **Return Type**: `boolean`
      - `true` if the selection was successful,
      - `false` otherwise.

19. `boolean makeMove(Player player, Move move)`

    - **Description**: Executes a move using the selected dice on a specified creature.
    - **Parameters**:
      - `player`: The player who wants to make the move.
      - `move`: The move to be executed, including the selected dice and target creature.
    - **Return Type**: `boolean`
      - `true` if the move is successfully completed,
      - `false` otherwise.

### `CLIGameController.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the CLI game controller that handles all data and the flow of the gameplay.

#### Methods: 
1. `void startGame`  
   - **Description**: begins the game and creates initial data elements of the whole game.


2. `boolean switchPlayer`  
   - **Description**: switches the roles of PASSIVE and ACTIVE among both players.
   - **Return Type**: `boolean`
      - `true` if switch was successful.
      - `false` if switch failed.


3. `Dice[] rollDice`  
   - **Description**: creates the dice and gives them random values.
   - **Return Type**: Array of `Dice`
     - the array of the new dice with new values.


4. `Dice[] getAvailableDice`  
   - **Description**: returns the dice that haven't been used yet.
   - **Return Type**: Array of `Dice`
     - Array of Dice that are Active.


5. `Dice[] getAllDice`  
   - **Description**: returns all dice independent from their states.
   - **Return Type**: Array of `Dice`
     - array of all existent dice.


6. `Dice[] getForgottenRealmDice`  
   - **Description**: returns all dice that are in The Forgotten Realm.
   - **Return Type**: Array of `Dice`
     - array of dice in Forgotten Realm.


7. `Move[] getAllPossibleMoves`  
   - **Description**: returns all moves still available in all creatures.
   - **Parameters**:
     - `player`: the player whose moves are checked for.
   - **Return Type**: Array of `Move`
     - an array of all all possible moves for the 5 creatures/realms.


8. `Move[] getPossibleMovesForRealm`  
   - **Description**: returns all possible moves for specific creature
   - **Parameters**:
     - `player`: the player whose moves are checked for.
     - `color`: the color of specified realm.
   - **Return Type**: Array of `Move`
     - Array of all possible moves for the specified realm.


9. `ArrayList<Integer> getPossibleAttackValuesForRealm`  
   - **Description**: returns all possible values that can be used to attack a specific realm of a certain player.
   - **Parameters**:
     - `player`: the player whose realms are checked.
     - `color`: the specified realm.
   - **Return Type**: `ArrayList<Integer>`
     - ArrayList of possible attack values.


10. `Move[] getPossibleMovesForAvailableDice`  
   - **Description**:  returns all possible moves playable with current dice values of a certain player.
   - **Parameters**:
     - `player`: the player whose dice are checked.
   - **Return Type**: Array of `Move`
     - an array of all moves that can be played with current dice.


11. `Move[] getPossibleMovesForADie`  
   - **Description**: returns all possible moves playable with a specified die of certain player.
   - **Parameters**:
     - `player`: the player whose die is checked.
     - `dice`:  dice we want to find out for it is possible moves
   - **Return Type**: Array of `Move`
     - an array of all moves that can be played with choosen die.


12. `GameBoard getGameBoard`  
   - **Description**: returns the GameBoard of the current running game.
   - **Return Type**: `GameBoard`
     - GameBoard of the current running game.


13. `Player getActivePlayer`  
   - **Description**: returns the player that is currently active.
   - **Return Type**: `Player`
     - the current active player.


14. `Player getPassivePlayer`  
   - **Description**: returns the player that is currently passive.
   - **Return Type**: `Player`
     - the current passive player.


15. `ScoreSheet getScoreSheet`  
   - **Description**: returns the ScoreSheet of the specified player that holds all data relevant to their realms.
   - **Parameters**:
     - `player`: the player whose ScoreSheet is checked.
   - **Return Type**: `ScoreSheet`
     - ScoreSheet Object of player.


16. `GameStatus getGameStatus`  
   - **Description**: returns the status of the current game.
   - **Return Type**: `GameStatus`
     - the status of the current game.


17. `GameScore getGameScore`  
   - **Description**: returns the current scores of the specified player.
   - **Parameters**:
     - `player`: the player whose scores are checked.
   - **Return Type**: `GameScore`
     - the GameScore object of the specified player.


18. `TimeWarp[] getTimeWarpPowers`  
   - **Description**: returns all TimeWarps in the game independent of their states for a certain player.
   - **Parameters**:
     - `player`: the player whose TimeWarps are checked.
   - **Return Type**: Array of `TimeWarp`
     - an array of all TimeWarps in the game.


19. `ArcaneBoost[] getArcaneBoostPowers`  
   - **Description**: returns all ArcaneBoosts in the game independent of their states for a certain player.
   - **Parameters**:
     - `player`: the player whose ArcaneBoosts are checked.
   - **Return Type**: Array of `TimeWarp`
     - an array of all ArcaneBoosts in the game.


20. `boolean selectDice`  
   - **Description**: selects the chosen die by the specified player and sends all lower value dice the forgotten realm and marks selected die as used.
   - **Parameters**:
     - `dice`: the chosen die.
     - `player`: the player who chose the dice.
   - **Return Type**: `boolean`
      - `true` if selection was successful.
      - `false` if selection failed.


21. `boolean makeMove`  
   - **Description**: executes an attack towards the creature of the die inside the move for a specified player.
   - **Parameters**:
     - `player`: the player who made the move.
     - `move`: the desired attack move: carrying the die and creature.
   - **Return Type**: `boolean`
      - `true` making move was successful.
      - `false` making move failed.


22. `void rewardHandler`  
   - **Description**: handles attack rewards, round rewards and passes the suitable rewardw, if any, to the player.
   - **Parameters**:
     - `player`: the player whom is given rewards, if any.
     - `reward`: the reward of the current attack/round.



23. `boolean useTimeWarp`  
   - **Description**: uses the TimeWarp powerup for the specified player.
   - **Parameters**:
     - `player`: the player whose TimeWarp is used.
   - **Return Type**: `boolean`
      - `true` TimeWarp was successful.
      - `false` TimeWarp failed.


24. `void useArcaneBoost`  
   - **Description**: uses the ArcaneBoost powerup for the specified player.
   - **Parameters**:
     - `player`: the player whose ArcaneBoost is used.


25. `int InputDragonNumber`  
   - **Description**: requests from the player the number of the dragon chosen to be attacked.
   - **Parameters**:
     - `player`: the player set to attack the dragon.
     - `red`: the red die carrying the attack value.
   - **Return Type**: `int`
     - the number of the dragon.


26. `int InputAttackValue`  
   - **Description**: requests from the player a chosen attack value on the realm specified by a bonus.
   - **Parameters**:
     - `color`: color of the specified realm.
     - `player`: the player set to attack the specified realm.
   - **Return Type**: `int`
     - returns the chosen attack value.


27. `boolean isValidNumber`  
   - **Description**: checks if the input from the user is valid (a number and not another type of a string).
   - **Parameters**:
     - `input`: the string input from the user.
   - **Return Type**: `boolean`
      - `true` input is valid.
      - `false` input is invalid.


28. `RealmColor InputAttackColor`  
   - **Description**: requests the color of the realm that the user wishes to attack using the white dice.
   - **Parameters**:
     - `dice`: passes the white die: carrying its value.
     - `player`: the player attacking with the white die.
   - **Return Type**: `RealmColor`
     - the color of the chosen realm.


29. `boolean askForArcane`  
   - **Description**: prompts the user to pick whether to use an ArcaneBoost or not.
   - **Parameters**:
     - `player`: the player who would either use or not use the ArcaneBoost.
   - **Return Type**: `boolean`
      - `true` player wants to use ArcaneBoost.
      - `false` player doesn't want to use ArcaneBoost.


30. `boolean askForTime`  
   - **Description**: prompts the user to pick whether to use an TimeWarp or not.
   - **Parameters**:
     - `player`: the player who would either use or not use the TimeWarp.
   - **Return Type**: `boolean`
      - `true` player wants to use TimeWarp.
      - `false` player doesn't want to use TimeWarp.


31. `Dice askForDiceSelection`  
   - **Description**: prompts the user to select dice for attacking.
   - **Parameters**:
     - `player`: the player currently attacking.
     - `arcaneSelection`: used to mark the die as used by ArcaneBoost to avoid reusigng it accidentally.
   - **Return Type**: `Dice`
     - the die selected by the user.


32. `int checkScanner`  
   - **Description**: checks if the input value represents one of the indices in the list of dice
   - **Parameters**:
     - `availableDices`: ArrayList of ther available dice
   - **Return Type**: `int`
     - index of the selected die in the ArrayList.


33. `void gameLoop`  
   - **Description**: the loop that handles the flow of all gameplay (rounds, turns, rewards, switching of players, w tarabeezat billiardo w ping), executes inside `startGame()`.


34. `void halfRound`  
   - **Description**: runs both players through a half round where one of them is active and the other is passive.
   - **Parameters**:
     - `roundRewardUse`: indicates whether the reward has been used in this half of the current round or not.
     - `roundReward`: passes the reward of the current round, if any.


35. `void activeTurn`  
   - **Description**: handles the turn(s) of the active player (rolling, TimeWarps, attacking).


36. `void forgottenTurn`  
   - **Description**: handles the turn(s) of the passive player.


37. `void turnArcane`  
   - **Description**: handles the use of ArcaneBoost for a player.
   - **Parameters**:
     - `player`: the player who's using ArcaneBoost.


38. `Move WhiteDieHandler`  
   - **Description**: handles the white die and its custom attack.
   - **Parameters**:
     - `whiteDie`: the white die carrying the attack value.
     - `player`: the player doing the attack.
   - **Return Type**: `Move`
     - move on the chosen realm with a newly created die of suitable color.
    

39. `RealmColor InputAttackColor`  
   - **Description**: requests the attack color that the player wishes to attack using the Essence Bonus reward.
   - **Parameters**:
     - `player`: the player doing the attack.
   - **Return Type**: `RealmColor`
     - the color of the realm to be attacked

40. `boolean canUseArcane`
   - **Description**: checks if ArcaneBoost-valid dice are available.
   - **Parameters**:
     - `player`: the player whose dice are checked.
   - **Return Type**: `boolean`
      - `true` available.
      - `false` unavailable.
   


### `GameBoard.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the GameBoard object of the game that carries both players and the 6 dice: imitates a real board.

#### Methods: 
1. `Player getPlayer1`  
   - **Description**: Get player 1 
   - **Return Type**: `Player`
     - returns player1's Player object


2. `Player getPlayer2`  
   - **Description**: Get player 2
   - **Return Type**: `Player`
     - return player2's Player object
    
3. `Dice[] getDice`  
   - **Description**: get all the dice 
   - **Return Type**:Array of `Dice`
     - Array of all the dices

4. `Dice getDice`  
   - **Description**: gets a specific die out of the 6 current dice.
   - **Parameters**:
     - `value`: index of the die (using ordinals).
   - **Return Type**: `Dice`
     - the requested die.


5. `GameStatus getStatus`  
   - **Description**: returns the GameStatus object.
   - **Return Type**: `GameStatus`
     - GameStatus of the current game.


6. `void resetUseByBoost`  
   - **Description**: set every dice isUsedByBoost value to false.


7. `void resetAllDices`  
   - **Description**: set every dice to Active and isUsedByBoost value to false.


### `GameState.java` Enum

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class represents all possible states of the game: IN_PROGRESS, PLAYER_1_WINS, PLAYER_2_WINS and DRAW;


### `GameStatus.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the status of the game: tracks round count, active turn count, passive turn count and rewards.

#### Methods: 
1. `boolean updateTurn`  
   - **Description**: Increment Turn 
   - **Return Type**: `boolean`
      - `true` If Increment was successful, 
      - `false` otherwise.


2. `void resetTurn`  
   - **Description**: Set TurnNumber back to one.


3. `boolean updateRound`  
   - **Description**: Increment Round Number
   - **Return Type**: `boolean`
      - `true` If Increment was successful
      - `false` If Increment was failure when excced the maxRoundNumber.


4. `int getroundNumber`  
   - **Description**: Get the current round Number
   - **Return Type**: `int`
     - The round number 


5. `int getturnNumber`  
   - **Description**: Get the current turn number in the round
   - **Return Type**: `int`
     - the turn number 


6. `void setGameState`  
   - **Description**: Change GameState 
   - **Parameters**:
     - `state`: The new desired GameState


7. `int getMaxRoundNumber`  
   - **Description**: Return the maximum number of rounds
   - **Return Type**: `int`
     -  Return the maximum round number


8. `int getMaxTurnNumberActive`  
   - **Description**: Return the maximum number of Active turns that can be played
   - **Return Type**: `int`
     - Return the maximum Active turn number


9. `int getMaxTurnNumberPassive`  
   - **Description**:  Return the maximum number of Passive turns that can be played
   - **Return Type**: `int`
     - Return the maximum Passive turn number


10. `RewardType[] getRewards`  
   - **Description**: returns the rewards of each round in an array.
   - **Return Type**: Array of `RewardType`
     - array of round rewards.


11. `int getRoundNumber`  
   - **Description**: returns the number of the current round running in the game
   - **Return Type**: `int`
     - current round number.


12. `GameState getGameState`  
   - **Description**: returns the current state of the game.
   - **Return Type**: `GameState`
     - current state of the game.


13. `int getTurnNumber`  
   - **Description**: returns the number of the current turn running in the game
   - **Return Type**: `int`
     - current turn number.


14. `RewardType getRoundReward`  
   - **Description**: returns the reward of the current round.
   - **Return Type**: `RewardType`
     - current round reward.


### `Move.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the Move object that carries dice, players and their attacks.

#### Methods: 
1. `Dice getDice`  
   - **Description**: get the dice that will be used in move
   - **Return Type**: `Dice`
     - Dice object of the dice used in to perform a move


2. `Creature getCreature`  
   - **Description**: get the creature that move will be performed on
   - **Return Type**: `Creature`
     - Creature object of the creature being attacked


3. `int compareTo`  
   - **Description**: used to sort two Move objects
   - **Parameters**:
     - `o`: Move being compared to 
   - **Return Type**: `int`
     - `1` if the Move compareTo is called on is consdiered to have higher value,
     - `0`   if the Move compareTo is called on is consdiered to have equale value,
     - `-1`  if the Move compareTo is called on  is consdiered to have lower value.


4. `String toString`  
   - **Description**: repreasnt a Move realvante info object as a String
   - **Return Type**: `String`
     - String reprenattion of Move object


### `Player.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents the player, their stats, and their rewards.

#### Methods: 
1. `boolean switchState`  
   - **Description**: switch stat of player from Active to passive or the oppt
   - **Return Type**: `boolean`
      - `true` if switch is successful 
      - `false` if switch failed.


2. `boolean addTimeWarp`  
   - **Description**: enable an avalible TimeWarp
   - **Return Type**: `boolean`
      - `true` if increase was successful 
      - `false` in increase was failure .


3. `boolean addArcaneBoost`  
   - **Description**: enable an avalible ArcaneBoost
   - **Return Type**: `boolean`
      - `true` if increase was successful ,
      - `false` if increase was failure .


4. `boolean useArcaneBoost`  
   - **Description**: use an avalibe arcane boost
   - **Return Type**: `boolean`
      - `true` is use was successful 
      - `false` if use failed or don't have avalibe ArcaneBoosts.


5. `boolean useTimeWarp`  
   - **Description**: use an avalibe TimeWarp
   - **Return Type**: `boolean`
      - `true` if use was successful 
      - `false` if use failed or don't have avalibe TimeWarps.


6. `TimeWarp[] getTimeWarp`  
   - **Description**: get all TimeWarps
   - **Return Type**: Array of `TimeWarp`
     - Array of time Warps


7. `ArcaneBoost[] getArcaneBoost`  
   - **Description**: get all ArcaneBoost
   - **Return Type**: Array of `ArcaneBoost`
     - Array of ArcaneBoost


8. `PlayerStatus getPlayerStatus`  
   - **Description**: the current player status 
   - **Return Type**: `PlayerStatus`
     - return either Active or Passive 


9. `ScoreSheet getScoreSheet`  
   - **Description**: The unique player's ScoreSheet
   - **Return Type**: `ScoreSheet`
     - ScoreSheet object


10. `GameScore getGameScore`  
   - **Description**: get the current GameScore
   - **Return Type**: `GameScore`
     - return the current GameScore object


11. `boolean hasArcaneBoost`  
   - **Description**: check if player has any  remaining ArcaneBoosts 
   - **Return Type**: `boolean`
      - `true` if player has at least one Arcane boost
      - `false` has no available ArcaneBoost.


12. `boolean hasTimeWarps`  
   - **Description**: check if player has any remaining TimeWarps
   - **Return Type**: `boolean`
      - `true` if player has at least one TimeWarp
      - `false` has no available TimeWarps.


13. `String getName`  
   - **Description**: the name the player chose 
   - **Return Type**: `String`
     - the string of the name player chose 


14. `void setName`  
   - **Description**: setting name of of one choosen by the player 
   - **Parameters**:
     - `name`: string of the name choosen by the player 


15. `int getTimeWarpsCount`  
   - **Description**: the number of enabled Time Warps
   - **Return Type**: `int`
     - number of Time warps


16. `int getArcaneBoostCount`  
   - **Description**: the number of enabled arcane boost 
   - **Return Type**: `int`
     - number of ArcaneBoost


### `PlayerStatus.java` Enum

- **Package**: `game.engine`
- **Type**: Enum Class
- **Description**: This class represents the current state of the player: ACTIVE or PASSIVE.


### `ScoreSheet.java` class

- **Package**: `game.engine`
- **Type**: Class
- **Description**: This class represents a player's scoresheet in the game: carries all attacks (past and future attacks), all scores (current and potential scores) and realm rewards.

#### Methods: 
1. `String toString`  
   - **Description**: displays the scoresheet in a tabular format, utilizes the `creature.toString()` method.
   - **Return Type**: `String`
     - tabular form of all relevant data.


2. `Creature getCreatureByRealm`  
   - **Description**: returns a creature with all its data, specified by inputting its color.
   - **Parameters**:
     - `color`: color of the requested realm.
   - **Return Type**: `Creature`
     - the creature that has been called.

## `game.dice` package

### `ArcanePrism.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents ArcanePrism dice

#### Methods: 
1. `void setAttackColor`  
   - **Description**: set a colour of ArcanePrism
   - **Parameters**:
     - `attackColor`: the new desired colour of ArcanePrism


2. `String getTextColor`  
   - **Description**: get the Unicode for the colour white
   - **Return Type**: `String`
     - string of the Unicode


### `BlueDice.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the Blue die

#### Methods: 
1. `String getTextColor`  
   - **Description**:  get the Unicode for the colour blue 
   - **Return Type**: `String`
     - string of the Unicode


### `Dice.java` class

- **Package**: `game.dice`
- **Type**: Abstract Class
- **Description**: This Abstract class represents die

#### Methods: 
1. `void roll`  
   - **Description**: Set vaule to a new random number from 1 to 6 


2. `int getValue`  
   - **Description**: Return the current value of the die
   - **Return Type**: `int`
     - the current vaile 


3. `DiceLocation getDiceLocation`  
   - **Description**: The cuurent location of the die
   - **Return Type**: `DiceLocation`
     - the enum either ACTIVE, USED or FORGOTTEN;


4. `void updateDiceLocation`  
   - **Description**: Change die location
   - **Parameters**:
     - `location`: location the new desired location of the die


5. `boolean isUsedByBoost`  
   - **Description**: Check if the die was used by Boost
   - **Return Type**: `boolean`
      - `true` if the die is used by boost,
      - `false` otherwise.


6. `void useByBoost`  
   - **Description**: Mark the die when used by Boost


7. `void resetUseByBoost`  
   - **Description**: Marks the die as no longer being used by boost.


8. `boolean setValue`  
   - **Description**: Set value of the die
   - **Parameters**:
     - `value`: The new value of die 
   - **Return Type**: `boolean`
      - `true` if assignment is successful
      - `false` otherwise.


9. `RealmColor getRealm`  
   - **Description**: get the associated realm colour of the die
   - **Return Type**: `RealmColor`
     - the RealmColour Enum of the die 


10. `String getTextColor`  
   - **Description**:  get the Unicode colour of the die 
   - **Return Type**: `String`
     - string of the Unicode


11. `String toString`  
   - **Description**: string representation of the die, including its color and value.
   - **Return Type**: `String`
     - String representation of the die.


### `DiceLocation.java` Enum

- **Package**: `game.dice`
- **Type**: Enum
- **Description**: This enumeration represents the three possible locations for any die: ACTIVE,USED,FORGOTTEN;

#### Methods: 
### `GreenDice.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the Green die

#### Methods: 
1. `String getTextColor`  
   - **Description**: get the Unicode value for the colour green
   - **Return Type**: `String`
     - string of the Unicode


### `MagentaDice.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents the Magenta die

#### Methods: 
1. `String getTextColor`  
   - **Description**:  get the Unicode value for the colour Magenta
   - **Return Type**: `String`
     - string of the Unicode


### `RedDice.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents red die

#### Methods: 
1. `void selectsDragon`  
   - **Description**: sets the DragonNumber creature
   - **Parameters**:
     - `i`: the number of the dragon between 1 to 4 


2. `DragonNumber getDragonNumber`  
   - **Description**: return the Dragon number
   - **Return Type**: `DragonNumber`
     - return the Creature DragonNumber 


3. `String getTextColor`  
   - **Description**:  get the Unicode value for the colour Red
   - **Return Type**: `String`
     - string of the Unicode


### `YellowDice.java` class

- **Package**: `game.dice`
- **Type**: Class
- **Description**: This class represents Yellow die

#### Methods: 
1. `String getTextColor`  
   - **Description**:  get the Unicode value for the colour yellow
   - **Return Type**: `String`
     - string of the Unicode

## `game.creatures` package

### `Creature.java` class

- **Package**: `game.creatures`
- **Type**: Abstract Class
- **Description**: This class represents the building base for each of the 5 creatures on a broad scale.

#### Methods: 
1. `RewardType[] attack`
   - **Description**: This method e an attack towards a chosen creature.
   - **Parameters**:
     - `dice`: The die chosen by the player to attack the creature
   - **Return Type**: Array of `RewardType`
     - Returns an array of given reward(s) (or none) to manage reward handling.


2. `boolean isAttackable`
   - **Description**: This method indicates whether the creature is available to attack or not.
   - **Return Type**: `boolean`
      - `true` The creature is available for attacking.
      - `false` No remaining attacks on the creature ("realm completed").


3. `void updateScore`  
   - **Description**: This method updates the current score in a realm.
   - **Parameters**:
     - `value`: The gained score post-attack.


4. `int getScore`  
   - **Description**: This method returns the current score in a realm.
   - **Return Type**: `int`
     - The current score.


5. `String toString`  
   - **Description**: This method converts all relevant data of a creature to be shown in scoresheet as a table.
   - **Return Type**: `String`
     - A table of relevant data in a creature.


6. `Move[] getPossibleMovesForADie`  
   - **Description**: This methods checks for all possible moves by a given die on a creature.
   - **Parameters**:
     - `dice`: The die used in checking.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on a creature.


7. `Move[] getPossibleMoves`  
   - **Description**: This method checks for all possible moves on a creature.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) on a creature.


### `Dragon.java` class

- **Package**: `game.creatures`
- **Type**: Class
- **Description**: This class represents the DRAGONS in the Red Realm.

#### Methods: 
1. `void setRewards`  
   - **Description**: Helper method to set the rewards from the config file.


2. `void setScores`  
   - **Description**: Helper method to set the scores from the config file.


3. `void setDragonGrid`  
   - **Description**: Helper method to set the attack values from the config file.


4. `RewardType[] attack`  
   - **Description**: This method does the attack action in the Red Realm and returns the acquired rewards , if any, in a RewardType array. 
   - **Parameters**:
     - `dice`: the die used to attack.
   - **Return Type**: Array of `RewardType`
     - returns rewards of attack, if any.
     


5. `boolean isDragon1Dead`  
   - **Description**: Helper method to check if Dragon 1 is dead
   - **Return Type**: `boolean`
      - `true` dead.
      - `false` not dead.


6. `boolean isDragon2Dead`  
   - **Description**: Helper method to check if Dragon 2 is dead
   - **Return Type**: `boolean`
      - `true` dead
      - `false` not dead.


7. `boolean isDragon3Dead`  
   - **Description**: Helper method to check if Dragon 3 is dead
   - **Return Type**: `boolean`
      - `true` dead
      - `false` not dead.


8. `boolean isDragon4Dead`  
   - **Description**: Helper method to check if Dragon 4 is dead
   - **Return Type**: `boolean`
      - `true` dead
      - `false` not dead.


9. `boolean checkHeadBonus`  
   - **Description**: Helper method to check if the Heads of all dragons are cut off.
   - **Return Type**: `boolean`
      - `true` all heads are cut off.
      - `false` not all heads are cut off.


10. `boolean checkWingBonus`  
   - **Description**: Helper method to check if the Wings of all dragons are cut off.
   - **Return Type**: `boolean`
      - `true` all wings are cut off
      - `false` not all wings are cut off.


11. `boolean checkTailBonus`  
   - **Description**: Helper method to check if the Tails of all dragons are cut off.
   - **Return Type**: `boolean`
      - `true` all tails are cut off.
      - `false` not all tails are cut off.


12. `boolean checkHeartBonus`  
   - **Description**: Helper method to check if the Hearts of all dragons are cut off.
   - **Return Type**: `boolean`
      - `true` all hearts are cut off
      - `false` not all hearts are cut off.


13. `boolean checkDiagonalBonus`  
   - **Description**: Helper method to check if the parts at the diagonal parts are cut off.
   - **Return Type**: `boolean`
      - `true` diagonal is cut off.
      - `false` not all of the diagonal is cut off.


14. `void updateScore`  
   - **Description**: Updates the score of the red realm
   - **Parameters**:
     - `value`: new score value.


15. `String dts`  
   - **Description**: Helper method to print the organ value of the dragons with the correct format.
   - **Parameters**:
     - `i`: row index
     - `j`: column index
   - **Return Type**: `String`



16. `String sts`  
   - **Description**: Helper method to print the score value of the dragons with the correct format.
   - **Parameters**:
     - `i`: score index.
   - **Return Type**: `String`
   


17. `String rts`  
   - **Description**: Helper method to print the rewards of the dragons with the correct format.
   - **Parameters**:
     - `i`: reward index
   - **Return Type**: `String`
     


18. `String toString`  
   - **Description**: Overridden method to format the red realm sheet
   - **Return Type**: `String`
     


19. `Move[] getPossibleMovesForADie`  
   - **Description**: Gets all possible moves for a specific dragon. 
   - **Parameters**:
     - `dice`: the die used to attack.
   - **Return Type**: Array of `Move`
   


20. `Move[] getPossibleMoves`  
   - **Description**: Gets all possible moves for the whole realm.
   - **Return Type**: Array of `Move`
    


### `DragonNumber.java` Enum

- **Package**: `game.creatures`
- **Type**: Enum
- **Description**: This enum represents the number of the dragons in the Red realm.

#### Methods: 
1. `DragonNumber getDragon`  
   - **Description**: Gets the dragon which corresponds to specific index.
   - **Parameters**:
     - `i`: The specific index.
   - **Return Type**: `DragonNumber`
   


2. `String getName`  
   - **Description**: Returns the name of each dragon.
   - **Return Type**: `String`


### `Gaia.java` class

- **Package**: `game.creatures`
- **Type**: Class
- **Description**: This class represents Gaia creature and its realm

#### Methods: 
1. `String X_space`  
   - **Description**: helper method to return x as a two-character long string to be suitable for printing.
   - **Parameters**:
     - `inp`: Reward type
   - **Return Type**: `String`
     - either X or X_


2. `String toString`  
   - **Description**: This method converts all relevant data of a creature to be shown in scoresheet as a table.
   - **Return Type**: `String`
     - String of the current board section of Gaia 


3. `RewardType[] attack`  
   - **Description**: to do a move
   - **Parameters**:
     - `dice`: dice for specific value 
   - **Return Type**: Array of `RewardType`
     - an array of rewards acquired after attack


4. `boolean rowEmpty`  
   - **Description**: helper method to check if row after attack is empty
   - **Parameters**:
     - `value`: number of row
   - **Return Type**: `boolean`
      - `true` if row is all attacked,
      - `false` if row still have unattached cells.


5. `boolean coloumEmpty`  
   - **Description**: helper method to check if a coloum after attack is emty
   - **Parameters**:
     - `value`: number of coloum
   - **Return Type**: `boolean`
      - `true` if coloum is all attacked 
      - `false` if coloum still unattacked.


6. `void updateScore`  
   - **Description**: This method updates the current score in a realm.
   - **Parameters**:
     - `value`: The new score post-attack.


7. `Move[] getPossibleMovesForADie`  
   - **Description**: This methods checks for all possible moves by a given die on a creature
   - **Parameters**:
     - `dice`: The die used in checking
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on a creature.


8. `Move[] getPossibleMoves`  
   - **Description**: This method checks for all possible moves on a creature.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on a creature.



### `Hydra.java` class

- **Package**: `game.creatures`
- **Type**: Class
- **Description**: This class represents the Hydra creature and its realm.

#### Methods: 
1. `RewardType[] attack`  
   - **Description**: This method e an attack towards the Hydra.
   - **Parameters**:
     - `dice`: The die chosen by the player to attack the Hydra
   - **Return Type**: Array of `RewardType`
     - Returns an array of given reward(s) (or none) to manage reward handling.


2. `void updateScore`  
   - **Description**: This method updates the current score in the blue realm.
   - **Parameters**:
     - `value`: The gained score post-attack.


3. `String toString`  
   - **Description**: This method converts all relevant data of the Hydra (Scores, Conditions, Rewards) to be shown in scoresheet as a table
   - **Return Type**: `String`
     - A table of relevant data in the blue realm.


4. `Move[] getPossibleMovesForADie`  
   - **Description**: This methods checks for all possible moves by a given die on the blue realm.
   - **Parameters**:
     - `dice`: The die used in checking
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on the blue realm.


5. `Move[] getPossibleMoves`  
   - **Description**: This method checks for all possible moves on the blue realm.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) on the blue realm.


### `Lion.java` class

- **Package**: `game.creatures`
- **Type**: Class
- **Description**: This class represents the Lion creature and its realm.

#### Methods: 
1. `RewardType[] attack`  
   - **Description**: This method e an attack towards the Lion.
   - **Parameters**:
     - `dice`: The die chosen by the player to attack the Lion.
   - **Return Type**: Array of `RewardType`
     - Returns an array of given reward(s) (or none) to manage reward handling.


2. `void updateScore`  
   - **Description**: This method updates the current score in the yellow realm.
   - **Parameters**:
     - `value`: The gained score post-attack.


3. `String toString`  
   - **Description**: This method converts all relevant data of the Lion (Scores, Rewards) to be shown in scoresheet as a table.
   - **Return Type**: `String`
     - A table of relevant data in the yellow realm.


4. `Move[] getPossibleMovesForADie`  
   - **Description**: This methods checks for all possible moves by a given die on the yellow realm.
   - **Parameters**:
     - `dice`: The die used in checking
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on the yellow realm.


5. `Move[] getPossibleMoves`  
   - **Description**: This method checks for all possible moves on the yellow realm.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) on the yellow realm.



### `Phoenix.java` class

- **Package**: `game.creatures`
- **Type**: Class
- **Description**: This class represents the Phoenix creature and its realm.

#### Methods: 
1. `RewardType[] attack`  
   - **Description**: This method executes an attack towards the Phoenix.
   - **Parameters**:
     - `dice`: The die chosen by the player to attack the Phoenix.
   - **Return Type**: Array of `RewardType`
     - Returns an array of given reward(s) (or none) to manage reward handling.


2. `void updateScore`  
   - **Description**: This method updates the current score in the magenta realm.
   - **Parameters**:
     - `value`: The gained score post-attack.


3. `String toString`  
   - **Description**: This method converts all relevant data of the Phoenix (Scores, Condition, Rewards) to be shown in scoresheet as a table.
   - **Return Type**: `String`
     - A table of relevant data in the magenta realm.


4. `Move[] getPossibleMovesForADie`  
   - **Description**: This methods checks for all possible moves by a given die on the magenta realm.
   - **Parameters**:
     - `dice`: The die used in checking.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) by a given die on the magenta realm.


5. `Move[] getPossibleMoves`  
   - **Description**: This method checks for all possible moves on the magenta realm.
   - **Return Type**: Array of `Move`
     - The possible move(s) (or none) on the magenta realm.

### `RealmColor.java` Enum

- **Package**: `game.creatures`
- **Type**: Enum 
- **Description**: This class represents the 5 colors, their corresponding creatures and text colors.

#### Methods: 
1. `String getTextColor`
   - **Description**: returns the text color of the RealmColor.
   - **Return Type**: `String`
     - String of the text color.

2. `String getName`
   - **Description**: returns the name of the RealmColor.
   - **Return Type**: `String`
     - String of the name.

## `game.collectibles` package

### `ArcaneBoost.java` class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents the ArcaneBoost object.

### `PowerUp.java` class

- **Package**: `game.collectibles`
- **Type**: Abstract Class
- **Description**: acts as a building base for PowerUps (TimeWarp and ArcaneBoost).

#### Methods: 
1. `boolean Enable`  
   - **Description**: marks PowerUp as ENABLED;
   - **Return Type**: `boolean`
      - `true` if enabling was successful.
      - `false` if enabling failed.


2. `boolean use`  
   - **Description**: marks PowerUp as USED;
   - **Return Type**: `boolean`
      - `true` if use was successful.
      - `false` if use failed.


3. `PowerUpState getPowerUpState`  
   - **Description**: Return the curernt state of the PowerUp.
   - **Return Type**: `PowerUpState`
     - PowerUpState Enum either DISABLED, ENABLED or USED;


4. `void setPowerUpState`  
   - **Description**: Change the state of the PowerUp.
   - **Parameters**:
     - `state`: The new desired PowerUpState.


### `PowerUpState.java` Enum

- **Package**: `game.collectibles`
- **Type**: Enum
- **Description**: This class represents the three possible states of a Powerup: DISABLED, ENABLED and USED.


### `RewardType.java` Enum

- **Package**: `game.collectibles`
- **Type**: Enum
- **Description**: This class represents all possible Rewards as RewardTypes in a shorter form: RB, GB, BB, MB, YB, EB, AB, TW, EC, X and EMPTY.

#### Methods: 
1. `RewardType getReward`  
   - **Description**: Return RewardType corrosponding to the name
   - **Parameters**:
     - `name`: the string representation of the Reward.
   - **Return Type**: `RewardType`
     - RewardType Enum of the corrosponding String.


### `TimeWarp.java` class

- **Package**: `game.collectibles`
- **Type**: Class
- **Description**: This class represents the TimeWarp PowerUp.

## `game.gui` package

### `DiceRealms.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the application of the game.

#### Methods:
1. `void start`
    - **Description**: this method launches the game's program.
    - **Parameters**:
        - `primaryStage`: the stage object that showcases the whole program.


2. `void terminateGame`
    - **Description**: this method manages the termination of the game through the *Terminate* button,
      or through ALT+F4, and shows an exit message in the terminal.
    - **Parameters**:
        - `stage`: the program.


### `DragonGUI.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class handles the Dragon creature and its realm in the GUI.

#### Methods:
1. `void updateDragon`
    - **Description**: this method updates the Dragons' GUI.
    - **Parameters**:
        - `player`: player who has most recently attacked.
        - `playSceneController`: the controller of the play scene.
        - `player1Dragon`: the scoresheet of player 1's dragon
        - `player2Dragon`: the scoresheet of player 2's dragon.


2. `void dragonMove`
    - **Description**: this method executes an attack towards a dragon.
    - **Parameters**:
        - `event`: the clicking event of a button in the dragon grid.
        - `playSceneController`: the controller of the play scene.
        - `player1Dragon`: the scoresheet of player 1's dragon
        - `player2Dragon`: the scoresheet of player 2's dragon.
        - `currentDice`: the current die used to attack.
        - `currPlayer`: player who has most recently attacked.


### `GaiaGUI.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class handles the Gaia creature and its realm in the GUI.

#### Methods:
1. `void updateGaia`
    - **Description**: this method updates the Gaias' GUI.
    - **Parameters**:
        - `playSceneController`: the controller of the play scene.
        - `allGrids`: all grids of creatures.
        - `player`: player who has most recently attacked.
        - `gridGaia2p`: the scoresheet of player 1's gaia.
        - `gridGaia1p`: the scoresheet of player 2's gaia.


2. `void helperGaiamove`
    - **Description**: a helper method in updating th Gaia GUI.
    - **Parameters**:
        - `player`: player who has most recently attacked.
        - `event`: the clicking event of a button in the gaia grid.
        - `currentDiceSelection`: the current die used to attack.
        - `playSceneController`: the controller of the play scene.
        - `allGrids`: all grids of creatures.


### `GameModeController.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the controller of the Game Mode scene.

#### Methods:
1. `boolean getIsPVP`
    - **Description**: this method checks whether the user picked PVP or PVE.
    - **Return Type**: `boolean`
        - `true` chosen mode is PVP.
        - `false` chosen mode is PVE.


2. `void selectMode`
    - **Description**: this method handles the picking of the Game Mode.
    - **Parameters**:
        - `event`: the clicking event of a Game Mode button.


### `HydraGUI.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class handles the Hydra creature and its realm in the GUI.

#### Methods:
1. `void attackHydra`
    - **Description**: this method handles attacks on the hydras.
    - **Parameters**:
        - `event`: the clicking event of a button in the hydra grid.
        - `currentDiceSelection`: the current die used to attack.
        - `playSceneController`: the controller of the play scene.
        - `currentPlayer`: player who has most recently attacked.
        - `allGrids`: all grids of creatures.


2. `void updateHydra`
    - **Description**: this method updates the Hydras' GUI.
    - **Parameters**:
        - `currentDiceSelection`: the current die used to attack.
        - `playSceneController`: the controller of the play scene.
        - `allGrids`: all grids of creatures.
        - `currentPlayer`: player who has most recently attacked.


### `LionGUI.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class handles the Lion creature and its realm in the GUI.

#### Methods:
1. `void updateLion`
    - **Description**: this method updates the Lions' GUI.
    - **Parameters**:
        - `player`: player who has most recently attacked.
        - `playSceneController`: the controller of the play scene.
        - `player1Lion`: the scoresheet of player 1's hydra.
        - `player2Lion`: the scoresheet of player 2's hydra.


2. `void player1LionMove`
    - **Description**: this method handles attacks on player 1's lion.
    - **Parameters**:
        - `event`: the clicking event of a button in the lion grid.
        - `playSceneController`: the controller of the play scene.
        - `player1Lion`: the scoresheet of player 1's Lion.
        - `player2Lion`: the scoresheet of player 2's Lion.
        - `currentDice`: the current die used to attack.


3. `void player2LionMove`
    - **Description**: this method handles attacks on player 2's lion.
    - **Parameters**:
        - `event`: the clicking event of a button in the lion grid.
        - `playSceneController`: the controller of the play scene.
        - `player1Lion`: the scoresheet of player 1's Lion.
        - `player2Lion`: the scoresheet of player 2's Lion.
        - `currentDice`: the current die used to attack.


### `MainSceneController.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the controller of the Main Scene.

#### Methods:
1. `void initialize`
    - **Description**: this method initializes the view of the Main Scene.


2. `void stopMusic`
    - **Description**: this method stops the music in the main scene.


3. `void show`
    - **Description**: this method shows the main scene.
    - **Parameters**:
        - `event`: an event (allahu a3lam).


2. `void terminateGame`
    - **Description**: this method manages the termination of the game through the *Exit* button.
    - **Parameters**:
        - `event`: the clicking of button *Exit*.


5. `void toPlayScene`
    - **Description**: proceeds towards the Play Scene.
    - **Parameters**:
        - `event`: the clicking of button *Play*.


6. `void toSettingScene`
    - **Description**: proceeds towards the Settings Scene.
    - **Parameters**:
        - `event`: the clicking of button *Settings*.


7. `void setMediaPlayer`
    - **Description**: this method handles the background music of the game.
    - **Parameters**:
        - `mediaPlayer`: the media player that carries the music file.


### `PhoenixGUI.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class handles the Phoenix creature and its realm in the GUI.

#### Methods:
1. `void phoenixMove`
    - **Description**: this method handles attacks on the phoenix.
    - **Parameters**:
        - `event`: the *clicking* event of a button in the phoenix grid.
        - `currentDiceSelection`: the current die used to attack.
        - `playSceneController`: the controller of the play scene.
        - `currentPlayer`: player who has most recently attacked.
        - `allGrids`: all grids of creatures.


2. `void updatePhoenix`
    - **Description**: this updates the Phoenix GUI.
    - **Parameters**:
        - `currentDiceSelection`: the current die used to attack.
        - `playSceneController`: the controller of the play scene.
        - `allGrids`: all grids of creatures.
        - `currentPlayer`: player who has most recently attacked.


### `PlayerNamesController.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the controller of the Player Names Scene.

#### Methods:
1. `void setPVP`
    - **Description**: this method ensures the GameMode is set to PVP
    - **Parameters**:
        - `PVP`: PvP game mode.


2. `String getPlayer1Name`
    - **Description**: this method returns the name of player 1.
    - **Return Type**: `String`
        - player 1 name.


3. `String getPlayer2Name`
    - **Description**: this method returns the name of player 2.
    - **Return Type**: `String`
        - player 2 name.


4. `void initialize`
    - **Description**: initializes the Player Names scene.
    - **Parameters**:
        - `url`: needed for the interface.
        - `resourceBundle`: all resources in the Player Names scene.


5. `void enterName`
    - **Description**: prompts the players to enter their names.
    - **Parameters**:
        - `event`: entering of a name in the textbox.


### `PlaySceneController.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the controller of the Play Scene that handles all game functionality.

#### Methods:
1. `void initialize`
    - **Description**: this method initializes all items in the Play Scene: grids, buttons, labels, etc.
    - **Parameters**:
        - `location`: needed for the interface.
        - `resources`: all resources in the Play Scene.


2. `void resetDiceSelection`
    - **Description**: resets all dice to *unselected*.


3. `void disableGrids`
    - **Description**: disables all grids of creatures to disable entries.


4. `void updateDicePositions`
    - **Description**: puts every die in its correct position: ACTIVE, FORGOTTEN or USED.


5. `void updateGaia`
    - **Description**: updates Gaia GUI.
    - **Parameters**:
        - `player`: player who has most recently attacked.


6. `void helperGaiamove`
    - **Description**: helper method to update Gaia.
    - **Parameters**:
        - `player`: player who has most recently attacked.
        - `event`: clicking of a button in Gaia.


7. `void player1GaiaMove`
    - **Description**: this method handles a move on player 1 Gaia.
    - **Parameters**:
        - `event`: the *clicking* event of a button in the Gaia grid.


8. `void player2GaiaMove`
    - **Description**: this method handles a move on player 2 Gaia.
    - **Parameters**:
        - `event`: the *clicking* event of a button in the Gaia grid.


9. `void player1LionMove`
    - **Description**: this method handles a move on player 1 Lion.
    - **Parameters**:
        - `event`: the *clicking* event of a button in the lion grid.


10. `void player2LionMove`
- **Description**: this method handles a move on player 2 Lion.
- **Parameters**:
    - `event`: the *clicking* event of a button in the Lion grid.


11. `void player1DragonMove`
- **Description**: this method handles a move on player 1 Dragon.
- **Parameters**:
    - `event`: the *clicking* event of a button in the Dragon grid.


12. `void player2DragonMove`
- **Description**: this method handles a move on player 2 Dragon.
- **Parameters**:
    - `event`: the *clicking* event of a button in the Dragon grid.


13. `void attackHydra`
- **Description**: this method handles a move on the Hydra.
- **Parameters**:
    - `event`: the *clicking* event of a button in the Hydra grid.


14. `void phoenixMove`
- **Description**: this method handles a move on the Phoenix.
- **Parameters**:
    - `event`: the *clicking* event of a button in the Phoenix grid.


15. `void updatePhoenix`
- **Description**: updates Phoenix GUI post-attack.
- **Parameters**:
    - `currentPlayer`: player who has most recently attacked.


16. `void nextTurn`
- **Description**: this method proceeds to the next turn.


17. `void endGame`
- **Description**: this method ends the game.


18. `void disableActiveDices`
- **Description**: this method disables all active dice (buttons) so that the user is forbidden from entering improper inputs.


19. `void activateActiveDices`
- **Description**: this method enables all active dice (buttons).


20. `void activateForgottenDices`
- **Description**: this method enables all forgotten dice (buttons).


21. `void disableForgottenDices`
- **Description**: this method disables all forgotten dice (buttons).


22. `void resetGridColor`
- **Description**: this method is unused and will be deleted.


23. `void disableAllDices`
- **Description**: this method disables all dice (buttons).


24. `void passiveTurn`
- **Description**: executes the passive turn sequence.


25. `void updateHydra`
- **Description**: this method updates the Hydra GUI post-attack.
- **Parameters**:
    - `player`: player who has most recently attacked.


26. `void setNamePlayer1`
- **Description**: this method sets the label that shows player 1 name.
- **Parameters**:
    - `player1Name`: player 1 name.


27`void setNamePlayer2`
- **Description**: this method sets the label that shows player 2 name.
- **Parameters**:
- `player2Name`: player 2 name.


28. `void animateDie`
- **Description**: this method animates the rolling of the dice.
- **Parameters**:
    - `timeline`: the timeline that carries the image sequence of rolling.
    - `die`: the die to be rolled.


29. `void reverseAnimate`
- **Description**: this method animates the dice in reverse to return them to their idle frame.
- **Parameters**:
    - `timeline`: the timeline that carries the image sequence of rolling.
    - `die`: the die to be rolled.


30. `Dice[] rollDice`
- **Description**: this method handles the logical rolling of the dice.
- **Return Type**: Array of `Dice`
    - an array of the rolled dice.


31. `void run`
- **Description**: this method is just a lambda method.


32. `boolean timeWarp`
- **Description**: this method handles the function of Time Warp reward.
- **Return Type**: `boolean`
    - `true` successfully ran Time Warp.
    - `false` failed to run Time Warp.


33. `boolean useTimeWarp`
- **Description**: this method handles the use of Time Warp reward.
- **Parameters**:
    - `player`: player who has just used a Time Warp
- **Return Type**: `boolean`
    - `true` successfully used Time Warp.
    - `false` failed to use Time Warp.


34. `boolean askForTimeWarp`
- **Description**: this method prompts the user to use a Time Warp.
- **Return Type**: `boolean`
    - `true` user wants to use Time Warp.
    - `false` user doesn't want to use Time Warp.


35. `void onDiceClick`
- **Description**: this method handles what happens when a die (button) is clicked.
- **Parameters**:
    - `event`: the ***click***.


36. `void showGrid`
- **Description**: this method enables the grid.
- **Parameters**:
    - `d`: the die whose grid is to be enabled.
    - `player`: the player whose grid is to be enabled.


37. `boolean selectDice`
- **Description**: this method marks a die as selected and acts accordingly.
- **Parameters**:
    - `dice`: the die that has been used.
    - `player`: the player who has used the die.
- **Return Type**: `boolean`
    - `true` die selected successfully.
    - `false` failed to select die.


38. `void showPopup`
- **Description**: this method is a lambda method to show a pop-up message.
- **Parameters**:
    - `message`: the pop-up message.


### `SettingsSceneController.java` class

- **Package**: `game.gui`
- **Type**: Class
- **Description**: This class represents the controller of the Settings Scene.

#### Methods:
1. `void setMediaPlayer`
    - **Description**: this method handles the background music of the game.
    - **Parameters**:
        - `mediaPlayer`: the media player that carries the background music of the game.


2. `void toMainScene`
    - **Description**: returns to the main scene.
    - **Parameters**:
        - `event`: the clicking of button *Back*.


3. `void MusicVolumeController`
    - **Description**: the controller of the background music volume.
    - **Parameters**:
        - `event`: alteration of the sound bar in the settings scene.






## `game.exceptions` package

### `DiceRollException.java` class

- **Package**: `game.exceptions`
- **Type**: Class
- **Description**: This class represents an exception that will be thrown if the dice value was not set successfully.

### `InvalidDragonNumber.java` class

- **Package**: `game.exceptions`
- **Type**: Class
- **Description**: This class represents an anxception that will be thrown when a wrong dragon number is passed.


### `InvalidMoveException.java` class

- **Package**: `game.exceptions`
- **Type**: Class
- **Description**: This class represents an an exception that will be thrown when trying to perform an impossible move.

### `InvalidPropertyKey.java` class

- **Package**: `game.exceptions`
- **Type**: Class
- **Description**: This class represents an exception that will be thrown when we fail to access a key that is not in the config file.

### `UnavailablePowerup.java` class

- **Package**: `game.exceptions`
- **Type**: Class
- **Description**: This class represents an ecxeption that occurs when a Powerup is not availale to be used.

## Additional Notes

- Each class is designed to work together seamlessly to provide a robust game experience. The structure allows for expansion and integration of additional features such as network play or AI opponents.
- The `Main.java` serves as the entry point of the application, initializing the game environment and starting the game loop.
- For additional information, please refer to the [Project UML Diagram](/src/main/resources/images/Project-UML-Diagram.png). Further details will not available at this stage.

_NOTE: This package structure and UML diagram provide a comprehensive overview of how the game components interact and are managed within the codebase._
