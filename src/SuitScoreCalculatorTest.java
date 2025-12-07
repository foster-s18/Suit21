import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SuitScoreCalculatorTest {

    private Player player;
    private int[] currentSuitScores;

    // Set up the test environment with a player and their hand
    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");

        Card[] hand = {
                new Card(12, 0), // Ace of Hearts
                new Card(11, 0),  // King of Hearts
                new Card(10, 0), // 10 of Hearts
                new Card(0, 1),  // 2 of Clubs
                new Card(1, 1)   // 3 of Clubs
        };
        player.setHand(hand);

        currentSuitScores = new int[4];
        SuitScoreCalculator.updateSuitScores(player, currentSuitScores);
    }

    // Test the updateSuitScores method to check the suit scores calculation
    @Test
    void UpdateSuitScores() {
        SuitScoreCalculator.updateSuitScores(player, currentSuitScores);

        // Expected scores after adding the values based on suits:
        int[] expectedScores = {31, 5, 0, 0};

        assertArrayEquals(expectedScores, currentSuitScores);
    }

    // Test the adjustForAce method to check how the Ace value is handled in the scores
    @Test
    void AdjustForAce() {
        SuitScoreCalculator.adjustForAce(player, currentSuitScores);

        // Expected scores after adjusting Ace
        int[] expectedScores = {21, 5, 0, 0}; // Hearts: 21, Clubs: 5, Diamonds: 0, Spades: 0

        assertArrayEquals(expectedScores, currentSuitScores);
    }

    // Test the checkFor21 method to verify whether a suit has reached a score of 21
    @Test
    void checkFor21() {
        int[] with21 = {21, 5, 0, 0}; // A suit has a score of 21
        int[] without21 = {31, 5, 0, 0}; // No suit has a score of 21

        assertTrue(SuitScoreCalculator.checkFor21(with21), "Expected true - one suit has a score of 21");
        assertFalse(SuitScoreCalculator.checkFor21(without21), "Expected false no suit has a score of 21");
    }
}
