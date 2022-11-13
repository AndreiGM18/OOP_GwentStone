package implementation;

import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;
import implementation.card.minion.Minion;

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
    private int chosenDeckIdx;
    private int wins;

    public Player(int nrCardsInDeck, int nrDecks, ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
        this.mana = 1;
        this.hand = new ArrayList<Card>();
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getChosenDeckIdx() {
        return chosenDeckIdx;
    }

    public void setChosenDeckIdx(int chosenDeckIdx) {
        this.chosenDeckIdx = chosenDeckIdx;
    }

    public ArrayList<Card> getCurrDeck() {
        return currDeck;
    }

    public void setCurrDeck(ArrayList<Card> currDeck) {
        this.currDeck = currDeck;
    }

    public int getCurrDeckIdx() {
        return currDeckIdx;
    }

    public void setCurrDeckIdx(int currDeckIdx) {
        this.currDeckIdx = currDeckIdx;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    private void addMinionRows(ArrayList<Minion> row, Card card) {
        if (card instanceof Environment)
            System.out.println("Cannot place environment card on table.");
        else {
            if (card.getMana() > this.mana)
                System.out.println("Not enough mana to place card on table.");
            else {
                if (row.size() == 5)
                    System.out.println("Cannot place card on table since row is full.");
                else {
                    this.mana -= card.getMana();
                    row.add((Minion) card);
                }
            }
        }
    }

    private void addCardHand(ArrayList<Card> hand, ArrayList<Card>  currDeck) {
        hand.add(currDeck.remove(0));
    }

    public ArrayList<Environment> getEnvironmentCards() {
        ArrayList<Environment> envs = new ArrayList<Environment>();

        for (Card card : hand)
            if (card instanceof Environment)
                envs.add((Environment) card);

        return envs;
    }

}
