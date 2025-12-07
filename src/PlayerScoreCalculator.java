import java.util.*;

public class PlayerScoreCalculator {
    // A HashMap to track whether a player has made 21 points
    private final HashMap<String, Boolean> playerMade21;

    /**
     * Constructor initializes the HashMap that tracks players who made 21 points.
     *
     * @param players Array of Player objects
     */
    public PlayerScoreCalculator(Player[] players) {
        playerMade21 = new HashMap<>();
    }

    /**
     * This method initializes the playerMade21 HashMap by setting all players' "made 21" status to false.
     *
     * @param players Array of Player objects
     */
    public void initialisePlayerMade21(Player[] players) {
        for (Player player : players) {
            this.playerMade21.put(player.getName(), false);
        }
    }

    /**
     * Initializes the player scores to zero for all players in the game.
     *
     * @param players Array of Player objects
     */
    public void initialisePlayerScores(Player[] players) {
        for (Player player : players) {
            player.setPlayerScore(0.0);
        }
    }

    /**
     * Updates the status of a player to indicate whether they made 21 points.
     *
     * @param player The player to update
     * @param made21 Boolean indicating whether the player made 21 points
     */
    public void update21Status(Player player, Boolean made21) {
        if (made21) {
            playerMade21.put(player.getName(), true);
        }
    }

    /**
     * Distributes single point equally between players who made 21.
     *
     * @param players Array of Player objects
     */
    public void distributePoints(Player[] players) {
        double pointsPerPlayer = 0;  // Point total to be awarded to player
        double pointsPerGame = 1;    // Total points awarded for the game (1 point)

        int countPlayersWith21 = 0;  // Count how many players made 21 points
        for (Player player : players) {
            if (playerMade21.get(player.getName())) {
                countPlayersWith21++;  // Increment count if player made 21
            }
        }

        // If at least one player made 21, distribute the total points equally among those players
        if (countPlayersWith21 > 0) {
            pointsPerPlayer = pointsPerGame / countPlayersWith21;
        }

        // Update the scores of players who made 21 points
        for (Player player : players) {
            if (playerMade21.get(player.getName())) {
                player.setPlayerScore(player.getPlayerScore() + pointsPerPlayer);  // Add the points to the player's score
            }
        }
    }

    // Comparator to sort players by score in descending order, then by name if scores are equal
    public static Comparator<Player> scoreComparator = (Player p1, Player p2) -> {
        int compareScores = Double.compare(p2.getPlayerScore(), p1.getPlayerScore());  // Compare scores in descending order
        if (compareScores == 0) {
            return p1.getName().compareTo(p2.getName());  // If scores are equal, sort alphabetically by name
        }
        return compareScores;
    };

    /**
     * Sorts the players by their scores and returns a formatted string of their names and scores.
     *
     * @param players Array of Player objects
     * @return A formatted string representing the leaderboard
     */
    public static String sortPlayerScores(Player[] players) {
        List<Player> descendingScores = new ArrayList<>(Arrays.asList(players));  // Convert the array to a list
        descendingScores.sort(scoreComparator);  // Sort the players by score using the comparator

        StringBuilder scoresLog = new StringBuilder();

        scoresLog.append("--------------------------\n");  // Header for the scores table
        String header = String.format("%-20s %-10s", "PLAYER NAME", "SCORE");  // Table header
        scoresLog.append(header).append("\n").append("--------------------------");

        // Append each player's name and score to the leaderboard string
        for (Player player : descendingScores) {
            String playerScore = String.format("\n%-20s %-10.2f", player.getName(), player.getPlayerScore());
            scoresLog.append(playerScore);  // Add the player's formatted score line
        }

        return scoresLog.toString();  // Return the complete leaderboard string
    }
}
