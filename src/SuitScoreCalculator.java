import java.util.Arrays;

public class SuitScoreCalculator {

    public static boolean calculateAndCheckSuitScores(Player player) {
        int[] currentSuitScores = player.getSuitScores();
        updateSuitScores(player, currentSuitScores);
        adjustForAce(player, currentSuitScores);
        return checkFor21(currentSuitScores);
    }

    public static void updateSuitScores(Player player, int[] currentSuitScores) {
        Arrays.fill(currentSuitScores, 0);
        for (Card card : player.getHand()) {
            int value = card.getValue();
            switch (card.getSuit()) {
                case '♥':
                    currentSuitScores[0] += value;
                    break;
                case '♣':
                    currentSuitScores[1] += value;
                    break;
                case '♦':
                    currentSuitScores[2] += value;
                    break;
                case '♠':
                    currentSuitScores[3] += value;
                    break;
                default:
                    break;
            }
        }
    }

    public static void adjustForAce(Player player, int[] currentSuitScores) {
        for (Card card : player.getHand()) {
            if (card.getValue() == 11)  {
                switch (card.getSuit()) {
                    case '♥':
                        if (currentSuitScores[0] > 21) {
                            currentSuitScores[0] -= 10;
                        }
                        break;
                    case '♣':
                        if (currentSuitScores[1] > 21) {
                            currentSuitScores[1] -= 10;
                        }
                        break;
                    case '♦':
                        if (currentSuitScores[2] > 21) {
                            currentSuitScores[2] -= 10;
                        }
                        break;
                    case '♠':
                        if (currentSuitScores[3] > 21) {
                            currentSuitScores[3] -= 10;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static boolean checkFor21(int[] currentSuitScores) {
        for (int score : currentSuitScores) {
            if (score == 21) {
                return true;
            }
        }
        return false;
    }
}
