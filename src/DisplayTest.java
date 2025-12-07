import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DisplayTest {
    private Player player;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalSystemOut;  // Save the original System.out

    // Set up the test environment by creating a test player and setting up hand and suit scores
    @BeforeEach
    public void setUp() {
        // Save the original System.out
        originalSystemOut = System.out;

        // Redirect System.out to the ByteArrayOutputStream for capturing output
        System.setOut(new PrintStream(outContent));

        // Create a test player and hand
        player = new Player("TestPlayer");
        player.setHand(new Card[]{
                new Card(0, 0), // 2 of hearts
                new Card(1, 1), // 3 of clubs
                new Card(2, 2), // 4 of diamonds
                new Card(8, 3), // 10 of spades
                new Card(12, 0) // Ace of hearts
        });

        // Set test player suit scores
        player.setSuitScores(new int[]{5, 10, 15, 20}); // Hearts: 5, Clubs: 10, Diamonds: 15, Spades: 20
    }

    @Test
    public void testDisplay() {
        // Redirect System.out to outContent (for capturing output)
        System.setOut(new PrintStream(outContent));


        Display.display(player, 1);


        String output = outContent.toString();
        assertTrue(output.contains("Game 1"));
        assertTrue(output.contains("TestPlayer's Turn"));
        assertTrue(output.contains("  A     B     C     D     E"));

        // Restore original System.out after the test is complete
        System.setOut(originalSystemOut);

        System.out.print(output);
    }

    // Test the printSuitScores method to ensure the correct output of suit scores
    @Test
    public void testPrintSuitScores() {
        // Redirect System.out to outContent (for capturing output)
        System.setOut(new PrintStream(outContent));

        Display.printSuitScores(player);


        String output = outContent.toString();
        assertTrue(output.contains("Hearts: 5"));
        assertTrue(output.contains("Clubs: 10"));
        assertTrue(output.contains("Diamonds: 15"));
        assertTrue(output.contains("Spades: 20"));

        // Restore original System.out after the test is complete
        System.setOut(originalSystemOut);

        System.out.print(output);
    }

    // Test the madeSuit21 method to ensure the correct output when a player makes 21
    @Test
    public void MadeSuit21() {
        // Redirect System.out to outContent (for capturing output)
        System.setOut(new PrintStream(outContent));

        Display.madeSuit21(player);

        String output = outContent.toString();
        assertTrue(output.contains("Congratulations TestPlayer!"));
        assertTrue(output.contains("YOU MADE 21!!"));

        // Restore original System.out after the test is complete
        System.setOut(originalSystemOut);

        System.out.print(output);
    }
}
