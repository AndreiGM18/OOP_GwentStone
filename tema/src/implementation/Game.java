package implementation;

import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.hero.Hero;
import implementation.card.minion.Minion;

import java.util.ArrayList;

public class Game {
    private static Game instance = null;
    private static int instCount = 0;
    private Player player1;
    private Player player2;
    private int shuffleSeed;
    private int currPlayerIdx;
    private int startingPlayer;
    private ArrayList<ArrayList<Minion>> table;
    private int roundCount = 1;

    private Game(Player player1, Player player2) {
        ++instCount;

        table = new ArrayList<ArrayList<Minion>>();
        for (int i = 0; i < 4; ++i) {
            ArrayList<Minion> row = new ArrayList<Minion>();
            table.add(row);
        }

        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public static Game initializeGame(Player player1, Player player2) {
        if (instance == null)
            instance = new Game(player1, player2);

        return instance;
    }

    public static void setup(int playerOneDeckIdx, int playerTwoDeckIdx,
                      int shuffleSeed, Hero playerOneHero, Hero playerTwoHero, int startingPlayer) {
        instance.setShuffleSeed(shuffleSeed);
        instance.setPlayerHero(instance.player1, playerOneHero);
        instance.setPlayerHero(instance.player2, playerTwoHero);
        instance.setStartingPlayer(startingPlayer);
        instance.setCurrPlayerIdx(startingPlayer);
        instance.setDeckIdx(instance.player1, playerOneDeckIdx);
        instance.setDeckIdx(instance.player2, playerTwoDeckIdx);
    }

    public static int getNumberOfGames() {
        return instCount;
    }

    private void addMana() {
        int addedMana;

        if (roundCount > 10)
            addedMana = 10;
        else
            addedMana = roundCount;

        this.player1.setMana(this.player1.getMana() + addedMana);
        this.player2.setMana(this.player2.getMana() + addedMana);
    }

    private void endRound() {
        ++roundCount;
    }

    private void setDeckIdx(Player player, int idx) {
        player.setCurrDeckIdx(idx);
        player.setCurrDeck(player.getDecks().get(idx));
    }

    private void setPlayerHero(Player player, Hero hero) {
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

    private Hero getPlayerHero(int playerIdx) {
        if (playerIdx == 1)
            return player1.getHero();
        else
            return player2.getHero();
    }

    private int getPlayerMana(int playerIdx) {
        if (playerIdx == 1)
            return player1.getMana();
        else
            return player2.getMana();
    }

    private int getPlayerWins(int playerIdx) {
        if (playerIdx == 1)
            return player1.getWins();
        else
            return player2.getWins();
    }

    private Card getCardAtPosition(int x, int y) {
        if (table.get(x).get(y) != null)
            return table.get(x).get(y);
        else
            // System.out.println("No card at that position");
            return null;
    }

    private ArrayList<Environment> getEnvironmentCardsInHand(int playerIdx) {
        if (playerIdx == 1)
            return player1.getEnvironmentCards();
        else
            return player2.getEnvironmentCards();
    }

    private ArrayList<Minion> getFrozenCards() {
        ArrayList<Minion> frozenCards = new ArrayList<Minion>();

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; ++j)
                if (table.get(i).get(j).isFrozen())
                    frozenCards.add(table.get(i).get(j));

        return frozenCards;
    }

    private int getTotalGamesPlayed() {
        return getNumberOfGames();
    }
}
