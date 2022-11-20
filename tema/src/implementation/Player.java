package implementation;

import constants.Constants;
import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;

import java.util.ArrayList;

public final class Player {
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
        this.hand = new ArrayList<>(player.hand);
        this.wins = player.wins;
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public ArrayList<Card> getCurrDeck() {
        return currDeck;
    }

    public void setCurrDeck(final ArrayList<Card> currDeck) {
        this.currDeck = currDeck;
    }

    public int getCurrDeckIdx() {
        return currDeckIdx;
    }

    public void setCurrDeckIdx(final int currDeckIdx) {
        this.currDeckIdx = currDeckIdx;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(final int wins) {
        this.wins = wins;
    }

    /**
     * Adds a card from the deck into the hand
     */
    public void addCardHand() {
        if (currDeck.size() != 0) {
            this.hand.add(currDeck.remove(0));
        }
    }

    /**
     * Returns all environment cards present in the player's hand
     * @return the environment cards in the hand
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
     * Returns a specified deck from the player's decks
     * @param idx the requested deck's index
     * @return the requested deck
     */
    public ArrayList<Card> getDeck(final int idx) {
        return this.getDecks().get(idx);
    }

}
