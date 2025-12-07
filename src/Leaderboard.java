import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Leaderboard {
    // File name where leaderboard data will be stored
    private static final String FILE_NAME = "leaderboard.txt";

    /**
     * Saves player data to the leaderboard file.
     * If the player already exists, their data will be updated.
     * If the player is new, their data will be added.
     *
     * @param playerName The name of the player.
     * @param gamesPlayed The number of games the player has played.
     * @param totalPoints The total points the player has scored.
     */
    public static void savePlayerData(String playerName, int gamesPlayed, double totalPoints) {
        // Skip saving for players with the name "Computer"
        if (playerName.toLowerCase().contains("computer")) {
            return; // Do nothing for players named "Computer"
        }

        try {
            // Ensure the leaderboard file exists, create it if not
            File leaderboardFile = new File(FILE_NAME);
            if (!leaderboardFile.exists()) {
                if (!leaderboardFile.createNewFile()) {
                    System.out.println("Error creating leaderboard file.");
                    return;
                }
            }

            // If the player doesn't exist, add new player data
            if (!isPlayerExist(playerName)) {
                String data = playerName + ", " + gamesPlayed + ", " + totalPoints + "\n";
                try (FileWriter fileWriter = new FileWriter(FILE_NAME, true)) {
                    fileWriter.write(data);  // Append the new player data to the file
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            } else {
                // If player exists, update their data with new game stats
                updatePlayerData(playerName, gamesPlayed, totalPoints);
            }
        } catch (IOException e) {
            System.out.println("Error creating or reading the leaderboard file: " + e.getMessage());
        }
    }

    /**
     * Checks if the player already exists in the leaderboard file.
     *
     * @param playerName The name of the player.
     * @return True if the player exists, false otherwise.
     */
    private static boolean isPlayerExist(String playerName) {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            // Loop through each line in the file to check for an existing player
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] playerInfo = line.split(", ");
                if (playerInfo[0].equals(playerName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return false; // Player doesn't exist
    }

    /**
     * Updates the player's data in the leaderboard file (if player already exists).
     * Adds new data if the player is not found.
     *
     * @param playerName The name of the player.
     * @param newGamesPlayed The new number of games played.
     * @param newTotalPoints The new total points scored.
     */
    public static void updatePlayerData(String playerName, int newGamesPlayed, double newTotalPoints) {
        List<String> updatedData = new ArrayList<>();
        boolean playerFound = false;

        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            // Loop through each line in the file to find the player
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] playerInfo = line.split(", ");
                String name = playerInfo[0];
                int gamesPlayed = Integer.parseInt(playerInfo[1]);
                double totalPoints = Double.parseDouble(playerInfo[2]);

                // If the player is found, update their data
                if (name.equals(playerName)) {
                    gamesPlayed += newGamesPlayed;  // Add the new games played
                    totalPoints += newTotalPoints;  // Add the new total points
                    playerFound = true;
                }

                // Add the updated player data to the list
                updatedData.add(name + ", " + gamesPlayed + ", " + totalPoints);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        // If player was not found, add new player data
        if (!playerFound) {
            updatedData.add(playerName + ", " + newGamesPlayed + ", " + newTotalPoints);
        }

        // Write the updated data back to the file
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            for (String data : updatedData) {
                fileWriter.write(data + "\n"); // Write each player's updated data to the file
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Displays the leaderboard sorted by total points, showing the top 20 players.
     */
    public static void displayLeaderboard() {
        List<PlayerData> leaderboard = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            // Read the player data from the leaderboard file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] playerInfo = line.split(", ");
                String name = playerInfo[0];
                int gamesPlayed = Integer.parseInt(playerInfo[1]);
                double totalPoints = Double.parseDouble(playerInfo[2]);

                // Calculate the average points per game (APG)
                double averagePerGame = totalPoints / gamesPlayed;

                // Add the player's data to the leaderboard
                leaderboard.add(new PlayerData(name, gamesPlayed, totalPoints, averagePerGame));
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }

        // Sort the leaderboard by total points in descending order
        leaderboard.sort((p1, p2) -> Double.compare(p2.getTotalPoints(), p1.getTotalPoints()));

        // Display the top 20 players
        System.out.println("\nLeaderboard (Top 20):");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-5s %-12s %-12s %-12s %-12s\n", "Rank", "Name", "Games", "Points", "APG");
        System.out.println("--------------------------------------------------");

        int count = 1;
        for (PlayerData player : leaderboard) {
            if (count > 20) break; // Only display the top 20 players
            System.out.printf("%-5d %-12s %-12d %-12.2f %-12.2f\n",
                    count,
                    player.getName(),
                    player.getGamesPlayed(),
                    player.getTotalPoints(),
                    player.getAveragePerGame());
            count++;
        }
    }

    /**
     * Inner class to represent player data in the leaderboard.
     */
    static class PlayerData {
        private String name;
        private int gamesPlayed;
        private double totalPoints;
        private double averagePerGame;

        public PlayerData(String name, int gamesPlayed, double totalPoints, double averagePerGame) {
            this.name = name;
            this.gamesPlayed = gamesPlayed;
            this.totalPoints = totalPoints;
            this.averagePerGame = averagePerGame;
        }

        public String getName() {
            return name;
        }

        public int getGamesPlayed() {
            return gamesPlayed;
        }

        public double getTotalPoints() {
            return totalPoints;
        }

        public double getAveragePerGame() {
            return averagePerGame;
        }
    }
}
