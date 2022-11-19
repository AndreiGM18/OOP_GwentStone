package implementation;

import constants.Constants;

import implementation.card.Card;

import implementation.card.environment.Environment;
import implementation.card.environment.Firestorm;
import implementation.card.environment.HeartHound;
import implementation.card.environment.Winterfell;

import implementation.card.hero.Hero;
import implementation.card.hero.EmpressThorina;
import implementation.card.hero.GeneralKocioraw;
import implementation.card.hero.KingMudface;
import implementation.card.hero.LordRoyce;

import implementation.card.minion.Minion;
import implementation.card.minion.Disciple;
import implementation.card.minion.Miraj;
import implementation.card.minion.Tank;
import implementation.card.minion.TheCursedOne;
import implementation.card.minion.TheRipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private long shuffleSeed;
    private int currPlayerIdx;
    private int startingPlayer;
    private final ArrayList<ArrayList<Minion>> table;

    private int roundCount = 1;
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
     *
     */
    public void nextRound() {
        ++roundCount;

        player1.addCardHand();
        player2.addCardHand();
        addMana();
    }

    /**
     *
     */
    public void nextTurn() {
        this.getCurrPlayer().getHero().setHasAttacked(false);

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

        nextRound = !nextRound;

        if (nextRound) {
            nextRound();
        }
    }

    /**
     *
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     *
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     *
     * @return
     */
    public Player getCurrPlayer() {
        if (currPlayerIdx == 1) {
            return player1;
        } else {
            return player2;
        }
    }


    /**
     *
     * @return
     */
    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    /**
     *
     * @return
     */
    public long getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     *
     * @param shuffleSeed
     */
    public void setShuffleSeed(final long shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     *
     * @param playerOneDeckIdx
     * @param playerTwoDeckIdx
     * @param shuffleSeedGiven
     * @param playerOneHero
     * @param playerTwoHero
     * @param startingPlayerGiven
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
     * @param idx
     * @return
     */
    public Player getPlayerByIdx(final int idx) {
        if (idx == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     *
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
     *
     */
    public void addMana() {
        int addedMana = Math.min(roundCount, Constants.Integers.MAX_MANA_INCREASE);

        this.player1.setMana(this.player1.getMana() + addedMana);
        this.player2.setMana(this.player2.getMana() + addedMana);
    }

    /**
     *
     * @param player
     * @param idx
     */
    public void setDeckPlayer(final Player player, final int idx) {
        player.setCurrDeckIdx(idx);
    }

    /**
     *
     * @param player
     * @param hero
     */
    public void setPlayerHero(final Player player, final Hero hero) {
        player.setHero(hero);
    }

    /**
     *
     * @return
     */
    public int getCurrPlayerIdx() {
        return currPlayerIdx;
    }

    /**
     *
     * @param currPlayerIdx
     */
    public void setCurrPlayerIdx(final int currPlayerIdx) {
        this.currPlayerIdx = currPlayerIdx;
    }

    /**
     *
     * @return
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }

    /**
     *
     * @param startingPlayer
     */
    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    /**
     *
     * @param playerIdx
     * @return
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
     * @param playerIdx
     * @return
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
     * @param x
     * @param y
     * @return
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
     * @param playerIdx
     * @return
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
     * @return
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
     *
     * @param cardIdx
     * @return
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

        if (card instanceof TheRipper || card instanceof Miraj || card instanceof Tank) {
            return this.getCurrPlayer().addMinionRows(this, frontRow, card);
        } else {
            return this.getCurrPlayer().addMinionRows(this, backRow, card);
        }
    }

    /**
     *
     * @param handIdx
     * @param affectedRow
     * @return
     */
    public String useEnvironmentCard(final int handIdx, final int affectedRow) {
        Player currPlayer = this.getCurrPlayer();
        Card card = currPlayer.getHand().get(handIdx);

        if (card instanceof Environment) {
            if (currPlayer.getMana() >= card.getMana()) {
                if ((currPlayerIdx == 1
                    && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))
                    || (currPlayerIdx == 2
                    && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                    || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                    ArrayList<Minion> affectedRowArray = this.getTable().get(affectedRow);

                    if (card instanceof HeartHound) {
                        ArrayList<Minion> mirror =
                                this.getTable().get(Constants.Integers.MIRROR - affectedRow);

                        if (mirror.size() == Constants.Integers.MAX_CARDS) {
                            return Constants.Error.ROW_FULL;
                        } else {
                            ((HeartHound) card).action(mirror, affectedRowArray);

                            currPlayer.setMana(currPlayer.getMana() - card.getMana());

                            currPlayer.getHand().remove(card);

                            return null;
                        }
                    } else {
                        if (card instanceof Firestorm) {
                            ((Firestorm) card).action(affectedRowArray);

                            int i = 0;
                            while (i < affectedRowArray.size()) {
                                if (affectedRowArray.get(i).getHealth() == 0) {
                                    affectedRowArray.remove(i);
                                    i--;
                                }
                                i++;
                            }
                        }
                        if (card instanceof Winterfell) {
                            ((Winterfell) card).action((affectedRowArray));
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
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public String attackMinion(final int x1, final int y1, final int x2, final int y2) {
        Minion cardAttacker, cardAttacked;

        if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
            || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))
            || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
            || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
            return Constants.Error.CARD_NOT_ENEMY;
        }

        try {
            cardAttacker = table.get(x1).get(y1);
            cardAttacked = table.get(x2).get(y2);
        } catch (Exception e) {
            return null;
        }

        if (cardAttacker.hasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }

        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        if (!(cardAttacked instanceof Tank)) {
            for (Minion minion : table.get(currPlayerIdx)) {
                if (minion instanceof Tank) {
                    return Constants.Error.NOT_TANK;
                }
            }
        }

        cardAttacker.attackMinion(cardAttacked);

        if (cardAttacked.getHealth() == 0) {
            table.get(x2).remove(cardAttacked);
        }

        return null;
    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public String minionUseAbility(final int x1, final int y1, final int x2, final int y2) {
        Minion cardAttacker, cardAttacked;

        try {
            cardAttacker = table.get(x1).get(y1);
            cardAttacked = table.get(x2).get(y2);
        } catch (Exception e) {
            return null;
        }

        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        if (cardAttacker.hasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }

        if (cardAttacker instanceof Disciple) {
            if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))
                || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                return Constants.Error.CARD_NOT_CURRENT;
            }
        } else if (cardAttacker instanceof TheRipper || cardAttacker instanceof Miraj
                || cardAttacker instanceof TheCursedOne) {
            if ((currPlayerIdx == 1 && (x2 == Constants.Integers.PLAYER_ONE_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_ONE_BACK_ROW))
                || (currPlayerIdx == 2 && (x2 == Constants.Integers.PLAYER_TWO_FRONT_ROW
                || x2 == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
                return Constants.Error.CARD_NOT_ENEMY;
            }

            if (!(cardAttacked instanceof Tank)) {
                for (Minion minion : table.get(currPlayerIdx)) {
                    if (minion instanceof Tank) {
                        return Constants.Error.NOT_TANK;
                    }
                }
            }
        }

        if (cardAttacker instanceof Miraj) {
            ((Miraj) cardAttacker).action(cardAttacked);
        }
        if (cardAttacker instanceof TheRipper) {
            ((TheRipper) cardAttacker).action(cardAttacked);
        }
        if (cardAttacker instanceof TheCursedOne) {
            ((TheCursedOne) cardAttacker).action(cardAttacked);
        }
        if (cardAttacker instanceof Disciple) {
            ((Disciple) cardAttacker).action(cardAttacked);
        }

        if (cardAttacked.getHealth() == 0) {
            table.get(x2).remove(cardAttacked);
        }

        return null;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public String minionAttackHero(final int x, final int y) {
        Minion cardAttacker;

        try {
            cardAttacker = table.get(x).get(y);
        } catch (Exception e) {
            return null;
        }

        if (cardAttacker.isFrozen()) {
            return Constants.Error.FROZEN;
        }

        if (cardAttacker.hasAttacked()) {
            return Constants.Error.CARD_ALREADY_ATTACKED;
        }

        if (currPlayerIdx == 1) {
            for (Minion minion : table.get(1)) {
                if (minion instanceof Tank) {
                    return Constants.Error.NOT_TANK;
                }
            }
        } else {
            for (Minion minion : table.get(2)) {
                if (minion instanceof Tank) {
                    return Constants.Error.NOT_TANK;
                }
            }
        }

        Hero hero = this.getPlayerHero(Constants.Integers.MIRROR - currPlayerIdx);
        cardAttacker.attackHero(hero);

        if (hero.getHealth() == 0) {
            return Integer.toString(currPlayerIdx);
        }

        return null;
    }

    /**
     *
     * @param affectedRow
     * @return
     */
    public String useHeroAbility(final int affectedRow) {
        Hero hero = this.getCurrPlayer().getHero();

        if (this.getCurrPlayer().getMana() < hero.getMana()) {
            return Constants.Error.HERO_NO_MANA;
        }

        if (hero.hasAttacked()) {
            return Constants.Error.HERO_ALREADY_ATTACKED;
        }

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            if ((currPlayerIdx == 1 && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))
                || (currPlayerIdx == 2 && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))) {
                return Constants.Error.SELECTED_ROW_NOT_ENEMY;
            }
        } else if (hero instanceof GeneralKocioraw || hero instanceof KingMudface) {
            if ((currPlayerIdx == 1 && (affectedRow == Constants.Integers.PLAYER_TWO_FRONT_ROW
                || affectedRow == Constants.Integers.PLAYER_TWO_BACK_ROW))
                || (currPlayerIdx == 2 && (affectedRow == Constants.Integers.PLAYER_ONE_FRONT_ROW
                || affectedRow == Constants.Integers.PLAYER_ONE_BACK_ROW))) {
                return Constants.Error.SELECTED_ROW_NOT_CURRENT;
            }
        }

        ArrayList<Minion> minions = this.getTable().get(affectedRow);

        if (hero instanceof EmpressThorina) {
            ((EmpressThorina) hero).action(minions);
        }

        if (hero instanceof GeneralKocioraw) {
            ((GeneralKocioraw) hero).action(minions);
        }

        if (hero instanceof KingMudface) {
            ((KingMudface) hero).action(minions);
        }

        if (hero instanceof LordRoyce) {
            ((LordRoyce) hero).action(minions);
        }

        this.getCurrPlayer().setMana(this.getCurrPlayer().getMana() - hero.getMana());
        hero.setHasAttacked(true);
        return null;
    }

}
