package commandhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.Input;
import implementation.Game;
import implementation.Player;
import implementation.card.Card;
import implementation.card.environment.Environment;
import implementation.card.minion.Minion;
import constants.Constants;
import setup.Setup;
import java.util.ArrayList;

public final class CommandHandler {
    private CommandHandler() {
    }

    /**
     *
     * @param node
     * @param error
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    static void handleAttackerAttackedError(final ObjectNode node, final String error,
                                            final int x1, final int y1,
                                            final int x2, final int y2) {
        if (error != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode attackerNode = objectMapper.createObjectNode();
            ObjectNode attackedNode = objectMapper.createObjectNode();

            attackerNode.put(Constants.Command.X, x1);
            attackerNode.put(Constants.Command.Y, y1);
            node.set(Constants.Command.CARD_ATTACKER, attackerNode);

            attackedNode.put(Constants.Command.X, x2);
            attackedNode.put(Constants.Command.Y, y2);

            node.set(Constants.Command.CARD_ATTACKED, attackedNode);

            node.put(Constants.Error.ERROR, error);
        }
    }

    static void handleAttackerError(final ObjectNode node, final String s,
                                    final int x, final int y) {
        if (s != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode attackerNode = objectMapper.createObjectNode();

            attackerNode.put(Constants.Command.X, x);
            attackerNode.put(Constants.Command.Y, y);

            node.set(Constants.Command.CARD_ATTACKER, attackerNode);

            node.put(Constants.Error.ERROR, s);
        }
    }

    static void getPlayerDeck(final Game game, final ActionsInput action,
                              final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_PLAYER_DECK);

        int playerIdx = action.getPlayerIdx();
        node.put(Constants.Command.PLAYER_IDX, playerIdx);

        ArrayList<Card> cards = game.getPlayerByIdx(playerIdx).getCurrDeck();
        ArrayNode cardsNode = objectMapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.createCardNode());
        }

        node.set(Constants.Output.OUTPUT, cardsNode);

        output.add(node);
    }

    static void getPlayerHero(final Game game, final ActionsInput action,
                              final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_PLAYER_HERO);

        int playerIdx = action.getPlayerIdx();
        node.put(Constants.Command.PLAYER_IDX, playerIdx);

        ObjectNode heroNode = game.getPlayerByIdx(playerIdx).getHero().createCardNode();
        node.set(Constants.Output.OUTPUT, heroNode);

        output.add(node);
    }

    static void getPlayerTurn(final Game game, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_PLAYER_TURN);

        node.put(Constants.Output.OUTPUT, game.getCurrPlayerIdx());

        output.add(node);
    }

    static void placeCard(final Game game, final ActionsInput action,
                          final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int handIdx = action.getHandIdx();
        String error = game.placeCard(handIdx);

        if (error != null) {
            node.put(Constants.Command.COMMAND, Constants.Command.PLACE_CARD);

            node.put(Constants.Command.HAND_IDX, handIdx);

            node.put(Constants.Error.ERROR, error);

            output.add(node);
        }
    }

    static void getCardsInHand(final Game game, final ActionsInput action,
                               final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_CARDS_IN_HAND);

        int playerIdx = action.getPlayerIdx();
        node.put(Constants.Command.PLAYER_IDX, playerIdx);

        ArrayList<Card> cards = game.getPlayerByIdx(playerIdx).getHand();
        ArrayNode cardsNode = objectMapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.createCardNode());
        }

        node.set(Constants.Output.OUTPUT, cardsNode);
        output.add(node);
    }

    static void getPlayerMana(final Game game, final ActionsInput action,
                              final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_PLAYER_MANA);

        int playerIdx = action.getPlayerIdx();
        node.put(Constants.Command.PLAYER_IDX, playerIdx);

        node.put(Constants.Output.OUTPUT, game.getPlayerByIdx(playerIdx).getMana());

        output.add(node);
    }

    static void getCardsOnTable(final Game game, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_CARDS_ON_TABLE);

        ArrayList<ArrayList<Minion>> cardsOnTable = game.getTable();
        ArrayNode cardsNode = objectMapper.createArrayNode();
        for (ArrayList<Minion> cards : cardsOnTable) {
            ArrayNode cardArrayNode = objectMapper.createArrayNode();

            for (Minion card : cards)  {
                cardArrayNode.add(card.createCardNode());
            }

            cardsNode.add(cardArrayNode);
        }

        node.set(Constants.Output.OUTPUT, cardsNode);

        output.add(node);
    }

    static void useEnvironmentCard(final Game game, final ActionsInput action,
                                   final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int handIdx = action.getHandIdx();
        int affectedRow = action.getAffectedRow();

        String error = game.useEnvironmentCard(handIdx, affectedRow);

        if (error != null) {
            node.put(Constants.Command.COMMAND, Constants.Command.USE_ENVIRONMENT_CARD);

            node.put(Constants.Command.HAND_IDX, handIdx);

            node.put(Constants.Command.AFFECTED_ROW, affectedRow);

            node.put(Constants.Error.ERROR, error);

            output.add(node);
        }
    }

    static void getEnvironmentCardsInHand(final Game game, final ActionsInput action,
                                          final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int playerIdx = action.getPlayerIdx();

        ArrayList<Environment> envs = game.getEnvironmentCardsInHand(playerIdx);

        node.put(Constants.Command.COMMAND, Constants.Command.GET_ENVIRONMENT_CARDS_IN_HAND);

        node.put(Constants.Command.PLAYER_IDX, playerIdx);

        ArrayNode cardsNode = objectMapper.createArrayNode();
        for (Environment env : envs) {
            cardsNode.add(env.createCardNode());
        }

        node.set(Constants.Output.OUTPUT, cardsNode);

        output.add(node);
    }

    static void getFrozenCardsOnTable(final Game game, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        node.put(Constants.Command.COMMAND, Constants.Command.GET_FROZEN_CARDS_ON_TABLE);

        ArrayList<Minion> frozenCards = game.getFrozenCards();
        ArrayNode cardsNode = objectMapper.createArrayNode();
        for (Minion minion : frozenCards) {
            cardsNode.add(minion.createCardNode());
        }

        node.set(Constants.Output.OUTPUT, cardsNode);

        output.add(node);
    }

    static void getCardAtPosition(final Game game, final ActionsInput action,
                                  final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int x = action.getX();
        int y = action.getY();

        Card card = game.getCardAtPosition(x, y);

        node.put(Constants.Command.COMMAND, Constants.Command.GET_CARD_AT_POSITION);

        node.put(Constants.Command.X, x);

        node.put(Constants.Command.Y, y);

        if (card != null) {
            node.set(Constants.Output.OUTPUT, card.createCardNode());
        } else {
            node.put(Constants.Output.OUTPUT, Constants.Output.NO_CARD_FOUND);
        }

        output.add(node);
    }

    static void cardUsesAttack(final Game game, final ActionsInput action,
                               final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int x2 = action.getCardAttacked().getX();
        int y2 = action.getCardAttacked().getY();
        int x1 = action.getCardAttacker().getX();
        int y1 = action.getCardAttacker().getY();

        String error = game.attackMinion(x1, y1, x2, y2);

        if (error != null) {
            node.put(Constants.Command.COMMAND, Constants.Command.CARD_USES_ATTACK);

            CommandHandler.handleAttackerAttackedError(node, error, x1, y1, x2, y2);

            output.add(node);
        }
    }

    static void cardUsesAbility(final Game game, final ActionsInput action,
                                final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int x1 = action.getCardAttacker().getX();
        int y1 = action.getCardAttacker().getY();
        int x2 = action.getCardAttacked().getX();
        int y2 = action.getCardAttacked().getY();

        String error = game.minionUseAbility(x1, y1, x2, y2);
        if (error != null) {
            node.put(Constants.Command.COMMAND, Constants.Command.CARD_USES_ABILITY);
            CommandHandler.handleAttackerAttackedError(node, error, x1, y1, x2, y2);
            output.add(node);
        }
    }

    static void useAttackHero(final Game game, final Player player1, final Player player2,
                              final ActionsInput action, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int x = action.getCardAttacker().getX();
        int y = action.getCardAttacker().getY();

        String s = game.minionAttackHero(x, y);

        if (s != null) {
            if (s.equals(Constants.Error.ONE)) {
                node.put(Constants.Output.GAME_ENDED, Constants.Output.PLAYER_ONE_WON);

                output.add(node);

                player1.setWins(player1.getWins() + 1);
            } else if (s.equals(Constants.Error.TWO)) {
                node.put(Constants.Output.GAME_ENDED, Constants.Output.PLAYER_TWO_WON);

                output.add(node);

                player2.setWins(player2.getWins() + 1);
            } else {
                node.put(Constants.Command.COMMAND, Constants.Command.USE_ATTACK_HERO);

                CommandHandler.handleAttackerError(node, s, x, y);

                output.add(node);
            }
        }
    }

    static void useHeroAbility(final Game game, final ActionsInput action,
                               final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        int affectedRow = action.getAffectedRow();

        String error = game.useHeroAbility(affectedRow);

        if (error != null) {
            node.put(Constants.Command.COMMAND, Constants.Command.USE_HERO_ABILITY);

            node.put(Constants.Command.AFFECTED_ROW, affectedRow);

            node.put(Constants.Error.ERROR, error);

            output.add(node);
        }
    }
    /**
     *
     * @param inputData
     * @param output
     */
    public static void commandHandler(final Input inputData, final ArrayNode output) {
        ObjectMapper objectMapper = new ObjectMapper();

        Player player1 = Setup.setupPlayer(inputData, 1);
        Player player2 = Setup.setupPlayer(inputData, 2);

        for (int i = 0; i < inputData.getGames().size(); i++) {
            player1.setHand(new ArrayList<>());
            player2.setHand(new ArrayList<>());

            Game game = new Game(player1, player2);

            Setup.setupGame(inputData, i, game);

            ArrayList<ActionsInput> actionsInput = inputData.getGames().get(i).getActions();

            for (ActionsInput action : actionsInput) {
                String command = action.getCommand();

                if (command.equals(Constants.Command.GET_PLAYER_DECK)) {
                    CommandHandler.getPlayerDeck(game, action, output);
                }

                if (command.equals(Constants.Command.GET_PLAYER_HERO)) {
                    CommandHandler.getPlayerHero(game, action, output);
                }

                if (command.equals(Constants.Command.GET_PLAYER_TURN)) {
                    CommandHandler.getPlayerTurn(game, output);
                }

                if (command.equals(Constants.Command.END_PLAYER_TURN)) {
                    game.nextTurn();
                }

                if (command.equals(Constants.Command.PLACE_CARD)) {
                    CommandHandler.placeCard(game, action, output);
                }

                if (command.equals(Constants.Command.GET_CARDS_IN_HAND)) {
                    CommandHandler.getCardsInHand(game, action, output);
                }

                if (command.equals(Constants.Command.GET_PLAYER_MANA)) {
                    CommandHandler.getPlayerMana(game, action, output);
                }

                if (command.equals(Constants.Command.GET_CARDS_ON_TABLE)) {
                    CommandHandler.getCardsOnTable(game, output);
                }

                if (command.equals(Constants.Command.USE_ENVIRONMENT_CARD)) {
                    CommandHandler.useEnvironmentCard(game, action, output);
                }

                if (command.equals(Constants.Command.GET_ENVIRONMENT_CARDS_IN_HAND)) {
                   CommandHandler.getEnvironmentCardsInHand(game, action, output);
                }

                if (command.equals(Constants.Command.GET_FROZEN_CARDS_ON_TABLE)) {
                    CommandHandler.getFrozenCardsOnTable(game, output);
                }

                if (command.equals(Constants.Command.GET_CARD_AT_POSITION)) {
                    CommandHandler.getCardAtPosition(game, action, output);
                }

                if (command.equals(Constants.Command.CARD_USES_ATTACK)) {
                    CommandHandler.cardUsesAttack(game, action, output);
                }

                if (command.equals(Constants.Command.CARD_USES_ABILITY)) {
                    CommandHandler.cardUsesAbility(game, action, output);
                }

                if (command.equals(Constants.Command.USE_ATTACK_HERO)) {
                    CommandHandler.useAttackHero(game, player1, player2, action, output);
                }

                if (command.equals(Constants.Command.USE_HERO_ABILITY)) {
                    CommandHandler.useHeroAbility(game, action, output);
                }

                if (command.equals(Constants.Command.GET_PLAYER_ONE_WINS)) {
                    ObjectNode node = objectMapper.createObjectNode();

                    node.put(Constants.Command.COMMAND, command);

                    node.put(Constants.Output.OUTPUT, player1.getWins());

                    output.add(node);
                }

                if (command.equals(Constants.Command.GET_PLAYER_TWO_WINS)) {
                    ObjectNode node = objectMapper.createObjectNode();

                    node.put(Constants.Command.COMMAND, command);

                    node.put(Constants.Output.OUTPUT, player2.getWins());

                    output.add(node);
                }

                if (command.equals(Constants.Command.GET_TOTAL_GAMES_PLAYED)) {
                    ObjectNode node = objectMapper.createObjectNode();

                    node.put(Constants.Command.COMMAND, command);

                    node.put(Constants.Output.OUTPUT, i + 1);

                    output.add(node);
                }
            }
        }
    }
}
