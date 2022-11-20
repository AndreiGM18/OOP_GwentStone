package implementation;

import constants.Constants;

import implementation.card.Card;

import implementation.card.environment.Environment;
import implementation.card.environment.Firestorm;
import implementation.card.environment.HeartHound;

import implementation.card.hero.Hero;
import implementation.card.hero.EmpressThorina;
import implementation.card.hero.LordRoyce;

import implementation.card.minion.Minion;
import implementation.card.minion.Disciple;
import implementation.card.minion.Generic;
import implementation.card.minion.Miraj;
import implementation.card.minion.Tank;
import implementation.card.minion.TheCursedOne;
import implementation.card.minion.TheRipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {
    private final Player player1;
    private final Player player2;
    private long shuffleSeed;
    private int currPlayerIdx;
    private int startingPlayer;
    private final ArrayList<ArrayList<Minion>> table;

    private int roundCount = 1;

    /*
    In the beginning, nextRound is set to true, so that when the first turn is over
    it becomes false. This way, the second turn marks the start of a new round
     */
    private boolean nextRound = true;

    public Game(final Player player1, final Player player2) {
        table = new ArrayList<>();
        for (int i = 0; i < Constants.Integers.NUMBER_OF_ROWS; ++i) {
            ArrayList<Minion> row = new ArrayList<>();
            table.add(row);
        }

        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    /**
     * When a round is over, the round counter increases, each player is given
     * a new card from the deck, and more mana (based on the current round)
     */
    public void nextRound() {
        ++roundCount;

        player1.addCardHand();
        player2.addCardHand();

        this.addMana();
    }

    /**
     * When a player's turn is over: all their cards (including their hero) are no longer frozen
     * and have not yet attacked (as the round in which they may have has finished), the other
     * player is now the current player and the round may also change depending on whose turn
     * just ended
     */
    public void nextTurn() {
        /*
        The hero has not attacked
         */
        this.getCurrPlayer().getHero().setHasAttacked(false);

        /*
        Each card's boolean fields are set to false and the current player is changed
         */
        if (currPlayerIdx == 1) {
            for (int i = 0; i < table.get(Constants.Integers.PLAYER_ONE_BACK_ROW).size(); i++) {
                Minion card1 = table.get(Constants.Integers.PLAYER_ONE_BACK_ROW).get(i);
                card1.setFrozen(false);
                card1.setHasAttacked(false);
            }

            for (int j = 0; j < table.get(Constants.Integers.PLAYER_ONE_FRONT_ROW).size(); j++) {
                Minion card2 = table.get(Constants.Integers.PLAYER_ONE_FRONT_ROW).get(j);
                card2.setFrozen(false);
                card2.setHasAttacked(false);
            }

            currPlayerIdx = 2;
        } else {
            for (int i = 0; i < table.get(Constants.Integers.PLAYER_TWO_FRONT_ROW).size(); i++) {
                Minion card1 = table.get(Constants.Integers.PLAYER_TWO_FRONT_ROW).get(i);
                card1.setFrozen(false);
                card1.setHasAttacked(false);
            }

            for (int j = 0; j < table.get(Constants.Integers.PLAYER_TWO_BACK_ROW).size(); j++) {
                Minion card2 = table.get(Constants.Integers.PLAYER_TWO_BACK_ROW).get(j);
                card2.setFrozen(false);
                card2.setHasAttacked(false);
            }

            currPlayerIdx = 1;
        }

        /*
        The nextRound boolean is negated
         */
        nextRound = !nextRound;

        /*
        If true, nextRound()
         */
        if (nextRound) {
            nextRound();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     *
     * @return the current player
     */
    public Player getCurrPlayer() {
        if (currPlayerIdx == 1) {
            return player1;
        } else {
            return player2;
        }
    }


    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    public long getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(final long shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public int getCurrPlayerIdx() {
        return currPlayerIdx;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    /**
     * Sets up a game, so that commands may be given afterwards
     * @param playerOneDeckIdx player1's current deck index
     * @param playerTwoDeckIdx player2's current deck index
     * @param shuffleSeedGiven the shuffle seed
     * @param playerOneHero player1's hero
     * @param playerTwoHero player2's hero
     * @param startingPlayerGiven which player is the first to start
     */
    public void setup(final int playerOneDeckIdx, final int playerTwoDeckIdx,
                      final long shuffleSeedGiven, final Hero playerOneHero,
                      final Hero playerTwoHero, final int startingPlayerGiven) {
        this.shuffleSeed = shuffleSeedGiven;
        this.setPlayerHero(this.player1, playerOneHero);
        this.setPlayerHero(this.player2, playerTwoHero);
        this.startingPlayer = startingPlayerGiven;
        this.currPlayerIdx = startingPlayerGiven;
        this.setDeckPlayer(this.player1, playerOneDeckIdx);
        this.setDeckPlayer(this.player2, playerTwoDeckIdx);
        this.setShuffledDecks();
    }

    /**
     *
     * @param idx 1 or 2
     * @return the requested player
     */
    public Player getPlayerByIdx(final int idx) {
        if (idx == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Manually shuffles the decks and sets them
     */
    public void setShuffledDecks() {
        Random r1 = new Random(shuffleSeed);
        Random r2 = new Random(shuffleSeed);

        ArrayList<Card> shuffledDeck1 = new ArrayList<>(player1.getDeck(player1.getCurrDeckIdx()));
        ArrayList<Card> shuffledDeck2 = new ArrayList<>(player2.getDeck(player2.getCurrDeckIdx()));

        for (int i = player1.getDeck(player1.getCurrDeckIdx()).size() - 1; i >= 1; i--) {
            Collections.swap(shuffledDeck1, i, r1.nextInt(i + 1));
        }

        for (int i = player2.getDeck(player2.getCurrDeckIdx()).size() - 1; i >= 1; i--) {
            Collections.swap(shuffledDeck2, i, r2.nextInt(i + 1));
        }

        player1.setCurrDeck(shuffledDeck1);
        player2.setCurrDeck(shuffledDeck2);
    }

    /**
     * Adds mana to each player
     */
    public void addMana() {
        /*
        Each player is given n mana for the n-th round
        After 10 rounds, they are only given 10
         */
        int addedMana = Math.min(roundCount, Constants.Integers.MAX_MANA_INCREASE);

        this.player1.setMana(this.player1.getMana() + addedMana);
        this.player2.setMana(this.player2.getMana() + addedMana);
    }

    /**
     *
     * @param player the player whose deck is to be set
     * @param idx the deck's index
     */
    public void setDeckPlayer(final Player player, final int idx) {
        player.setCurrDeckIdx(idx);
    }

    /**
     *
     * @param player the player's whose hero is to be set
     * @param hero the hero
     */
    public void setPlayerHero(final Player player, final Hero hero) {
        player.setHero(hero);
    }

    /**
     *
     * @param playerIdx 1 or 2
     * @return the requested player's hero
     */
    public Hero getPlayerHero(final int playerIdx) {
        if (playerIdx == 1) {
            return player1.getHero();
        } else {
            return player2.getHero();
        }
    }

    /**
     *
     * @param playerIdx 1 or 2
     * @return the requested player's mana
     */
    public int getPlayerMana(final int playerIdx) {
        if (playerIdx == 1) {
            return player1.getMana();
        } else {
            return player2.getMana();
        }
    }

    /**
     *
     * @param x the row's index
     * @param y the card's index on its row
     * @return the card or null if it is not found
     */
    public Card getCardAtPosition(final int x, final int y) {
        Card card;

        try {
            card = table.get(x).get(y);
            return card;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param playerIdx 1 or 2
     * @return the environment cards of the requested player
     */
    public ArrayList<Environment> getEnvironmentCardsInHand(final int playerIdx) {
        if (playerIdx == 1) {
            return player1.getEnvironmentCards();
        } else {
            return player2.getEnvironmentCards();
        }
    }

    /**
     *
     * @return the frozen cards on the table
     */
    public ArrayList<Minion> getFrozenCards() {
        ArrayList<Minion> frozenCards = new ArrayList<>();

        for (int i = 0; i < Constants.Integers.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < table.get(i).size(); ++j) {
                Minion minion = table.get(i).get(j);
                if (minion.isFrozen()) {
                    frozenCards.add(minion);
                }
            }
        }

        return frozenCards;
    }

    /**
     * Places the given card on the table's specified row, removes it from the player's
     * hand, subtracting mana
     * Various checks are performed
     * @param idx the row's index
     * @param card the card that is to be added
     * @return null or an error String
     */
    public String addMinionRows(final int idx, final Card card) {
        /*
        Checks if the card is an environment card
         */
        if (card instanceof Environment) {
            return "Cannot place environment card on table.";
        } else {
            /*
            Checks if the current player does not have enough mana to place the card
             */
            if (card.getMana() > this.getCurrPlayer().getMana()) {
                return "Not enough mana to place card on table.";
            } else {
                /*
                Checks if there is space left on the row
                 */
                if (table.get(idx).size() == Constants.Integers.MAX_CARDS) {
                    return "Cannot place card on table since row is full.";
                } else {
                    this.getCurrPlayer().setMana(this.getCurrPlayer().getMana()
                            - card.getMana());

                    Minion minion;
                    if (card instanceof Tank) {
                        minion = new Tank((Tank) card);
                    } else if (card instanceof Disciple) {
                        minion = new Disciple((Disciple) card);
                    } else if (card instanceof Miraj) {
                        minion = new Miraj((Miraj) card);
                    } else if (card instanceof TheCursedOne) {
                        minion = new TheCursedOne((TheCursedOne) card);
                    } else if (card instanceof TheRipper) {
                        minion = new TheRipper((TheRipper) card);
                    } else {
                        minion = new Generic((Generic) card);
                    }

                    table.get(idx).add(minion);

                    this.getCurrPlayer().getHand().remove(card);

                    return null;
                }
            }
        }
    }

    /**
     * Places card on the table
     * Calls addMinionRows
     * @param cardIdx the index of the card in the current player's hand
     * @return null or an error String
     */
    public String placeCard(final int cardIdx) {
        Card card = this.getCurrPlayer().getHand().get(cardIdx);

        int frontRow, backRow;

        if (this.getCurrPlayerIdx() == 1) {
            frontRow = Constants.Integers.PLAYER_ONE_FRONT_ROW;
            backRow = Constants.Integers.PLAYER_ONE_BACK_ROW;
        } else {
            frontRow = Constants.Integers.PLAYER_TWO_FRONT_ROW;
            backRow = Constants.Integers.PLAYER_TWO_BACK_ROW;
        }

        /*
        Places the card according to its type
         */
        if (card instanceof TheRipper || card instanceof Miraj || card instanceof Tank) {
            return addMinionRows(frontRow, card);
        } else {
            return addMinionRows(backRow, card);
        }
    }

    /**
     * Uses the Environment card and removes it from the current player's hand,
     * subtracting mana
     * Various checks are performed
     * @param handIdx the index of the card in the current player's hand
     * @param affectedRow the row on which the card is to be used
     * @return null or an error String
     */
    public String useEnvironmentCard(final int handIdx, final int affectedRow) {
        Player currPlayer = this.getCurrPlayer();
        Card card = currPlayer.getHand().get(handIdx);

        /*
        Checks if it is an environment card
         */
        if (card instanceof Environment env) {
            /*
            Checks if the current player has enough mana
             */
            if (currPlayer.getMana() >= card.getMana()) {
                /*
                Checks if the given row is the enemy player's row
                 */
                if ((currPlayerIdx == 1
                        && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                        || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))
                        || (currPlayerIdx == 2
                        && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                        || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                    ArrayList<Minion> affectedRowArray = table.get(affectedRow);

                    /*
                    Checks if the card is HeartHound
                     */
                    if (env instanceof HeartHound) {
                        /*
                        Gets the enemy player's mirrored row
                        the mirrored row's index = 3 - the current row's index
                         */
                        ArrayList<Minion> mirror =
                                table.get(Constants.Integers.OPPOSITE - affectedRow);

                        /*
                        Checks if there is enough space on the mirror row
                         */
                        if (mirror.size() == Constants.Integers.MAX_CARDS) {
                            return Constants.Error.ROW_FULL;
                        } else {
                            ((HeartHound) env).action(mirror, affectedRowArray);

                            currPlayer.setMana(currPlayer.getMana() - env.getMana());

                            currPlayer.getHand().remove(env);

                            return null;
                        }
                    } else {
                        env.action(affectedRowArray);

                        /*
                        If Firestorm was used, check if any card now has 0 HP
                        Removes those cards
                         */
                        if (env instanceof Firestorm) {
                            int i = 0;
                            while (i < affectedRowArray.size()) {
                                if (affectedRowArray.get(i).getHealth() == 0) {
                                    affectedRowArray.remove(i);
                                    i--;
                                }
                                i++;
                            }
                        }

                        currPlayer.setMana(currPlayer.getMana() - card.getMana());
                        currPlayer.getHand().remove(card);

                        return null;
                    }
                } else {
                    return Constants.Error.CHOSEN_ROW_NOT_ENEMY;
                }
            } else {
                return Constants.Error.ENVIRONMENT_NO_MANA;
            }
        } else {
            return Constants.Error.NOT_ENVIRONMENT;
        }
    }

    /**
     * Attacks a card on the table
     * Various checks are performed
     * @param x1 the attacker card's row
     * @param y1 the attacker card's index on its row
     * @param x2 the attacked card's row
     * @param y2 the attacked card's index on its row
     * @return null or an error String
     */
    public String attackMinion(final int x1, final int y1, final int x2, final int y2) {
        Minion cardAttacker, cardAttacked;

        /*
        Checks if the attacked card does not belong to the enemy
         */
        if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))
                || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
            return Constants.Error.CARD_NOT_ENEMY;
        }

        cardAttacker = table.get(x1).get(y1);
        cardAttacked = table.get(x2).get(y2);

        /*
        Checks if the attacker card has already attacked
         */
        if (cardAttacker.getHasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }

        /*
        Checks if the attacker card is frozen
         */
        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        /*
        Checks if a Tank exists on the table
        The row on which a player's Tank resides matches said player's index
         */
        if (!(cardAttacked instanceof Tank)) {
            for (Minion minion : table.get(currPlayerIdx)) {
                if (minion instanceof Tank) {
                    return Constants.Error.NOT_TANK;
                }
            }
        }

        cardAttacker.attackMinion(cardAttacked);

        /*
        Removes the attacked card is its HP is 0
         */
        if (cardAttacked.getHealth() == 0) {
            table.get(x2).remove(cardAttacked);
        }

        return null;
    }

    /**
     * Uses the ability of a card on the table
     * Various checks are performed
     * @param x1 the attacker card's row
     * @param y1 the attacker card's index on their row
     * @param x2 the attacked card's row
     * @param y2 the attacked card's index on their row
     * @return null or an error String
     */
    public String minionUseAbility(final int x1, final int y1, final int x2, final int y2) {
        Minion cardAttacker, cardAttacked;

        cardAttacker = table.get(x1).get(y1);
        cardAttacked = table.get(x2).get(y2);

        /*
        Checks if the attacker card is frozen
         */
        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        /*
        Checks if the attacker card has already attacked
         */
        if (cardAttacker.getHasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }

        if (cardAttacker instanceof Disciple) {
            /*
            Checks if the "attacked" card does not belong to the current player
            (Disciple heals a card)
             */
            if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
                    || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))
                    || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
                    || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                return Constants.Error.CARD_NOT_CURRENT;
            }

            cardAttacker.action(cardAttacked);

        } else {
            /*
            Checks if the attacked card does not belong to the enemy player
             */
            if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
                    || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))
                    || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
                    || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
                return Constants.Error.CARD_NOT_ENEMY;
            }

            /*
            Checks if a Tank exists on the table
            The row on which a player's Tank resides matches said player's index
             */
            if (!(cardAttacked instanceof Tank)) {
                for (Minion minion : table.get(currPlayerIdx)) {
                    if (minion instanceof Tank) {
                        return Constants.Error.NOT_TANK;
                    }
                }
            }

            cardAttacker.action(cardAttacked);

            /*
            Removes the attacked card is its HP is 0
             */
            if (cardAttacked.getHealth() == 0) {
                table.get(x2).remove(cardAttacked);
            }
        }

        return null;
    }

    /**
     * Attacks the enemy player's hero using the given card
     * Various checks are performed
     * @param x the row's index
     * @param y the card's index on its row
     * @return null or an error String
     */
    public String minionAttackHero(final int x, final int y) {
        Minion cardAttacker;

        cardAttacker = table.get(x).get(y);

        /*
        Checks if the attacker card is frozen
         */
        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        /*
        Checks if the attacker card has already attacked
         */
        if (cardAttacker.getHasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }


        /*
        Checks if a Tank exists on the table
        The row on which a player's Tank resides matches said player's index
         */
        for (Minion minion : table.get(currPlayerIdx)) {
            if (minion instanceof Tank) {
                return Constants.Error.NOT_TANK;
            }
        }

        /*
        Gets the enemy player's hero
        the opposite's player index = 3 - the current player's index
         */
        Hero hero = this.getPlayerHero(Constants.Integers.OPPOSITE - currPlayerIdx);

        cardAttacker.attackHero(hero);

        /*
        Removes the attacked card is its HP is 0
         */
        if (hero.getHealth() == 0) {
            return Integer.toString(currPlayerIdx);
        }

        return null;
    }

    /**
     * Uses the current player's hero's ability on a given card, subtracting mana
     * Various checks are performed
     * @param affectedRow the row on which the hero's ability is to be used
     * @return null or an error String
     */
    public String useHeroAbility(final int affectedRow) {
        Hero hero = this.getCurrPlayer().getHero();

        /*
        Checks if the current player does not have enough mana
         */
        if (this.getCurrPlayer().getMana() < hero.getMana()) {
            return Constants.Error.HERO_NO_MANA;
        }

        /*
        Checks if the current player's hero has already attacked
         */
        if (hero.getHasAttacked()) {
            return Constants.Error.HERO_ALREADY_ATTACKED;
        }

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            /*
            Checks if the given row does not belong to the enemy player
             */
            if ((currPlayerIdx == 1
                    && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))
                    || (currPlayerIdx == 2
                    && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
                return Constants.Error.SELECTED_ROW_NOT_ENEMY;
            }
        } else {
            /*
            Checks if the given row does not belong to the current player
             */
            if ((currPlayerIdx == 1
                    && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))
                    || (currPlayerIdx == 2
                    && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                return Constants.Error.SELECTED_ROW_NOT_CURRENT;
            }
        }

        ArrayList<Minion> minions = table.get(affectedRow);
        hero.action(minions);

        this.getCurrPlayer().setMana(this.getCurrPlayer().getMana() - hero.getMana());

        return null;
    }

}
