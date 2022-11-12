package implementation;

import java.util.ArrayList;

class Game {
    private static Game instance = null;
    private static int instCount = 0;
    private Player player1;
    private Player player2;
    private int currPlayerIdx;
    private Minion[][] table;
    private int roundCount = 1;

    private Game() {
        ++instCount;
        table = new Minion[5][5];
    }

    public static Game startGame() {
        if (instance == null)
            instance = new Game();

        return instance;
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

    private void setStartingPlayer1(int startingPlayerIdx) {
        if (startingPlayerIdx == 1)
            this.currPlayerIdx = 1;
        else
            this.currPlayerIdx = 2;
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
        if (table[x][y] != null)
            return table[x][y];
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
                if (table[i][j].isFrozen())
                    frozenCards.add(table[i][j]);

        return frozenCards;
    }

    private int getTotalGamesPlayed() {
        return getNumberOfGames();
    }
}
