**Name: Mitran Andrei-Gabriel**
**Group: 323CA**

# GwentStone

### Description:

* In order for the game to function, the Game and Player classes were created.
* Players are set up independent of any specific game. While the Player class
possesses fields such as the current deck or the hand, they are only utilized
when a Game instance is created (by the Player variables player1 and player2
from inside aforementioned class).
* The wins are set in the Player instances that are outside the Game instance.
***
* The Setup class handles setting up the independent players and the start of
each game (things such as the shuffle seed, the starting player etc.)
* The CommandHandler class handles all incoming commands by retrieving any
output or error from the Game class (which actually possesses the
functionalities), and writes the output in a JSON format. The wins and total
games played are also handled here.
***
* The Game class handles almost all commands, from proper commands (placeCard)
to debug commands (getPlayerDeck). All checks are done here. When having to
attack or use an ability, the Card subclasses' methods are used.
***
* The Card class is an abstract class, having fields that all types of cards
posses. It is further extended by three more abstract classes: Minion (having
two extra fields: health and attackDamage), Environment and Hero (having one
extra field: health), each one in turn extended by further subclasses. Every
card has an "action" function, that is made to be overridden.
* The Minion class can attack another Minion or a Hero. Some subclasses can
use special abilities on a single Minion. As such, the Minion class overloads
"action" so that it receives only 1 Minion as an argument, this function being
overridden by each subclass so that the ability may be utilized. A Player
spends mana only when placing a Minion on the table. Any usage of the attack
commands or the special ability does not cost mana.
* The Environment class also overloads "action", this time with an ArrayList
of Minions (a row from the table). HeartHound is the exception, as it requires
two ArrayLists. A Player spends mana when using the card's ability.
* The Hero class does the same as the Environment class, but is a lot more
important to the Game class. A Player only has one Hero, and when the Hero's
health reaches 0, that specific game ends. More debug commands may be given.
They are handled by CommandHandler. Mana is spent when using the Hero's
ability.

### Comments:
* I have learned that when starting any coding homework, understanding how the
input is parsed to you is key.
* Overloading abstract methods is something I need to look further into.
