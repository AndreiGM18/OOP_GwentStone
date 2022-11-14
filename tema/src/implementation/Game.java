package implementation;

import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.environment.Firestorm;
import implementation.card.environment.HeartHound;
import implementation.card.environment.Winterfell;
import implementation.card.hero.*;
import implementation.card.minion.*;

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
    private boolean nextRound = true;

    public Game(Player player1, Player player2) {
        table = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            ArrayList<Minion> row = new ArrayList<>();
            table.add(row);
        }

        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    public void nextRound() {
        ++roundCount;

        player1.addCardHand();
        player2.addCardHand();
        addMana();
    }

    public void nextTurn() {
        this.getCurrPlayer().getHero().setHasAttacked(false);

        if (currPlayerIdx == 1) {
            for (int i = 0; i < 5; i++) {
                try {
                    Minion card1 = table.get(3).get(i);
                    card1.setFrozen(false);
                    card1.setHasAttacked(false);
                }
                catch (Exception e) {
                }

                try {
                    Minion card2 = table.get(2).get(i);
                    card2.setFrozen(false);
                    card2.setHasAttacked(false);
                }
                catch (Exception e) {
                }
            }
            currPlayerIdx = 2;
        }
        else {
            for (int i = 0; i < 5; i++) {
                try {
                    Minion card1 = table.get(1).get(i);
                    card1.setFrozen(false);
                    card1.setHasAttacked(false);
                }
                catch (Exception e) {
                }

                try {
                    Minion card2 = table.get(0).get(i);
                    card2.setFrozen(false);
                    card2.setHasAttacked(false);
                }
                catch (Exception e) {
                }
            }
            currPlayerIdx = 1;
        }

        nextRound = !nextRound;

        if (nextRound)
            nextRound();
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

        for (int i = player1.getDeck(player1.getCurrDeckIdx()).size() - 1; i >= 1; i--)
            Collections.swap(shuffledDeck1, i, r1.nextInt(i + 1));

        for (int i = player2.getDeck(player2.getCurrDeckIdx()).size() - 1; i >= 1; i--)
            Collections.swap(shuffledDeck2, i, r2.nextInt(i + 1));

        player1.setCurrDeck(shuffledDeck1);
        player2.setCurrDeck(shuffledDeck2);
    }

    public void addMana() {
        int addedMana = Math.min(roundCount, 10);

        this.player1.setMana(this.player1.getMana() + addedMana);
        this.player2.setMana(this.player2.getMana() + addedMana);
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
        Card card;

        try {
            card = table.get(x).get(y);
            return card;
        }
        catch (Exception e) {
            return null;
        }
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
            for (int j = 0; j < 5; ++j) {
                try {
                    Minion minion = table.get(i).get(j);
                    if (minion.isFrozen())
                        frozenCards.add(minion);
                }
                catch (Exception e) {
                }
            }

        return frozenCards;
    }

    public String placeCard(int cardIdx) {
        Card card = this.getCurrPlayer().getHand().get(cardIdx);

        int frontRow, backRow;

        if (this.getCurrPlayerIdx() == 1) {
            frontRow = 2;
            backRow = 3;
        } else {
            frontRow = 1;
            backRow = 0;
        }

        if (card instanceof TheRipper || card instanceof Miraj || card instanceof Tank)
            return this.getCurrPlayer().addMinionRows(this, frontRow, card);
        else
            return this.getCurrPlayer().addMinionRows(this, backRow, card);
    }

    public String useEnvironmentCard(int handIdx, int affectedRow) {
        Player currPlayer = this.getCurrPlayer();
        int currPlayerIdx = this.getCurrPlayerIdx();
        Card card = currPlayer.getHand().get(handIdx);

        if (card instanceof Environment) {
            if (currPlayer.getMana() >= card.getMana()) {
                if ((currPlayerIdx == 1 && (affectedRow == 0 || affectedRow == 1)) ||
                        (currPlayerIdx == 2 && (affectedRow == 2 || affectedRow == 3))) {
                    ArrayList<Minion> affectedRowArray = this.getTable().get(affectedRow);
                    if (card instanceof HeartHound) {
                        ArrayList<Minion> mirror = this.getTable().get(3 - affectedRow);
                        if (mirror.size() == 5) {
                            return "Cannot steal enemy card since the player's row is full.";
                        }
                        else {
                            ((HeartHound) card).action(mirror, affectedRowArray);
                            currPlayer.setMana(currPlayer.getMana() - card.getMana());
                            currPlayer.getHand().remove(card);
                            return null;
                        }
                    } else {
                        if (card instanceof Firestorm) {
                            ((Firestorm) card).action(affectedRowArray);
                        }
                        if (card instanceof Winterfell) {
                            ((Winterfell) card).action((affectedRowArray));
                        }

                        currPlayer.setMana(currPlayer.getMana() - card.getMana());
                        currPlayer.getHand().remove(card);
                        return null;
                    }
                }
                else {
                    return "Chosen row does not belong to the enemy.";
                }
            }
            else {
                return "Not enough mana to use environment card.";
            }
        }
        else {
            return "Chosen card is not of type environment.";
        }
    }

    public String attackMinion(int x1, int y1, int x2, int y2) {
        Minion cardAttacker, cardAttacked;

        if ((currPlayerIdx == 1 && (x2 == 3 || x2 == 2)) || (currPlayerIdx == 2 && (x2 == 1 || x2 == 0)))
            return "Attacked card does not belong to the enemy.";

        try {
            cardAttacker = table.get(x1).get(y1);
            cardAttacked = table.get(x2).get(y2);
        }
        catch (Exception e) {
            return null;
        }

        if (cardAttacker.hasAttacked())
            return "Attacker card has already attacked this turn.";

        if (cardAttacker.isFrozen())
            return "Attacker card is frozen.";

        if (!(cardAttacked instanceof Tank)) {
            if (currPlayerIdx == 1) {
                for (Minion minion : table.get(1))
                    if (minion instanceof Tank) {
                        return "Attacked card is not of type 'Tank'.";
                    }
            } else {
                for (Minion minion : table.get(2))
                    if (minion instanceof Tank) {
                        return "Attacked card is not of type 'Tank'.";
                    }
            }
        }

        cardAttacker.attackMinion(cardAttacked);

        if (cardAttacked.getHealth() == 0)
            table.get(x2).remove(cardAttacked);

        return null;
    }

    public String minionUseAbility(int x1, int y1, int x2, int y2) {
        Minion cardAttacker, cardAttacked;

        try {
            cardAttacker = table.get(x1).get(y1);
            cardAttacked = table.get(x2).get(y2);
        }
        catch (Exception e) {
            return null;
        }

        if (cardAttacker.isFrozen())
            return "Attacker card is frozen.";

        if (cardAttacker.hasAttacked())
            return "Attacker card has already attacked this turn.";

        if (cardAttacker instanceof Disciple) {
            if ((currPlayerIdx == 1 && (x2 == 1 || x2 == 0)) || (currPlayerIdx == 2 && (x2 == 3 || x2 == 2)))
            return "Attacked card does not belong to the current player.";
        } else if (cardAttacker instanceof TheRipper || cardAttacker instanceof Miraj ||
                    cardAttacker instanceof TheCursedOne) {
            if ((currPlayerIdx == 1 && (x2 == 3 || x2 == 2)) || (currPlayerIdx == 2 && (x2 == 1 || x2 == 0)))
                return "Attacked card does not belong to the enemy.";

            if (!(cardAttacked instanceof Tank)) {
                if (currPlayerIdx == 1) {
                    for (Minion minion : table.get(1))
                        if (minion instanceof Tank) {
                            return "Attacked card is not of type 'Tank'.";
                        }
                } else {
                    for (Minion minion : table.get(2))
                        if (minion instanceof Tank) {
                            return "Attacked card is not of type 'Tank'.";
                        }
                }
            }
        }

        if (cardAttacker instanceof Miraj)
            ((Miraj)cardAttacker).action(cardAttacked);
        if (cardAttacker instanceof TheRipper)
            ((TheRipper)cardAttacker).action(cardAttacked);
        if (cardAttacker instanceof TheCursedOne)
            ((TheCursedOne)cardAttacker).action(cardAttacked);
        if (cardAttacker instanceof Disciple)
            ((Disciple)cardAttacker).action(cardAttacked);

        if (cardAttacked.getHealth() == 0)
            table.get(x2).remove(cardAttacked);

        return null;
    }

    public String minionAttackHero(int x, int y) {
        Minion cardAttacker;

        try {
            cardAttacker = table.get(x).get(y);
        }
        catch (Exception e){
            return null;
        }

        if (cardAttacker.isFrozen())
            return "Attacker card is frozen.";

        if (cardAttacker.hasAttacked())
            return "Attacker card has already attacked this turn.";

        if (currPlayerIdx == 1) {
            for (Minion minion : table.get(1))
                if (minion instanceof Tank) {
                    return "Attacked card is not of type 'Tank'.";
                }
        } else {
            for (Minion minion : table.get(2))
                if (minion instanceof Tank) {
                    return "Attacked card is not of type 'Tank'.";
                }
        }

        Hero hero = this.getPlayerHero(3 - currPlayerIdx);
        cardAttacker.attackHero(hero);

        if (hero.getHealth() == 0)
            return Integer.toString(currPlayerIdx);

        return null;
    }

    public String useHeroAbility(int affectedRow) {
        Hero hero = this.getCurrPlayer().getHero();

        if (this.getCurrPlayer().getMana() < hero.getMana())
            return "Not enough mana to use hero's ability.";

        if (hero.hasAttacked())
            return "Hero has already attacked this turn.";

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            if ((currPlayerIdx == 1 && (affectedRow == 3 || affectedRow == 2)) || (currPlayerIdx == 2 &&
                    (affectedRow == 1 || affectedRow == 0))) {
                System.out.println(affectedRow);
                System.out.println(currPlayerIdx);
                return "Selected row does not belong to the enemy.";
            }
        } else if (hero instanceof GeneralKocioraw || hero instanceof KingMudface) {
            if ((currPlayerIdx == 1 && (affectedRow == 1 || affectedRow == 0)) || (currPlayerIdx == 2 &&
                    (affectedRow == 3 || affectedRow == 2))) {
                return "Selected row does not belong to the current player.";
            }
        }

        ArrayList<Minion> minions = this.getTable().get(affectedRow);

        if (hero instanceof EmpressThorina)
            ((EmpressThorina)hero).action(minions);

        if (hero instanceof GeneralKocioraw)
            ((GeneralKocioraw)hero).action(minions);

        if (hero instanceof KingMudface)
            ((KingMudface)hero).action(minions);

        if (hero instanceof LordRoyce)
            ((LordRoyce)hero).action(minions);

        this.getCurrPlayer().setMana(this.getCurrPlayer().getMana() - hero.getMana());
        hero.setHasAttacked(true);
        return null;
    }

}
