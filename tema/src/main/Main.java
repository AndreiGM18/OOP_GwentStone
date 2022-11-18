package main;

import checker.Checker;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import implementation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.Input;
import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.minion.Minion;
import implementation.card.utils.MaxbyATK;
import implementation.card.utils.MaxbyHP;
import setup.Setup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation
        Player player1 = Setup.setupPlayer(inputData, 1);
        Player player2 = Setup.setupPlayer(inputData, 2);

        for (int i = 0; i < inputData.getGames().size(); i++) {
            player1.setHand(new ArrayList<>());
            player2.setHand(new ArrayList<>());
            System.out.println(player1.getHand());
            System.out.println(player2.getHand());
            Game game = new Game(player1, player2);
            Setup.setupGame(inputData, i, game);

            ArrayList<ActionsInput> actionsInput = inputData.getGames().get(i).getActions();

            for (ActionsInput action : actionsInput) {

                String command = action.getCommand();

                ObjectNode node = objectMapper.createObjectNode();

                if (command.equals("getPlayerDeck")) {
                    node.put("command", command);

                    int playerIdx = action.getPlayerIdx();

                    node.put("playerIdx", playerIdx);

                    ArrayList<Card> cardss = game.getPlayerbyIdx(playerIdx).getCurrDeck();

                    ArrayNode cardsnNode = objectMapper.createArrayNode();
                    for (Card card : cardss) {
                        cardsnNode.add(card.createCardNode());
                    }

                    node.set("output", cardsnNode);
                    output.add(node);
                }

                if (command.equals("getPlayerHero")) {
                    node.put("command", command);

                    int playerIdx = action.getPlayerIdx();
                    node.put("playerIdx", playerIdx);

                    ObjectNode heroNode = game.getPlayerbyIdx(playerIdx).getHero().createCardNode();

                    node.set("output", heroNode);
                    output.add(node);
                }

                if (command.equals("getPlayerTurn")) {
                    node.put("command", command);

                    node.put("output", game.getCurrPlayerIdx());
                    output.add(node);
                }

                if (command.equals("endPlayerTurn")) {
                    game.nextTurn();
                }

                if (command.equals("placeCard")) {
                    int handIdx = action.getHandIdx();

                    String error = game.placeCard(handIdx);

                    if (error != null) {
                        node.put("command", command);
                        node.put("handIdx", handIdx);
                        node.put("error", error);

                        output.add(node);
                    }
                }

                if (command.equals("getCardsInHand")) {
                    node.put("command", command);

                    int playerIdx = action.getPlayerIdx();
                    node.put("playerIdx", playerIdx);

                    ArrayList<Card> cards = game.getPlayerbyIdx(playerIdx).getHand();

                    ArrayNode cardsNode = objectMapper.createArrayNode();
                    for (Card card : cards) {
                        cardsNode.add(card.createCardNode());
                    }

                    node.set("output", cardsNode);
                    output.add(node);
                }

                if (command.equals("getPlayerMana")) {
                    node.put("command", command);

                    int playerIdx = action.getPlayerIdx();
                    node.put("playerIdx", playerIdx);

                    node.put("output", game.getPlayerbyIdx(playerIdx).getMana());

                    output.add(node);
                }

                if (command.equals("getCardsOnTable")) {
                    node.put("command", command);

                    ArrayList<ArrayList<Minion>> cardsOnTable = game.getTable();

                    ArrayNode cardsNode = objectMapper.createArrayNode();
                    for (ArrayList<Minion> cards : cardsOnTable) {
                        ArrayNode cardArrayNode = objectMapper.createArrayNode();
                        for (Minion card : cards)  {
                            cardArrayNode.add(card.createCardNode());
                        }

                        cardsNode.add(cardArrayNode);
                    }

                    node.set("output", cardsNode);
                    output.add(node);
                }


                if (command.equals("useEnvironmentCard")) {
                    int handIdx = action.getHandIdx();
                    int affectedRow = action.getAffectedRow();

                    String error = game.useEnvironmentCard(handIdx, affectedRow);

                    if (error != null) {
                        node.put("command", command);
                        node.put("handIdx", handIdx);
                        node.put("affectedRow", affectedRow);
                        node.put("error", error);
                        output.add(node);
                    }
                }

                if (command.equals("getEnvironmentCardsInHand")) {
                    int playerIdx = action.getPlayerIdx();

                    ArrayList<Environment> envs = game.getEnvironmentCardsInHand(playerIdx);

                    node.put("command", command);
                    node.put("playerIdx", playerIdx);
                    ArrayNode cardsNode = objectMapper.createArrayNode();

                    for (Environment env : envs)
                        cardsNode.add(env.createCardNode());

                    node.set("output", cardsNode);
                    output.add(node);
                }

                if (command.equals("getFrozenCardsOnTable")) {
                    node.put("command", command);

                    ArrayList<Minion> frozenCards = game.getFrozenCards();

                    ArrayNode cardsNode = objectMapper.createArrayNode();

                    for (Minion minion : frozenCards)
                        cardsNode.add(minion.createCardNode());

                    node.set("output", cardsNode);
                    output.add(node);

                }

                if (command.equals("getCardAtPosition")) {
                    int x = action.getX();
                    int y = action.getY();

                    Card card = game.getCardAtPosition(x, y);

                    node.put("command", command);
                    node.put("x", x);
                    node.put("y", y);
                    if (card != null) {
                        node.set("output", card.createCardNode());
                        output.add(node);
                    }
                    else {
                        node.put("output", "No card available at that position.");
                        output.add(node);
                    }
                }

                if (command.equals("cardUsesAttack")) {
                    int x1 = action.getCardAttacker().getX();
                    int y1 = action.getCardAttacker().getY();
                    int x2 = action.getCardAttacked().getX();
                    int y2 = action.getCardAttacked().getY();

                    String error = game.attackMinion(x1, y1, x2, y2);

                    if (error != null) {
                        node.put("command", command);

                        ObjectNode attackerNode = objectMapper.createObjectNode();
                        ObjectNode attackedNode = objectMapper.createObjectNode();

                        attackerNode.put("x", x1);
                        attackerNode.put("y", y1);
                        node.set("cardAttacker", attackerNode);

                        attackedNode.put("x", x2);
                        attackedNode.put("y", y2);
                        node.set("cardAttacked", attackedNode);

                        node.put("error", error);

                        output.add(node);
                    }
                }

                if (command.equals("cardUsesAbility")) {
                    int x1 = action.getCardAttacker().getX();
                    int y1 = action.getCardAttacker().getY();
                    int x2 = action.getCardAttacked().getX();
                    int y2 = action.getCardAttacked().getY();

                    String error = game.minionUseAbility(x1, y1, x2, y2);

                    if (error != null) {
                        node.put("command", command);

                        ObjectNode attackerNode = objectMapper.createObjectNode();
                        ObjectNode attackedNode = objectMapper.createObjectNode();

                        attackerNode.put("x", x1);
                        attackerNode.put("y", y1);
                        node.set("cardAttacker", attackerNode);

                        attackedNode.put("x", x2);
                        attackedNode.put("y", y2);
                        node.set("cardAttacked", attackedNode);

                        node.put("error", error);

                        output.add(node);
                    }
                }

                if (command.equals("useAttackHero")) {
                    int x = action.getCardAttacker().getX();
                    int y = action.getCardAttacker().getY();

                    String s = game.minionAttackHero(x, y);

                    if (s != null) {

                        if (s.equals("1")) {
                            node.put("gameEnded", "Player one killed the enemy hero.");
                            output.add(node);
                            player1.setWins(player1.getWins() + 1);
                        }

                        else if (s.equals("2")) {
                            node.put("gameEnded", "Player two killed the enemy hero.");
                            output.add(node);
                            player2.setWins(player2.getWins() + 1);
                        }

                        else {
                            node.put("command", command);
                            ObjectNode attackerNode = objectMapper.createObjectNode();
                            attackerNode.put("x", x);
                            attackerNode.put("y", y);
                            node.set("cardAttacker", attackerNode);

                            node.put("error", s);
                            output.add(node);
                        }
                    }
                }

                if (command.equals("useHeroAbility")) {
                    int affectedRow = action.getAffectedRow();

                    String error = game.useHeroAbility(affectedRow);

                    if (error != null) {
                        node.put("command", command);
                        node.put("affectedRow", affectedRow);
                        node.put("error", error);

                        output.add(node);
                    }
                }

                if (command.equals("getPlayerOneWins")) {
                    node.put("command", command);
                    node.put("output", player1.getWins());

                    output.add(node);
                }

                if (command.equals("getPlayerTwoWins")) {
                    node.put("command", command);
                    node.put("output", player2.getWins());

                    output.add(node);
                }

                if (command.equals("getTotalGamesPlayed")) {
                    node.put("command", command);
                    node.put("output", i + 1);

                    output.add(node);
                }
            }
        }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
