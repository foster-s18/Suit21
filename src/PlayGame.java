import java.util.Scanner;

public class PlayGame {
    private static int gameNumber;
    private final Player[] players;
    private Replay replay;
    private static final Scanner scanner = new Scanner(System.in);
    private static Deck deck;
    private String cardSelection;
    private Card newCard;
    private Card replacedCard;
    private final int totalGames;
    private final PlayerScoreCalculator playerScores;

    /**
     * Constructor for initializing the game with players and total number of games.
     *
     * @param players Array of Player objects
     * @param totalGames Total number of games to be played
     */
    public PlayGame(Player[] players, int totalGames) {
        this.players = players;
        this.totalGames = totalGames;
        playerScores = new PlayerScoreCalculator(players);
        playerScores.initialisePlayerScores(players);
        startGame(totalGames);
    }

    /**
     * Starts the game loop. Handles the rounds of the game, and proceeds until all games are finished.
     *
     * @param gamesRemaining The number of remaining games
     */
    private void startGame(int gamesRemaining) {
        playerScores.initialisePlayerMade21(players);  // Reset the made21 status for players
        gameNumber++;

        if (gamesRemaining <= 0) {
            // End of the game
            System.out.println("\nGame Over!\nAll rounds are completed.\nPress enter to return to main menu.");
            scanner.nextLine();
            recordScores();
            Main.mainMenu();
        }

        deck = new Deck();
        initialiseHands();
        play();
        startGame(gamesRemaining - 1);  // Recursively start the next game
    }

    /**
     * Handles the main gameplay loop where players take turns.
     */
    private void play() {
        this.replay = new Replay();
        boolean gameEnded = false;
        boolean made21;
        int roundNumber = 1;

        while (!gameEnded) {
            // Log round information
            replay.logMove("=================================\n" +
                    "ROUND " + roundNumber + "\n=================================\n");

            // Loop through each player and let them take their turn
            for (Player player : players) {
                made21 = SuitScoreCalculator.calculateAndCheckSuitScores(player);  // Check if player made 21
                showInitialHand(player);

                if (made21) {
                    made21(player);  // Player made 21, end game
                    gameEnded = true;
                } else {
                    if (player.getName().contains("Computer")) {
                        // Handle computer player's turn based on their difficulty level
                        String computerMove = computerTurn((ComputerPlayer) player);

                        if (computerMove == null) {
                            deckEmpty();  // If the deck is empty, end the game
                        }

                        replay.logMove(computerMove + "\n");
                        logPlayerCards(player);  // Log the computer player's cards
                    } else {
                        playerMove(player);  // Handle human player's move
                        showUpdatedHand(player);  // Show updated hand after move
                    }
                    made21 = SuitScoreCalculator.calculateAndCheckSuitScores(player);  // Re-check if player made 21

                    if (made21) {
                        made21(player);  // Player made 21, end game
                        gameEnded = true;
                    }
                }
                waitForInput();  // Wait for user input to continue
            }
            roundNumber++;  // Increment round number
        }
        finishGame();  // End the game and display results
    }

    /**
     * Handles the turn of a computer player, depending on their difficulty level.
     *
     * @param player The computer player whose turn it is
     * @return A string describing the move made by the computer player
     */
    private static String computerTurn(ComputerPlayer player) {
        return switch (player.getComputerLevel()) {
            case 1 -> ComputerLevels.computerLevel1(player, deck, gameNumber);
            case 2 -> ComputerLevels.computerLevel2(player, deck, gameNumber);
            case 3 -> ComputerLevels.computerLevel3(player, deck, gameNumber);
            default -> throw new IllegalArgumentException("Invalid computer level");
        };
    }

    /**
     * Displays the initial hand of a player at the start of their turn.
     *
     * @param player The player whose hand is being displayed
     */
    private void showInitialHand(Player player) {
        Display.display(player, gameNumber);
        Display.printSuitScores(player);
        logPlayerCards(player);
    }

