
The arcaneboost interaction with class dice

We need ElementalCrest as a type because of the config files; however, I can't find a tangible use, soooo I used a counter for
elemental crest in the GameScore class till we think more thoroughly about it

player.getScoreSheet().getCreatureByRealm(Dice dice).attack(Dice dice); ------> does the attack accordingly AND returns RewardType (enum) and the Controller then processes the boost/powerup accordingly

- Change access modifiers after finishing the code
- add exceptions stated in the TemplateSkeleton.md file 
- add custom exceptions or use predefined exceptions 
- handle exceptions accordingly
- implement the powerups and bonuses functionalities
- implement the CLIGameController methods + new methods if needed
- arcane boost creates array of dices with all possible color and call the get possiblemovesforthisdie for each die in the array
- Getpossiblemoves for a die White Die Case: when the passed die is white create an array of 5 dices of all colors and then loop on the array
- isUsedbyBoost needs  to be implemented
- checker for dice value in class dice : dice of value 1000 for example

-Fixed some bugs with the game loop including:
1:not checking if the get input for the dice from the user did not return 
anything aka: null
2:dices were not reset after the first half of the round was finished
-Added attributes to the input methods for dice to know whether the call was due to ArcaneBoost or not
-added arcaneTurn(Player player) that handles use of ArcaneBoost
NOTE: rewards still not usable and we still need to handle White Dice

<<<<<<<<<<<<< Guys >>>>>>>>>>>>>
I don't know why in the test cases he takes only the first possible move from the possible moves array. 
I have spent a lot of time debugging but all methods work properly the problem is that in test cases specially in move number 4 
when he is attaching dragon 1 with value 1.
the getPossibleMoveForADie method works ,and it returns an array of two moves which is correct !
but he instead of checking these two moves he just tests the first one which will do nothing so the test case fails.
<<<<<<<<<<<<< END >>>>>>>>>>>>>

Ok, the problem has been solved.
I changed the order of moves in the move array. 
the elements are now in order according to dragon number.

<<<<<<<<<<<<< another note >>>>>>>>>>>>>
I noticed that there was a big problems in getPossibleMovesForADie(Dice dice) in some classes:
in the testcases he checks if the move array returned has a length of more than 0 and depending on that 
he sends the parameters. In some classes, this method always returns an array of length of 1 but 
the element inside is null !!
So this throws NullPointerException.
But these issues have been handled :)
we only have one final last testcase to go ;) ---> "testGetPossibleMovesForAvailableDice()"

2:dices were not reset after the first half of the round was finshed
-Added attributes to the input methods for dice to know wether the call was due to ArcaneBoost or not
-added arcaneTurn(Player player) that handles use of ArcaneBoost
NOTE: rewards still not usable and we still need to handle White Dice

Remove Location from dice printing and make them Print with colors
Make arcane turn handle if dice already used by boost


<<<<<<<<<<<<< Guys >>>>>>>>>>>>>
Use only sc.nextLine() not even sc.next() for scanner issues.
<<<<<<<<<<<<< END >>>>>>>>>>>>>
