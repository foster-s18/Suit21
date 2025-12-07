import java.util.InputMismatchException;
import java.util.Scanner;

public class Setup {
    private int numPlayers;
    private Player[] players;
    private int games;
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructor initializes the game setup, repeatedly asking for details until the player confirms.
     */
    public Setup() {
        while (true) {
            enterNumberOfPlayers();
            enterPlayerNames();
            enterGames();
            boolean confirm = confirmDetails();

            if (confirm) {
                System.out.println("\nSUIT 21");
                break;
            } else {
                System.out.println("Please re-enter the player details.");
                numPlayers = 0;
                players = null;
                games = 0;
            }
        }
    }

    /**
     * Prompts the user to input the number of players. Validates the input to ensure it's between 2 and 6.
     */
    private void enterNumberOfPlayers() {
        while (true) {
            try {
                System.out.print("Enter the number of players (2-6): ");
                numPlayers = scanner.nextInt();

                // Check if number of players is within the valid range
                if (numPlayers < 2 || numPlayers > 6) {
                    throw new IllegalArgumentException("The number of players must be between 2 and 6.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }

    /**
     * Asks for player names, ensuring that they are valid, unique, and not empty.
     * Handles both human and computer players (the latter also allows for selecting a difficulty level).
     */
    private void enterPlayerNames() {
        players = new Player[numPlayers];
        scanner.nextLine();

        for (int i = 0; i < numPlayers; i++) {
            while (true) {
                System.out.print("Enter name for Player " + (i + 1) + " (max 10): ");
                String name = scanner.nextLine().trim();

                // Validate the name (not empty and length <= 10)
                if (name.isEmpty()) {
                    System.out.println("Player name cannot be empty. Please try again.");
                } else if (name.length() > 10) {
                    System.out.println("Player name cannot exceed 10 characters. Please try again.");
                } else if (isNameUnique(name)) {
                    System.out.println("This name is already taken. Please choose a different name.");
                } else {
                    // Format the name (capitalize first letter and lowercase the rest)
                    String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

                    // Check if the name is "Computer" and assign a level
                    if (formattedName.equals("Computer")) {
                        int computerLevel = chooseComputerLevel();
                        String computerName = formattedName + "(Lvl" + computerLevel + ")";

                        // Check if the complete computer name (including level) is unique
                        if (isNameUnique(computerName)) {
                            System.out.println("This name is already taken. Please choose a different name.");
                            continue;
                        }
                        players[i] = new ComputerPlayer(computerName, computerLevel);  // Create a computer player
                    } else {
                        players[i] = new Player(formattedName);  // Create a regular player
                    }

                    break;  // Exit the loop once a valid name is entered
                }
            }
        }
    }

    /**
     * Asks the user to choose the difficulty level for a computer player.
     */
    private int chooseComputerLevel() {
        int computerLevel;
        while (true) {
            try {
                System.out.print("Choose computer level (1-3): ");
                computerLevel = scanner.nextInt();

                if (computerLevel < 1 || computerLevel > 3) {
                    throw new IllegalArgumentException("Invalid input. Please enter a valid number.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return computerLevel;
    }

    /**
     * Checks if the player name is unique.
     */
    private boolean isNameUnique(String name) {
        for (Player player : players) {
            if (player != null && player.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Asks the user to input the number of games to be played (validates the input between 1 and 20).
     */
    private void enterGames() {
        while (true) {
            try {
                System.out.print("Enter the number of games (1-20): ");
                games = scanner.nextInt();

                if (games < 1 || games > 20) {
                    throw new IllegalArgumentException("The number of games must be between 1 and 20.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the player details and asks the user to confirm if they are correct.
     */
    private boolean confirmDetails() {
        scanner.nextLine();  // Consume newline character
        System.out.println("\n--------------------------");
        System.out.println("Number of players: " + numPlayers);
        System.out.println("--------------------------");
        System.out.println("Number of games: " + games);
        System.out.println("--------------------------");
        System.out.println("PLAYERS");
        System.out.println("--------------------------");

        for (Player player : players) {
            System.out.println(player.getName());
        }
        System.out.println("--------------------------");

        while (true) {
            System.out.print("\nAre these details correct? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if (confirm.equals("Y")) {
                return true;
            } else if (confirm.equals("N")) {
                return false;
            } else {
                System.out.println("\nPlease enter 'Y' for Yes or 'N' for No.");
            }
        }
    }

    /**
     * Returns the list of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the number of games to be played.
     */
    public int getGames() {
        return games;
    }
}
