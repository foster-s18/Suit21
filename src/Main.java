import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Entry point for the program
    public static void main(String[] args) {
        mainMenu();
    }

    /**
     * Displays the main menu and handles user input.
     * Offers options to start a new game, view instructions, view the leaderboard, or exit the game.
     */
    public static void mainMenu() {

        while (true) {
            // Print main menu options
            System.out.println("\n\n\n=================================");
            System.out.println("       Welcome to Suit 21!");
            System.out.println("=================================");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Leaderboard");
            System.out.println("4. Exit");
            System.out.println("=================================");
            System.out.print("Please choose an option (1-4): ");

            String choice = scanner.nextLine().trim();  // Get user input

            // Handle user choice using a switch statement
            switch (choice) {
                case "1":
                    start();
                    break;
                case "2":
                    showInstructions();
                    System.out.println("\nPress Enter to return to the main menu...");
                    scanner.nextLine(); // Wait for user to press Enter
                    break;
                case "3":
                    Leaderboard.displayLeaderboard();
                    System.out.println("\nPress Enter to return to the main menu...");
                    scanner.nextLine(); // Wait for user to press Enter
                    break;
                case "4":
                    System.out.println("Exiting the game. Goodbye!");
                    System.exit(0); // Exit the game if option 4 is selected
                    break;
                default:
                    // If the user enters an invalid option, display an error message and return to the main menu
                    System.out.println("Invalid choice. Press Enter to return to the main menu...");
                    scanner.nextLine(); // Wait for user to press Enter
            }
        }
    }

    /**
     * Starts a new game.
     * This method creates a new setup, retrieves player names, and begins the game.
     */
    public static void start() {
        Setup newSetup = new Setup(); // Create a new Setup object to handle the game setup
        Player[] playerNames = newSetup.getPlayers(); // Get the list of players from Setup
        int games = newSetup.getGames(); // Get the number of games to be played from Setup
        new PlayGame(playerNames, games); // Start the game with the specified players and number of games
        System.out.println("\nReturning to the main menu..."); // Display a message when the game ends
    }

    /**
     * Displays the instructions for playing the game.
     * Explains the setup, objective, card values, gameplay, and how to win.
     */
    public static void showInstructions() {
        System.out.println("\nWelcome to Suit 21!");
        System.out.println();
        System.out.println("1. Players and Setup:");
        System.out.println("   - For 2 to 6 players with a standard shuffled deck.");
        System.out.println("   - Each player gets five cards to start.");
        System.out.println();
        System.out.println("2. Objective:");
        System.out.println("   - Achieve exactly 21 points in a single suit, using any number of cards.");
        System.out.println();
        System.out.println("3. Card Values:");
        System.out.println("   - Number cards: Score their face value (e.g., 2 scores 2).");
        System.out.println("   - Face cards: Jack, Queen, King score 10.");
        System.out.println("   - Aces: Count as 1 or 11, whichever is better.");
        System.out.println();
        System.out.println("4. Gameplay:");
        System.out.println("   - On your turn, swap one card with one from the deck.");
        System.out.println("   - Aim for a hand in one suit that totals 21 points.");
        System.out.println();
        System.out.println("5. Winning the Game:");
        System.out.println("   - The is 1 point awarded per game.");
        System.out.println("   - Score 21 in one suit to win.");
        System.out.println("   - If someone scores 21, others get one last turn.");
        System.out.println("   - If multiple players score 21, the point is shared.");
        System.out.println("   - If the deck runs out without a 21, no point is awarded. ");
        System.out.println();
        System.out.println("6. Good Luck:");
        System.out.println("   - Play smart and aim for the perfect 21!");
    }
}
