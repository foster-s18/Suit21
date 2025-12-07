import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    @Test
    public void testConstructor() {
        // Create a ComputerPlayer with a specific name and level
        ComputerPlayer computerPlayer = new ComputerPlayer("Test Computer", 2);

        // Test if the name is set correctly
        assertEquals("Test Computer", computerPlayer.getName(), "The name should be 'Test Computer'.");

        // Test if the computer level is set correctly
        assertEquals(2, computerPlayer.getComputerLevel(), "The computer level should be 2.");
    }

    @Test
    public void testInheritanceFromPlayer() {
        // Create a ComputerPlayer instance
        ComputerPlayer computerPlayer = new ComputerPlayer("Test Computer", 3);

        // Test if it is an instance of Player
        assertTrue(computerPlayer instanceof Player, "ComputerPlayer should be a subclass of Player.");
    }

}
