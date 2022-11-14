package implementation;

import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;
import implementation.card.minion.Minion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private final Player player1;
    private final Player player2;
    private long shuffleSeed;
    private int currPlayerIdx;
    private int startingPlayer;
    private ArrayList<ArrayList<Minion>> table;

    private int roundCount = 1;

    public Game(Player player1, Player player2) {
        table = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            ArrayList<Minion> row = new ArrayList<>();
            table.add(row);
        }

        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    public Player getPlayer1() {
        return player1;
    }


    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrPlayer() {
        if (currPlayerIdx == 1)
            return player1;
        else
            return player2;
    }

    public ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }


    public ArrayList<Minion> getRow(int idx) {
        return this.getTable().get(idx);
    }

    public long getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(long shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }


    public void setup(int playerOneDeckIdx, int playerTwoDeckIdx,
                      long shuffleSeed, Hero playerOneHero, Hero playerTwoHero, int startingPlayer) {
        this.setShuffleSeed(shuffleSeed);
        this.setPlayerHero(this.player1, playerOneHero);
        this.setPlayerHero(this.player2, playerTwoHero);
        this.setStartingPlayer(startingPlayer);
        this.setCurrPlayerIdx(startingPlayer);
        this.setDeckPlayer(this.player1, playerOneDeckIdx);
        this.setDeckPlayer(this.player2, playerTwoDeckIdx);
        this.setShuffledDecks();
    }

    public Player getPlayerbyIdx(int idx) {
        if (idx == 1)
            return player1;
        else
            return player2;
    }

    public void setShuffledDecks() {
        Random r1 = new Random(shuffleSeed);
        Random r2 = new Random(shuffleSeed);

        ArrayList<Card> shuffledDeck1 = new ArrayList<>(player1.getDeck(player1.getCurrDeckIdx()));
        ArrayList<Card> shuffledDeck2 = new ArrayList<>(player2.getDeck(player2.getCurrDeckIdx()));

        for (int i = player1.getDeck(player1.getCurrDeckIdx()).size() -1; i >= 1; i--)
            Collections.swap(shuffledDeck1, i, r1.nextInt(i + 1));

        for (int i = player2.getDeck(player2.getCurrDeckIdx()).size() -1; i >= 1; i--)
            Collections.swap(shuffledDeck2, i, r2.nextInt(i + 1));

        player1.setCurrDeck(shuffledDeck1);
        player2.setCurrDeck(shuffledDeck2);
    }

    public void addMana() {
        int addedMana = Math.min(roundCount, 10);

        this.player1.setMana(this.player1.getMana() + addedMana);
        this.player2.setMana(this.player2.getMana() + addedMana);
    }

    public void endRound() {
        ++roundCount;
        player1.addCardHand();
        player2.addCardHand();
    }

    public void setDeckPlayer(Player player, int idx) {
        player.setCurrDeckIdx(idx);
    }

    public void setPlayerHero(Player player, Hero hero) {
        player.setHero(hero);
    }

    public int getCurrPlayerIdx() {
        return currPlayerIdx;
    }

    public void setCurrPlayerIdx(int currPlayerIdx) {
        this.currPlayerIdx = currPlayerIdx;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public Hero getPlayerHero(int playerIdx) {
        if (playerIdx == 1)
            return player1.getHero();
        else
            return player2.getHero();
    }

    public int getPlayerMana(int playerIdx) {
        if (playerIdx == 1)
            return player1.getMana();
        else
            return player2.getMana();
    }

    public int getPlayerWins(int playerIdx) {
        if (playerIdx == 1)
            return player1.getWins();
        else
            return player2.getWins();
    }

    public Card getCardAtPosition(int x, int y) {
        if (table.get(x).get(y) != null)
            return table.get(x).get(y);
        else
            return null;
    }

    public ArrayList<Environment> getEnvironmentCardsInHand(int playerIdx) {
        if (playerIdx == 1)
            return player1.getEnvironmentCards();
        else
            return player2.getEnvironmentCards();
    }

    public ArrayList<Minion> getFrozenCards() {
        ArrayList<Minion> frozenCards = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; ++j)
                if (table.get(i).get(j).isFrozen())
                    frozenCards.add(table.get(i).get(j));

        return frozenCards;
    }
}
