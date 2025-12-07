import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {

    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private Card card6;

    // Set up the test environment with different cards
    @BeforeEach
    public void setUp() {
        card1 = new Card(12, 0); // Ace of Hearts
        card2 = new Card(11, 1); // King of Clubs
        card3 = new Card(10, 2); // Queen of Diamonds
        card4 = new Card(9, 3);  // Jack of Spades
        card5 = new Card(6, 0);  // 8 of Hearts
        card6 = new Card(0, 1);  // 2 of Clubs
    }

    // Test the rank of each card
    @Test
    public void testGetRank() {
        assertEquals("A", card1.getRank(), "The rank of the card should be A.");
        assertEquals("K", card2.getRank(), "The rank of the card should be K.");
        assertEquals("Q", card3.getRank(), "The rank of the card should be Q.");
        assertEquals("J", card4.getRank(), "The rank of the card should be J.");
        assertEquals("8", card5.getRank(), "The rank of the card should be 8.");
    }

    // Test the suit of each card
    @Test
    public void testGetSuit() {
        assertEquals('♥', card1.getSuit(), "The suit of the card should be Hearts.");
        assertEquals('♣', card2.getSuit(), "The suit of the card should be Clubs.");
        assertEquals('♦', card3.getSuit(), "The suit of the card should be Diamonds.");
        assertEquals('♠', card4.getSuit(), "The suit of the card should be Spades.");
        assertEquals('♥', card5.getSuit(), "The suit of the card should be Hearts.");
        assertEquals('♣', card6.getSuit(), "The suit of the card should be Clubs.");
    }

    // Test the value of each card
    @Test
    public void testGetValue() {
        assertEquals(11, card1.getValue(), "The value of Ace should be 11.");
        assertEquals(10, card2.getValue(), "The value of King should be 10.");
        assertEquals(10, card3.getValue(), "The value of Queen should be 10.");
        assertEquals(10, card4.getValue(), "The value of Jack should be 10.");
        assertEquals(8, card5.getValue(), "The value of 8 should be 8.");
        assertEquals(2, card6.getValue(), "The value of 2 should be 2.");
    }

    // Test setting a new value for a card
    @Test
    public void testSetValue() {
        card1.setValue(1);
        assertEquals(1, card1.getValue(), "The value should be updated to 1.");

        card2.setValue(7);
        assertEquals(7, card2.getValue(), "The value should be updated to 7.");

        card3.setValue(11);
        assertEquals(11, card3.getValue(), "The value should be updated to 11.");
    }

    // Test the string representation of each card
    @Test
    public void testToString() {
        assertEquals("A of ♥", card1.toString(), "The card should be represented as 'A of ♥'.");
        assertEquals("K of ♣", card2.toString(), "The card should be represented as 'K of ♣'.");
        assertEquals("Q of ♦", card3.toString(), "The card should be represented as 'Q of ♦'.");
        assertEquals("J of ♠", card4.toString(), "The card should be represented as 'J of ♠'.");
        assertEquals("8 of ♥", card5.toString(), "The card should be represented as '8 of ♥'.");
    }

    // Test the card constructor for different combinations of rank and suit
    @Test
    public void testCardConstructor() {
        Card newCard1 = new Card(1, 1); // rank = 1 (3), suit = 1 (Clubs)
        assertEquals("3", newCard1.getRank(), "The rank of the card should be 3.");
        assertEquals('♣', newCard1.getSuit(), "The suit of the card should be Clubs.");
        assertEquals(3, newCard1.getValue(), "The value of the card should be 3.");

        Card newCard2 = new Card(12, 2); // rank = 12 (Ace), suit = 2 (Diamonds)
        assertEquals("A", newCard2.getRank(), "The rank of the card should be A.");
        assertEquals('♦', newCard2.getSuit(), "The suit of the card should be Diamonds.");
        assertEquals(11, newCard2.getValue(), "The value of the card should be 11.");
    }
}
