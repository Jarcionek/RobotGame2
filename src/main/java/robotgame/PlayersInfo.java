package robotgame;

/**
 * Stores informations of robots for easy print. Each robot has to be loaded manually.
 *
 * @author Jaroslaw Pawlak
 */
public class PlayersInfo {
    private int players;
    private String infoArray[];

    /**
     * Constructs an object with a string array of given length.
     *
     * @param numberOfPlayers number of cells in array
     */
    public PlayersInfo(int numberOfPlayers) {
        players = numberOfPlayers;
        infoArray = new String[players];
    }

    /**
     * Loads all information about given robot and saves it in a cell with given index.
     *
     * @param i index where to store
     */
    public void load(int i, Robot robot) {
        if (robot.getHP() == 0) {
            infoArray[i - 1] = (i - 1) + " - ";
            infoArray[i - 1] += robot.getName() + ": DEAD ";
            infoArray[i - 1] += robot.getEndurance() + " endurance, ";
            infoArray[i - 1] += robot.getSpeed() + " speed, ";
            infoArray[i - 1] += robot.getAttack() + " attack.";
        } else {
            infoArray[i - 1] = (i - 1) + " - ";
            infoArray[i - 1] += robot.getName() + ": ";
            infoArray[i - 1] += robot.getPositionAsString() + ", ";
            infoArray[i - 1] += robot.getHP() + " HP, ";
            infoArray[i - 1] += robot.getEndurance() + " endurance, ";
            infoArray[i - 1] += robot.getSpeed() + " speed, ";
            infoArray[i - 1] += robot.getAttack() + " attack.";
        }
    }

    /**
     * Prints information about all players as a string.
     *
     * @return information about all players as a string
     */
    public String print() {
        String result = "";
        for (int i = 0; i < players; i++) {
            result += infoArray[i] + "\n";
        }
        return result;
    }
}

