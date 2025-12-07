public class Player {

    private final String name;
    private Card[] hand;
    private int[] suitScores;
    private double playerScore;

    /**
     * Constructor to initialize a player with a given name.
     * Initializes hand to have 5 cards, suitScores for 4 suits, and playerScore to 0.
     *
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Card[5];
        this.suitScores = new int[4];
        this.playerScore = 0.0;
    }

    /**
     * Getter method to retrieve the player's name.
     *
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method to set the player's hand.
     *
     * @param hand The array of cards representing the player's hand
     */
    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    /**
     * Getter method to retrieve the player's hand (array of cards).
     *
     * @return The player's hand (array of 5 cards)
     */
    public Card[] getHand() {
        return hand;
    }

    /**
     * Getter method to retrieve the player's suit scores.
     *
     * @return The suit scores (array of 4 integers representing Hearts, Clubs, Diamonds, and Spades)
     */
    public int[] getSuitScores() {
        return suitScores;
    }

    /**
     * Setter method to set the player's suit scores.
     *
     * @param suitScores The array of suit scores
     */
    public void setSuitScores(int[] suitScores) {
        this.suitScores = suitScores;
    }

    /**
     * Getter method to retrieve the player's overall score.
     *
     * @return The player's score as a double
     */
    public double getPlayerScore() {
        return playerScore;
    }

    /**
     * Setter method to set the player's overall score.
     *
     * @param playerScore The new score of the player
     */
    public void setPlayerScore(double playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * Method to retrieve a detailed description of the player's hand.
     * This method returns a string describing each card in the player's hand.
     *
     * @return A string representation of the player's hand
     */
    public String getHandDetails() {
        StringBuilder handDescription = new StringBuilder(name + "'s Hand:\n");
        for (Card card : hand) {
            handDescription.append(card.toString()).append("\n");
        }
        return handDescription.toString();
    }
}
