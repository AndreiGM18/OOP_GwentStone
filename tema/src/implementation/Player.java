package implementation;

import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;
import implementation.card.minion.*;

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

    public Player(int nrCardsInDeck, int nrDecks, ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
        this.mana = 1;
        this.hand = new ArrayList<>();
        this.currDeck = new ArrayList<>();
    }

    public Player(Player player) {
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

    public String addMinionRows(Game game, int idx, Card card) {
        if (card instanceof Environment)
            return "Cannot place environment card on table.";
        else {
            if (card.getMana() > this.mana)
                return "Not enough mana to place card on table.";
            else {
                if (game.getTable().get(idx).size() == 5)
                    return "Cannot place card on table since row is full.";
                else {
                    this.mana -= card.getMana();
                    Minion minion;
                    if (card instanceof Tank)
                        minion = new Tank((Tank) card);
                    else if (card instanceof Disciple)
                        minion = new Disciple((Disciple) card);
                    else if (card instanceof Miraj)
                        minion = new Miraj((Miraj) card);
                    else if (card instanceof TheCursedOne)
                        minion = new TheCursedOne((TheCursedOne) card);
                    else if (card instanceof TheRipper)
                       minion = new TheRipper((TheRipper) card);
                    else
                        minion = new Minion((Minion) card);

                    game.getTable().get(idx).add(minion);

                    hand.remove(card);
                    return null;
                }
            }
        }
    }

    public void addCardHand() {
        if (currDeck.size() != 0)
            this.hand.add(currDeck.remove(0));
    }

    public ArrayList<Environment> getEnvironmentCards() {
        ArrayList<Environment> envs = new ArrayList<>();

        for (Card card : hand)
            if (card instanceof Environment)
                envs.add((Environment) card);

        return envs;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getDeck(int idx) {
        return this.getDecks().get(idx);
    }

}
