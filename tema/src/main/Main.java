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

        for (int i = 0; i < inputData.getGames().size(); ++i) {
            Game game = new Game(player1, player2);
            Setup.setupGame(inputData, i, game);

            ArrayList<ActionsInput> actionsInput = inputData.getGames().get(i).getActions();

            for (int j = 0; j < actionsInput.size(); ++j) {

                ActionsInput action = actionsInput.get(j);
                String command = action.getCommand();

                ObjectNode node = objectMapper.createObjectNode();
                node.put("command", command);

                if (command.equals("getPlayerDeck")) {
                    int playerIdx = action.getPlayerIdx();

                    node.put("playerIdx", playerIdx);

                    ArrayList<Card> cards = game.getPlayerbyIdx(playerIdx).getCurrDeck();

                    ArrayNode cardsNode = objectMapper.createArrayNode();
                    for (int k = 0; k < cards.size(); k++) {
                        //System.out.println(cards.get(k).toString());
                        cardsNode.add(cards.get(k).createCardNode());
                    }

                    node.set("output", cardsNode);
                    output.add(node);
                }

                if (command.equals("getPlayerHero")) {
                    int playerIdx = action.getPlayerIdx();
                    node.put("playerIdx", playerIdx);

                    ObjectNode heroNode = objectMapper.createObjectNode();
                    heroNode = game.getPlayerbyIdx(playerIdx).getHero().createCardNode();

                    node.set("output", heroNode);
                    output.add(node);
                }

                if (command.equals("getPlayerTurn")) {
                    node.put("output", game.getCurrPlayerIdx());
                    output.add(node);
                }

//                if (command.equals("placeCard")) {
//                    Card card = game.getCurrPlayer().getHand().get(action.getHandIdx());
//
//                    if (card instanceof Environment)
//
//                    int frontRow, backRow;
//                    if (game.getCurrPlayerIdx() == 1) {
//                        frontRow = 2;
//                        backRow = 3;
//                    } else {
//                        frontRow = 1;
//                        backRow = 0;
//                    }
//
//                    if (card instanceof TheRipper || card instanceof Miraj || card instanceof Tank)
//                        game.getTable().get(frontRow).add((Minion) card);
//                    else
//                        game.getTable().get(backRow).add((Minion) card);
//
//                    return "";
//                }
            }
        }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
