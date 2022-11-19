package implementation;

import constants.Constants;
import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;

import implementation.card.minion.Minion;
import implementation.card.minion.Disciple;
import implementation.card.minion.Miraj;
import implementation.card.minion.Tank;
import implementation.card.minion.TheCursedOne;
import implementation.card.minion.TheRipper;

import java.util.ArrayList;

public class Player {
    private int mana;
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;
    private ArrayList<Card> currDeck;
    private int currDeckIdx;
    private Hero hero;
    private ArrayList<Card> hand;
    private int wins;

    public Player(final int nrCardsInDeck, final int nrDecks,
                  final ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
        this.mana = Constants.Integers.STARTING_MANA;
        this.hand = new ArrayList<>();
        this.currDeck = new ArrayList<>();
    }

    public Player(final Player player) {
        this.mana = player.mana;
        this.nrCardsInDeck = player.nrCardsInDeck;
        this.nrDecks = player.nrDecks;
        this.decks = player.decks;
        this.currDeck = player.currDeck;
        this.currDeckIdx = player.currDeckIdx;
        this.hero = player.hero;
        this.hand = player.hand;
        this.wins = player.wins;
    }

    /**
     *
     * @return
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     *
     * @param nrCardsInDeck
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     *
     * @return
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     *
     * @param nrDecks
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     *
     * @return
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    /**
     *
     * @param decks
     */
    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    /**
     *
     * @return
     */
    public Hero getHero() {
        return hero;
    }

    /**
     *
     * @param hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     *
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     *
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return
     */
    public ArrayList<Card> getCurrDeck() {
        return currDeck;
    }

    /**
     *
     * @param currDeck
     */
    public void setCurrDeck(final ArrayList<Card> currDeck) {
        this.currDeck = currDeck;
    }

    /**
     *
     * @return
     */
    public int getCurrDeckIdx() {
        return currDeckIdx;
    }

    /**
     *
     * @param currDeckIdx
     */
    public void setCurrDeckIdx(final int currDeckIdx) {
        this.currDeckIdx = currDeckIdx;
    }

    /**
     *
     * @return
     */
    public int getWins() {
        return wins;
    }

    /**
     *
     * @param wins
     */
    public void setWins(final int wins) {
        this.wins = wins;
    }

    /**
     *
     * @param game
     * @param idx
     * @param card
     * @return
     */
    public String addMinionRows(final Game game, final int idx, final Card card) {
        if (card instanceof Environment) {
            return "Cannot place environment card on table.";
        } else {
            if (card.getMana() > this.mana) {
                return "Not enough mana to place card on table.";
            } else {
                if (game.getTable().get(idx).size() == Constants.Integers.MAX_CARDS) {
                    return "Cannot place card on table since row is full.";
                } else {
                    this.mana -= card.getMana();
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
                        minion = new Minion((Minion) card);
                    }

                    game.getTable().get(idx).add(minion);

                    hand.remove(card);
                    return null;
                }
            }
        }
    }

    /**
     *
     */
    public void addCardHand() {
        if (currDeck.size() != 0) {
            this.hand.add(currDeck.remove(0));
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Environment> getEnvironmentCards() {
        ArrayList<Environment> envs = new ArrayList<>();

        for (Card card : hand) {
            if (card instanceof Environment) {
                envs.add((Environment) card);
            }
        }

        return envs;
    }

    /**
     *
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     *
     * @param hand
     */
    public void setHand(final ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     *
     * @param idx
     * @return
     */
    public ArrayList<Card> getDeck(final int idx) {
        return this.getDecks().get(idx);
    }

}
