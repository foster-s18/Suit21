import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreCalculatorTest {

    private Player testPlayer1, testPlayer2, testPlayer3;
    private Player[] players;
    private PlayerScoreCalculator scoreCalculator;

    // Set up the test environment by initializing test players, the score calculator, and scores
    @BeforeEach
    void setUp() {
        testPlayer1 = new Player("Colin");
        testPlayer2 = new Player("Bob");
        testPlayer3 = new Player("Adam");

        players = new Player[]{testPlayer1, testPlayer2, testPlayer3};
        scoreCalculator = new PlayerScoreCalculator(players);

        scoreCalculator.initialisePlayerScores(players);
        scoreCalculator.initialisePlayerMade21(players);
    }

    // Test that all players' scores are initialized to 0.0
    @Test
    void TestInitialisePlayerScores() {

        assertEquals(0.0, testPlayer1.getPlayerScore());
        assertEquals(0.0, testPlayer2.getPlayerScore());
        assertEquals(0.0, testPlayer3.getPlayerScore());
    }

    // Test distributing points to players who made 21
    @Test
    void TestDistributePoints() {

        scoreCalculator.update21Status(testPlayer1, true);
        scoreCalculator.update21Status(testPlayer2, false);
        scoreCalculator.update21Status(testPlayer3, true);

        // Initially, all players have 0 points
        scoreCalculator.distributePoints(players);

        // Both player1 and player2 should have received equal points
        assertEquals(0.5, testPlayer1.getPlayerScore());
        assertEquals(0.0, testPlayer2.getPlayerScore());
        assertEquals(0.5, testPlayer3.getPlayerScore()); // player3 gets no points
    }

    // Test the score comparator to ensure players are compared correctly
    @Test
    void TestScoreComparator() {
        testPlayer1.setPlayerScore(10.0); // Same score for name comparison
        testPlayer2.setPlayerScore(15.0);
        testPlayer3.setPlayerScore(10.0); // Same score for name comparison

        assertTrue(PlayerScoreCalculator.scoreComparator.compare(testPlayer2, testPlayer1) < 0);

        // Colin and Adam have the same score, so compare/order by name
        assertTrue(PlayerScoreCalculator.scoreComparator.compare(testPlayer3, testPlayer1) < 0);

        // Adam  and Bob are compared, Bob has a higher score
        assertTrue(PlayerScoreCalculator.scoreComparator.compare(testPlayer3, testPlayer2) > 0);
    }

    // Test sorting players based on their scores to ensure correct order
    @Test
    void TestSortPlayerScores() {
        // Test sorting players based on their scores
        testPlayer1.setPlayerScore(10.0);
        testPlayer2.setPlayerScore(20.0);
        testPlayer3.setPlayerScore(15.0);

        String sortedScores = PlayerScoreCalculator.sortPlayerScores(players);

        // Verify that the players are sorted in descending order by score
        assertTrue(sortedScores.indexOf("Bob") < sortedScores.indexOf("Colin"));
        assertTrue(sortedScores.indexOf("Adam") < sortedScores.indexOf("Colin"));
    }
}
