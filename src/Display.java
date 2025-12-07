public class Display {

    /**
     * Returns the ANSI escape code for setting the background color to white.
     *
     * @return The ANSI code for a white background.
     */
    private static String backgroundWhite() {
        return "\u001B[48;5;255m";
    }

    /**
     * Returns the ANSI escape code to reset the background color to default.
     *
     * @return The ANSI code for resetting the background color.
     */
    private static String backgroundReset() {
        return "\u001B[49m";
    }

    /**
     * Returns the ANSI escape code to reset the text color to default.
     *
     * @return The ANSI code for resetting the text color.
     */
    private static String resetTextColour() {
        return "\u001B[0m";
    }

    /**
     * Returns the ANSI escape code for red-colored text.
     *
     * @return The ANSI code for red-colored text.
     */
    private static String redTextColour() {
        return "\u001B[31m";
    }

    /**
     * Returns the ANSI escape code for black-colored text.
     *
     * @return The ANSI code for black-colored text.
     */
    private static String blackTextColour() {
        return "\u001B[30m";
    }

    /**
     * Displays the player's hand (cards) in a visually formatted manner.
     * The cards' ranks and suits are displayed in three lines, and color formatting is used for suits.
     *
     * @param player The player whose hand is being displayed.
     * @param gameNumber The current game number.
     */
    public static void display(Player player, int gameNumber) {
        // String builders for the three lines representing card ranks and suits.
        StringBuilder stringBuilderLine1 = new StringBuilder();
        StringBuilder stringBuilderLine2 = new StringBuilder();
        StringBuilder stringBuilderLine3 = new StringBuilder();

        // Loop through each card in the player's hand.
        for (Card card : player.getHand()) {
            String textColour;

            // Choose text color based on the suit.
            if (card.getSuit() == '♥' || card.getSuit() == '♦') {
                textColour = redTextColour();
            } else {
                textColour = blackTextColour();
            }

            // Append the rank of the card in line 1, with formatting and color applied.
            stringBuilderLine1.append(backgroundWhite())
                    .append(textColour)
                    .append(String.format("%-5s", card.getRank()))  // Adjust rank spacing for neatness.
                    .append(backgroundReset())
                    .append(" ");

            // Append the suit of the card in line 2, with formatting and color applied.
            stringBuilderLine2.append(backgroundWhite())
                    .append(textColour)
                    .append("  ").append(card.getSuit()).append("  ")
                    .append(backgroundReset())
                    .append(" ");

            // Append the rank of the card again in line 3, for consistency in display.
            stringBuilderLine3.append(backgroundWhite())
                    .append(textColour)
                    .append(String.format("%5s", card.getRank()))  // Adjust rank spacing for neatness.
                    .append(backgroundReset())
                    .append(" ");
        }

        // Display game number, player name, and cards.
        System.out.println("\n-----------------------------");
        System.out.printf(resetTextColour() + "Game %d\n%s's Turn\n", gameNumber, player.getName());
        System.out.println("-----------------------------");
        System.out.println(stringBuilderLine1);
        System.out.println(stringBuilderLine2);
        System.out.println(stringBuilderLine3);
        System.out.println(resetTextColour() + "  A     B     C     D     E");
    }

    /**
     * Displays the suit scores for the player (Hearts, Clubs, Diamonds, Spades).
     *
     * @param player The player whose suit scores are being displayed.
     */
    public static void printSuitScores(Player player) {
        // Retrieve the suit scores for the player.
        int[] suitScores = player.getSuitScores();
        System.out.println("\nSUIT SCORES:");
        System.out.println("------------");
        System.out.println("Hearts: " + suitScores[0]);
        System.out.println("Clubs: " + suitScores[1]);
        System.out.println("Diamonds: " + suitScores[2]);
        System.out.println("Spades: " + suitScores[3]);
    }

    /**
     * Displays a congratulatory message for the player when they make a "21" with their suit.
     *
     * @param player The player who made "21" with their suit.
     */
    public static void madeSuit21(Player player) {
        System.out.printf("\n\nCongratulations %s!\n", player.getName());
        System.out.println("\n♥ ♣ ♦ ♠ ♥ ♣ ♦ ♠ ♥ ♣ ");
        System.out.println("♣  YOU MADE 21!!  ♦ ");
        System.out.println("♦ ♠ ♥ ♣ ♦ ♠ ♥ ♣ ♦ ♠");
    }

    /**
     * Displays the sorted scores in the game.
     *
     * @param sortedScores A string containing the sorted scores to be displayed.
     */
    public static void displaySortedScores(String sortedScores) {
        System.out.println(sortedScores);
    }
}
