import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ComputerLevels {
    private static final Random RANDOM = new Random();
    private static final char[] CARD_LABELS = {'A', 'B', 'C', 'D', 'E'};
    private static final Scanner scanner = new Scanner(System.in);
    private static Card replacedCard;
    private static Card newCard;

    /**
     * Executes computer level 1 logic: selects a random card to replace, avoiding Aces.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move made by the computer.
     */
    public static String computerLevel1(Player player, Deck deck, int gameNumber) {
        return selectRandomCardKeepAce(player, deck, gameNumber);
    }

    /**
     * Executes computer level 2 logic: attempts to remove a card to make 21,
     * keeps high cards, or selects a random card to replace.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move made by the computer.
     */
    public static String computerLevel2(Player player, Deck deck, int gameNumber) {
        String result = removeCardToMake21(player, deck, gameNumber);
        if (result != null) {
            return result;
        }

        result = keepHighCards(player, deck, gameNumber);
        if (result != null) {
            return result;
        }

        result = selectRandomCardKeepAce(player, deck, gameNumber);
        return result;
    }

    /**
     * Executes computer level 3 logic: optimizes card replacement to achieve 21
     * or follows similar logic to level 2 for other decisions.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move made by the computer.
     */
    public static String computerLevel3(Player player, Deck deck, int gameNumber) {
        String result = removeCardToMake21(player, deck, gameNumber);
        if (result != null) {
            return result;
        }

        result = changeOptimalCard(player, deck, gameNumber);
        if (result != null) {
            return result;
        }

        result = keepHighCards(player, deck, gameNumber);
        if (result != null) {
            return result;
        }

        result = selectRandomCardKeepAce(player, deck, gameNumber);
        return result;
    }

    /**
     * Attempts to remove a card to achieve a score of 21.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move, or null if no suitable card was found.
     */
    private static String removeCardToMake21(Player player, Deck deck, int gameNumber) {
        int index = 0;
        int tempValue;
        boolean made21;

        // Calculate the current suit scores before attempting replacements.
        SuitScoreCalculator.calculateAndCheckSuitScores(player);

        for (Card card : player.getHand()) {
            tempValue = card.getValue();

            // Temporarily set the card's value to 0 to simulate removing it and calculate scores from other cards
            card.setValue(0);
            made21 = SuitScoreCalculator.calculateAndCheckSuitScores(player);

            if (made21) {
                newCard = deck.deal();
                if (newCard == null) {
                    return null;
                }
                replacedCard = player.getHand()[index];
                player.getHand()[index] = newCard;
                displayComputerMove(player, gameNumber, index);

                return player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".";
            }

            // Restore the card's original value if it doesn't lead to 21.
            card.setValue(tempValue);
            index++;
        }
        return null;
    }

    /**
     * Replaces low-value cards with new cards from the deck.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move, or null if no card was replaced.
     */
    private static String keepHighCards(Player player, Deck deck, int gameNumber) {
        int index = 0;

        SuitScoreCalculator.calculateAndCheckSuitScores(player);

        for (Card card : player.getHand()) {
            // Check for cards with values less than 10 and replace first card in hand under the value of 10.
            if (card.getValue() < 10) {
                newCard = deck.deal();
                if (newCard == null) {
                    return "Deck Empty";
                }

                replacedCard = player.getHand()[index];
                player.getHand()[index] = newCard;
                displayComputerMove(player, gameNumber, index);

                return player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".";
            }
            index++;
        }
        return null;
    }

    /**
     * Randomly selects a card to replace, avoiding Aces.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move, or null if no card was replaced.
     */
    private static String selectRandomCardKeepAce(Player player, Deck deck, int gameNumber) {
        int index;

        // Randomly select a card index, ensuring the selected card is not an Ace.
        do {
            index = RANDOM.nextInt(CARD_LABELS.length);
        } while (player.getHand()[index].getValue() == 11);

        newCard = deck.deal();
        if (newCard == null) {
            return null;
        }
        replacedCard = player.getHand()[index];
        player.getHand()[index] = newCard;
        displayComputerMove(player, gameNumber, index);

        return player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".";
    }

    /**
     * Attempts to replace a card with the best possible option from the deck.
     *
     * @param player     The player making the move.
     * @param deck       The deck of cards.
     * @param gameNumber The current game number.
     * @return A message describing the move, or null if no optimal replacement was found.
     */
    private static String changeOptimalCard(Player player, Deck deck, int gameNumber) {
        // Peek at the next card in the deck to check if the deck has cards available
        Card nextCard = deck.peek();
        Card currentCard;
        Card checkNextCard;

        // If no cards are available in the deck, return null to signify no move possible
        if (nextCard == null) {
            return null;
        }

        // Initialize a map to track the number of replacements for each card that can lead to a score of 21
        Map<Integer, Integer> cardPossibilities = new HashMap<>();
        for (int key = 0; key <= 4; key++) {
            cardPossibilities.put(key, 0);
        }

        int index = 0;
        boolean made21;

        // Calculate current suit scores before any replacements
        SuitScoreCalculator.calculateAndCheckSuitScores(player);

        // Iterate over each card in the player's hand to evaluate replacement options
        for (Card card : player.getHand()) {
            currentCard = card;

            // Simulate replacing the current card with every card in the deck
            for (int i = 0; i < deck.deck.size(); i++) {
                checkNextCard = deck.deck.get(i);
                player.getHand()[index] = checkNextCard;

                // Check if replacing this card achieves a score of 21
                made21 = SuitScoreCalculator.calculateAndCheckSuitScores(player);

                if (made21) {
                    // Increment the count of successful replacements for this card index
                    cardPossibilities.put(index, cardPossibilities.get(index) + 1);
                }
            }

            // Restore the original card in the hand after simulation
            player.getHand()[index] = currentCard;
            index++;
        }

        // Find the card index with the highest chance of achieving 21
        int highestChanceCard = 0;
        int maxValue = 0;

        for (Map.Entry<Integer, Integer> entry : cardPossibilities.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                highestChanceCard = entry.getKey();
            }
        }

        // Uncomment for debugging to manually confirm card replacement logic.
//         debug(player, gameNumber, cardPossibilities, deck, highestChanceCard);

        // Replace the card if there is a significant chance of improving the score
        if (maxValue > 0) {
            newCard = deck.deal();
            replacedCard = player.getHand()[highestChanceCard];
            player.getHand()[highestChanceCard] = newCard;

            // Display the move made by the computer
            displayComputerMove(player, gameNumber, highestChanceCard);
            return player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".";
        }
        return null;
    }

    /**
     * Displays the computer's move and updates the game state.
     *
     * @param player     The player making the move.
     * @param gameNumber The current game number.
     * @param index      The index of the card being replaced.
     */
    private static void displayComputerMove(Player player, int gameNumber, int index) {
        // Announce the card replacement decision
        System.out.print("\n" + player.getName() + " chose to replace card " + CARD_LABELS[index] + "\n");
        System.out.print("\nPress enter to continue...");
        scanner.nextLine();

        // Recalculate suit scores and display the updated game state
        SuitScoreCalculator.calculateAndCheckSuitScores(player);
        Display.display(player, gameNumber);

        // Print details of the replacement
        System.out.println("\n" + player.getName() + " replaced the " + replacedCard + " with the " + newCard + ".");
        Display.printSuitScores(player);
    }

    /**
     * Debug method to manually confirm card replacement logic.
     *
     * @param player            The player making the move.
     * @param gameNumber        The current game number.
     * @param cardPossibilities A map of card indices to the number of possible replacements that make 21.
     * @param deck              The deck of cards.
     * @param highestChanceCard The index of the card with the highest replacement chance.
     */
    private static void debug(Player player, int gameNumber,
                              Map<Integer, Integer> cardPossibilities,
                              Deck deck, int highestChanceCard) {

        char[] cardLetter = {'A', 'B', 'C', 'D', 'E'};

        Display.display(player, gameNumber);

        // Print the chances of each card being replaced to achieve 21
        for (Map.Entry<Integer, Integer> entry : cardPossibilities.entrySet()) {
            System.out.println("\nCard: " + cardLetter[entry.getKey()]);
            System.out.println("Possible cards in deck to make 21 if replaced: " + entry.getValue());
        }

        // Highlight the card with the highest chance of improving the score
        System.out.println("\nCard to replace with highest chance: " + cardLetter[highestChanceCard]);

        // Print remaining cards in the deck for additional context
        System.out.println("\nCards remaining in deck:");
        System.out.print(deck.toString());
    }
}
