import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DeckTest {

    private Deck deck;

    // Set up a new deck before each test
    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    // Test to ensure all cards in the deck are unique
    @Test
    public void testUniqueCards() {
        Set<Card> uniqueCards = new HashSet<>();

        for (int i = 0; i < 52; i++) {
            uniqueCards.add(deck.deal());
        }
        assertEquals(52, uniqueCards.size(), "The deck should contain only unique cards.");
    }

    // Test to ensure the deck contains exactly 52 cards
    @Test
    public void testDeckHas52Cards() {
        int count = 0;

        while (deck.deal() != null) {
            count++;
        }
        assertEquals(52, count, "The deck should contain exactly 52 cards.");
    }
}