    /**
     * Displays the updated hand of a player after a move.
     *
     * @param player The player whose hand is being displayed
     */
    private void showUpdatedHand(Player player) {
        Display.display(player, gameNumber);
        System.out.println("\n" + player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".");
        SuitScoreCalculator.calculateAndCheckSuitScores(player);  // Recalculate suit scores after the move
        Display.printSuitScores(player);  // Display updated suit scores
    }

    /**
     * Handles the situation when a player makes 21 points.
     *
     * @param player The player who made 21 points
     */
    private void made21(Player player) {
        Display.madeSuit21(player);  // Display a message that the player made 21
        playerScores.update21Status(player, true);  // Mark the player as having made 21
        replay.logMove(player.getName() + " made 21!\n");  // Log the move
    }

    /**
     * Handles a player's move by allowing them to select and replace a card.
     *
     * @param player The player making the move
     */
    private void playerMove(Player player) {
        selectCard();
        changeCard(player);
        replay.logMove(player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".\n");
        logPlayerCards(player);
    }

    /**
     * Prompts the player to select a card to replace.
     */
    private void selectCard() {
        while (true) {
            System.out.print("\nSelect card to replace (A, B, C, D, or E): ");
            this.cardSelection = scanner.nextLine().trim().toUpperCase();

            if (cardSelection.isEmpty()) {
                System.out.println("\nSelection cannot be empty. Please try again.");
            } else if (!cardSelection.matches("[ABCDE]")) {
                System.out.println("\nInvalid selection. Please select A, B, C, D, or E.");
            } else {
                break;
            }
        }
    }

    /**
     * Changes a player's selected card with a new card from the deck.
     *
     * @param player The player whose card is being replaced
     */
    public void changeCard(Player player) {
        char position = cardSelection.charAt(0);  // Get the card position (A, B, C, D, or E)
        int index = position - 'A';  // Convert position to an index
        this.newCard = deck.deal();

        if (newCard == null) {
            deckEmpty();  // If the deck is empty, end the game
        }
        this.replacedCard = player.getHand()[index];  // Store the card that is being replaced
        player.getHand()[index] = newCard;  // Replace the card in the player's hand
    }

    /**
     * Handles the case when the deck is empty, and no more cards can be dealt.
     */
    public void deckEmpty() {
        System.out.println("\nThe deck is empty.");
        replay.logMove("\nGame over. The deck is empty.");
        waitForInput();
        finishGame();
        startGame(totalGames - gameNumber);  // Start the next game
    }

    /**
     * Logs the details of a player's cards and suit scores.
     *
     * @param player The player whose cards are being logged
     */
    private void logPlayerCards(Player player) {
        String handDescription = player.getHandDetails();
        replay.logMove(handDescription);
        replay.logSuitScores(player);
    }

    /**
     * Pauses the game and waits for the user to press enter to continue.
     */
    public static void waitForInput() {
        System.out.print("\nPress enter to continue...");
        scanner.nextLine();
    }

    /**
     * Deals initial cards to each player at the start of the game.
     */
    private void initialiseHands() {
        for (Player player : players) {
            Card[] cards = new Card[5];  // Array to hold 5 cards for each player
            for (int i = 0; i < 5; i++) {
                cards[i] = deck.deal();
            }
            player.setHand(cards);  // Set the player's hand
        }
    }

    /**
     * Ends the current game and displays the results.
     */
    private void finishGame() {
        System.out.println("\nGame " + gameNumber + " has ended.");
        playerScores.distributePoints(players);
        String sortedScores = PlayerScoreCalculator.sortPlayerScores(players);
        Display.displaySortedScores(sortedScores);
        replay.logMove(sortedScores);

        // Ask the user if they want to view the replay
        while (true) {
            System.out.print("\nDo you wish to view replay? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                replay.showReplay();
                waitForInput();
                break;
            } else if (confirm.equals("N")) {
                waitForInput();
                break;
            } else {
                System.out.println("\nPlease enter 'Y' for Yes or 'N' for No.");
            }
        }
    }

    /**
     * Records the scores of players at the end of the game.
     */
    private void recordScores() {
        for (Player player : players) {
            double totalPoints = player.getPlayerScore();
            Leaderboard.savePlayerData(player.getName(), totalGames, totalPoints);
        }
    }
}
