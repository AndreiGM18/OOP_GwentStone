package constants;

public class Constants {
    public static class Command {
        public static final String COMMAND = "command";
        public static final String GET_PLAYER_DECK = "getPlayerDeck";
        public static final String PLAYER_IDX = "playerIdx";
        public static final String GET_PLAYER_HERO = "getPlayerHero";
        public static final String GET_PLAYER_TURN = "getPlayerTurn";
        public static final String END_PLAYER_TURN = "endPlayerTurn";
        public static final String PLACE_CARD = "placeCard";
        public static final String HAND_IDX = "handIdx";
        public static final String GET_CARDS_IN_HAND = "getCardsInHand";
        public static final String GET_PLAYER_MANA = "getPlayerMana";
        public static final String GET_CARDS_ON_TABLE = "getCardsOnTable";
        public static final String USE_ENVIRONMENT_CARD = "useEnvironmentCard";
        public static final String GET_ENVIRONMENT_CARDS_IN_HAND = "getEnvironmentCardsInHand";
        public static final String GET_FROZEN_CARDS_ON_TABLE = "getFrozenCardsOnTable";
        public static final String GET_CARD_AT_POSITION = "getCardAtPosition";
        public static final String CARD_USES_ATTACK = "cardUsesAttack";
        public static final String CARD_USES_ABILITY = "cardUsesAbility";
        public static final String X = "x";
        public static final String Y = "y";
        public static final String CARD_ATTACKER = "cardAttacker";
        public static final String CARD_ATTACKED = "cardAttacked";
        public static final String USE_ATTACK_HERO = "useAttackHero";
        public static final String USE_HERO_ABILITY = "useHeroAbility";
        public static final String AFFECTED_ROW = "affectedRow";
        public static final String GET_PLAYER_ONE_WINS = "getPlayerOneWins";
        public static final String GET_PLAYER_TWO_WINS = "getPlayerTwoWins";
        public static final String GET_TOTAL_GAMES_PLAYED = "getTotalGamesPlayed";

    }

    public static class Error {
        public static final String ERROR = "error";
        public static final String ONE = "1";
        public static final String TWO = "2";
        public static final String ROW_FULL =
                "Cannot steal enemy card since the player's row is full.";
        public static final String CHOSEN_ROW_NOT_ENEMY =
                "Chosen row does not belong to the enemy.";
        public static final String ENVIRONMENT_NO_MANA =
                "Not enough mana to use environment card.";
        public static final String NOT_ENVIRONMENT =
                "Chosen card is not of type environment.";
        public static final String CARD_NOT_ENEMY =
                "Attacked card does not belong to the enemy.";
        public static final String CARD_ALREADY_ATTACKED =
                "Attacker card has already attacked this turn.";
        public static final String FROZEN =
                "Attacker card is frozen.";
        public static final String NOT_TANK =
                "Attacked card is not of type 'Tank'.";
        public static final String CARD_NOT_CURRENT =
                "Attacked card does not belong to the current player.";
        public static final String HERO_NO_MANA =
                "Not enough mana to use hero's ability.";
        public static final String HERO_ALREADY_ATTACKED =
                "Hero has already attacked this turn.";
        public static final String SELECTED_ROW_NOT_ENEMY =
                "Selected row does not belong to the enemy.";
        public static final String SELECTED_ROW_NOT_CURRENT =
                "Selected row does not belong to the current player.";
    }

    public static class Output {
        public static final String OUTPUT = "output";
        public static final String NO_CARD_FOUND = "No card available at that position.";
        public static final String GAME_ENDED = "gameEnded";
        public static final String PLAYER_ONE_WON = "Player one killed the enemy hero.";
        public static final String PLAYER_TWO_WON = "Player two killed the enemy hero.";
    }

    public static class Integers {
        public static final int HERO_STARTING_HP = 30;
        public static final int STARTING_MANA = 1;
        public static final int PLAYER_ONE_FRONT_ROW = 2;
        public static final int PLAYER_ONE_BACK_ROW = 3;
        public static final int PLAYER_TWO_FRONT_ROW = 1;
        public static final int PLAYER_TWO_BACK_ROW = 0;
        public static final int NUMBER_OF_ROWS = 4;
        public static final int MAX_MANA_INCREASE = 10;
        public static final int MAX_CARDS = 5;
        public static final int MIRROR = 3;
    }
}
