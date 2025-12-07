public class ComputerPlayer extends Player {

    private final int computerLevel;

    /**
     * Constructs a ComputerPlayer with the given name and difficulty level.
     *
     * @param name          The name of the computer player.
     * @param computerLevel The difficulty level for this computer player.
     */
    public ComputerPlayer(String name, int computerLevel) {
        super(name);
        this.computerLevel = computerLevel;
    }

    /**
     * Retrieves the difficulty level of this computer player.
     *
     * @return The difficulty level of the computer player (an integer value).
     */
    public int getComputerLevel() {
        return computerLevel;
    }
}
