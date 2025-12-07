import java.util.Stack;
import java.util.Collections;

public class Deck {

    // The stack that holds the cards in the deck
    public Stack<Card> deck = new Stack<>();

    /**
     * Constructor for the Deck class.
     * Initializes the deck by adding 52 cards (13 ranks and 4 suits)
     * and shuffles the deck for random card order.
     */
    public Deck() {

        // Loop through numbers from 0 to 51 to create 52 cards.
        // i % 13 gives the rank (0 to 12), i / 13 gives the suit (0 to 3)
        for (int i = 0; i < 52; i++) {
            this.deck.add(new Card(i % 13, i / 13));
        }

        Collections.shuffle(this.deck);
    }

    /**
     * Provides a string representation of the deck.
     * Each card in the deck is appended to a string with each card on a new line.
     *
     * @return A string representation of all the cards in the deck.
     */
    public String toString() {
        StringBuilder resultStr = new StringBuilder("\n");

        for (Card card : deck) {
            resultStr.append(card).append("\n");
        }

        return resultStr.toString();
    }

    /**
     * Deals a card from the top of the deck.
     * Removes the top card from the deck and returns it.
     *
     * @return The card dealt from the deck, or null if the deck is empty.
     */
    public Card deal() {
        if (!this.deck.isEmpty()) {
            return this.deck.pop(); // Remove and return the top card from the deck
        }
        return null; // Return null if the deck is empty
    }

    /**
     * Peeks at the top card of the deck without removing it.
     * Returns the card at the top of the deck, or null if the deck is empty.
     *
     * @return The top card of the deck, or null if the deck is empty.
     */
    public Card peek() {
        if (!this.deck.isEmpty()) {
            return this.deck.peek(); // Return the top card without removing it
        }
        return null; // Return null if the deck is empty
    }
}
