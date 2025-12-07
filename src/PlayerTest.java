import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Card[] hand;

    // Set up the player and hand for testing
    @BeforeEach
    public void setUp() {
        player = new Player("Test Player");

        hand = new Card[] {
                new Card(12, 0), // Ace of Hearts
                new Card(11, 1), // King of Clubs
                new Card(10, 2), // Queen of Diamonds
                new Card(9, 3),  // Jack of Spades
                new Card(6, 0)   // 8 of Hearts
        };

        player.setHand(hand);

        player.setSuitScores(new int[] {20, 15, 10, 5});
    }

    // Test the constructor of the Player class
    @Test
    public void testConstructor() {
        Player player = new Player("Simon");
        assertEquals("Simon", player.getName(), "Player name should be 'Simon'.");
        assertNotNull(player.getHand(), "Player's hand should be initialized.");
        assertEquals(5, player.getHand().length, "Player's hand should contain 5 cards.");
        assertNotNull(player.getSuitScores(), "Suit scores should be initialized.");
        assertEquals(4, player.getSuitScores().length, "There should be 4 suit scores.");
        assertEquals(0.0, player.getPlayerScore(), "Player's score should initially be 0.0.");
    }

    // Test getting the name of the player
    @Test
    public void testGetName() {
        assertEquals("Test Player", player.getName(), "Player's name should be 'Test Player'.");
    }

    // Test setting the player's hand
    @Test
    public void testSetHand() {
        assertEquals(hand, player.getHand(), "Player's hand should match the set hand.");
    }

    // Test getting the player's hand
    @Test
    public void testGetHand() {
        assertArrayEquals(hand, player.getHand(), "Player's hand should return the correct cards.");
    }

    // Test getting the hand details as a string
    @Test
    public void testGetHandDetails() {
        String handDetails = player.getHandDetails();
        assertNotNull(handDetails, "Hand details should not be null.");
        assertTrue(handDetails.startsWith("Test Player's Hand:\n"), "Hand details should start with player's name.");
        assertTrue(handDetails.contains("A of ♥"), "Hand details should include 'A of ♥'.");
        assertTrue(handDetails.contains("K of ♣"), "Hand details should include 'K of ♣'.");
        assertTrue(handDetails.contains("Q of ♦"), "Hand details should include 'Q of ♦'.");
        assertTrue(handDetails.contains("J of ♠"), "Hand details should include 'J of ♠'.");
        assertTrue(handDetails.contains("8 of ♥"), "Hand details should include '8 of ♥'.");
        assertFalse(handDetails.contains("5 of ♥"), "Hand details should not include '5 of ♥'.");
    }

    // Test setting the suit scores for the player
    @Test
    public void testSetSuitScores() {
        int[] newSuitScores = {25, 18, 12, 8};
        player.setSuitScores(newSuitScores);
        assertArrayEquals(newSuitScores, player.getSuitScores(), "Suit scores should match the updated values.");
    }

    // Test getting the suit scores of the player
    @Test
    public void testGetSuitScores() {
        int[] suitScores = player.getSuitScores();
        assertArrayEquals(new int[] {20, 15, 10, 5}, suitScores, "Suit scores should match the set values.");
    }

    // Test getting the player's score
    @Test
    public void testGetPlayerScore() {
        assertEquals(0.0, player.getPlayerScore(), "Player's initial score should be 0.0.");
    }

    // Test setting the player's score
    @Test
    public void testSetPlayerScore() {
        player.setPlayerScore(50.5);
        assertEquals(50.5, player.getPlayerScore(), "Player's score should match the updated value.");
    }
}
