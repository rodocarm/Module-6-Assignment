

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);

            System.out.print("How many players? ");
            int players = Integer.parseInt(keyboard.nextLine());

            DiceGame diceGame = new DiceGame(players, 5, 3);

            char response;
            do {
                playGame(diceGame, keyboard);

                System.out.print("\nAnother game? (y/n) ");
                try {
                    response = keyboard.nextLine().toLowerCase().charAt(0);
                } catch (StringIndexOutOfBoundsException ex) {
                    System.out.println("Bad input... exiting.");
                    response = 'n';
                }
            } while (response == 'y');

            System.out.printf("And the grand champion is... %s", diceGame.getFinalWinner());

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void playGame(DiceGame game, Scanner keyboard) {
        game.startNewGame();

        do {
            System.out.printf("PLAYER %d's TURN%n", game.getCurrentPlayerNumber());

            game.resetDice();

            while (game.currentPlayerCanRoll()) {
                game.rollDice();

                if (game.autoHold(6) && game.autoHold(5) && game.autoHold(4)) {

                    System.out.println(game.getDiceResults());

                    if (game.currentPlayerCanRoll()) {

                        char choice;
                        do {
                            System.out.print("Enter letter of cargo die to hold, or (r) to reroll now: ");
                            try {
                                choice = keyboard.nextLine().toLowerCase().charAt(0);
                            } catch (StringIndexOutOfBoundsException ex) {
                                System.out.println("Bad command. Rerolling...");
                                choice = 'r';
                            }

                            if (choice != 'r') {
                                game.playerHold(choice);

                                System.out.println(game.getDiceResults());
                            }
                        } while (choice != 'r');
                    }
                } else {
                    System.out.println(game.getDiceResults());

                    System.out.println("You don't have ship, captain, and crew. Press enter to continue...");
                    keyboard.nextLine();
                }
            }

            game.scoreCurrentPlayer();

            System.out.printf("PLAYER %d's SCORE: %d %n%n",
                    game.getCurrentPlayerNumber(), game.getCurrentPlayerScore());

            System.out.println("Press enter for next player...");
            keyboard.nextLine();
        } while (game.nextPlayer());

        System.out.println(game.getGameResults());
    }

}
