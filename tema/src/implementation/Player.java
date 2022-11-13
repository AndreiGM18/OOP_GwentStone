package implementation;

import java.util.ArrayList;

class Deck {
    private ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}

class Player {
    private int mana;
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<Deck> decks;
    private Deck currDeck;
    private int currDeckIdx;
    private Hero hero;
    private Deck hand;
    private int chosenDeckIdx;
    private int wins;

    public Player(int nrCardsInDeck, int nrDecks, ArrayList<Deck> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
        this.mana = 1;
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

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
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

    public Deck getCurrDeck() {
        return currDeck;
    }

    public void setCurrDeck(Deck currDeck) {
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

    private void addCardHand(Deck hand, Deck currDeck) {
        hand.getCards().add(currDeck.getCards().remove(0));
    }

    public ArrayList<Environment> getEnvironmentCards() {
        ArrayList<Environment> envs = new ArrayList<Environment>();

        for (Card card : hand.getCards())
            if (card instanceof Environment)
                envs.add((Environment) card);

        return envs;
    }
}
