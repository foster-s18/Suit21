import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Replay {
    // Queue to store the moves made during the game for replay purposes
    public Queue<String> moves;

    /**
     * Constructor to initialize the Replay object with an empty queue of moves.
     */
    public Replay() {
        moves = new LinkedList<>();
    }

    /**
     * Logs a move made during the game into the replay.
     *
     * @param move A string describing the move to be logged
     */
    public void logMove(String move) {
        moves.add(move);
    }

    /**
     * Logs the suit scores of a player.
     * The suit scores represent the points in each suit (Hearts, Clubs, Diamonds, Spades).
     *
     * @param player The player whose suit scores are being logged
     */
    public void logSuitScores(Player player) {
        int[] suitScores = player.getSuitScores();  // Retrieve the player's suit scores
        Arrays.fill(suitScores, 0);  // Ensure the suit scores are reset (set to 0)

        // Update suit scores using the SuitScoreCalculator
        SuitScoreCalculator.updateSuitScores(player, suitScores);


        String suitsLog = player.getName() + " Suit Scores:" +
                "\nHearts: " + suitScores[0] +
                "\nClubs: " + suitScores[1] +
                "\nDiamonds: " + suitScores[2] +
                "\nSpades: " + suitScores[3] + "\n";

        moves.add(suitsLog);
    }

    /**
     * Displays the replay of all moves made during the game.
     * It prints each move from the queue of logged moves.
     */
    public void showReplay() {
        System.out.println("\n\nReplay of all moves:");

        // While there are moves left in the queue, dequeue and print each move
        while (!moves.isEmpty()) {
            System.out.println(moves.poll());
        }

        System.out.println("\nReplay Finished.\n");
    }
}
