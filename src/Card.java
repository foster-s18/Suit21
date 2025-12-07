public class Card {

    private final int rank;
    private final int suit;
    private int value;

    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final char[] SUITS = {'♥', '♣', '♦', '♠'};
    private static final int[] CARDVALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

    // Constructor to initialize a card with rank and suit
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = CARDVALUES[rank];
    }

    // Returns the rank of the card as a string
    public String getRank() {
        return Card.RANKS[this.rank];
    }

    // Returns the suit of the card as a character
    public char getSuit() {
        return Card.SUITS[this.suit];
    }

    // Returns the value of the card
    public int getValue() {
        return this.value;
    }

    // Sets a new value for the card
    public void setValue(int value) {
        this.value = value;
    }

    // Returns a string representation of the card
    public String toString() {
        return getRank() + " of " + getSuit();
    }
}
