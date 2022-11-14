package setup;

import fileio.*;
import implementation.Game;
import implementation.Player;
import implementation.card.Card;
import implementation.card.environment.Firestorm;
import implementation.card.environment.HeartHound;
import implementation.card.environment.Winterfell;
import implementation.card.hero.*;
import implementation.card.minion.*;

import java.util.ArrayList;

public class Setup {
    public static Player setupPlayer(Input inputData, int nr) {
        DecksInput decksData;

        if (nr == 1)
            decksData = inputData.getPlayerOneDecks();
        else
            decksData = inputData.getPlayerTwoDecks();

        ArrayList<ArrayList<Card>> decksPlayer = new ArrayList<>();
        for (int i = 0; i < decksData.getNrDecks(); i++) {
            ArrayList<Card> deck = new ArrayList<>();

            for (int j = 0; j < decksData.getNrCardsInDeck(); j++) {
                Card card;

                CardInput cardInput = decksData.getDecks().get(i).get(j);

                int mana = cardInput.getMana();
                String description = cardInput.getDescription();
                ArrayList<String> colors = cardInput.getColors();
                int attackDamage = cardInput.getAttackDamage();
                int health = cardInput.getHealth();
                String name = cardInput.getName();

                card = switch (name) {
                    case "Goliath", "Warden" -> new Tank(mana, description, colors, health, attackDamage, name);
                    case "Miraj" -> new Miraj(mana, description, colors, health, attackDamage, name);
                    case "The Ripper" -> new TheRipper(mana, description, colors, health, attackDamage, name);
                    case "The Cursed One" -> new TheCursedOne(mana, description, colors, health, attackDamage, name);
                    case "Disciple" -> new Disciple(mana, description, colors, health, attackDamage, name);
                    case "Firestorm" -> new Firestorm(mana, description, colors, name);
                    case "Winterfell" -> new Winterfell(mana, description, colors, name);
                    case "Heart Hound" -> new HeartHound(mana, description, colors, name);
                    default -> new Minion(mana, description, colors, health, attackDamage, name);
                };

                deck.add(card);
            }

            decksPlayer.add(deck);
        }

        return new Player(decksData.getNrCardsInDeck(), decksData.getNrDecks(), decksPlayer);
    }

    public static Hero setupHero(CardInput heroInput) {
        String description = heroInput.getDescription();
        int mana = heroInput.getMana();
        ArrayList<String> colors = heroInput.getColors();
        String name = heroInput.getName();

        return switch (name) {
            case "EmpressThorina" -> new EmpressThorina(mana, description, colors, name);
            case "KingMudface" -> new KingMudface(mana, description, colors, name);
            case "GeneralKocioraw" -> new GeneralKocioraw(mana, description, colors, name);
            default -> new LordRoyce(mana, description, colors, name);
        };

    }

    public static void setupGame(Input inputData, int nr, Game game) {
        StartGameInput startGameInput = inputData.getGames().get(nr).getStartGame();

        int playerOneIdx = startGameInput.getPlayerOneDeckIdx();
        int playerTwoIdx = startGameInput.getPlayerTwoDeckIdx();
        int shuffleSeed = startGameInput.getShuffleSeed();
        Hero hero1 = setupHero(startGameInput.getPlayerOneHero());
        Hero hero2 = setupHero(startGameInput.getPlayerTwoHero());
        int startingPlayer = startGameInput.getStartingPlayer();

        game.setup(playerOneIdx, playerTwoIdx, shuffleSeed, hero1, hero2, startingPlayer);

        game.getPlayer1().addCardHand();
        game.getPlayer2().addCardHand();
    }
}
