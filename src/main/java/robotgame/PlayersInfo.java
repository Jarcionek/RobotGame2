package robotgame;

/**
 * Stores informations of robots for easy print. Each robot has to be loaded manually.
 *
 * @author Jaroslaw Pawlak
 */
public class PlayersInfo {

    private String infoArray[];

    /**
     * Constructs an object with a string array of given length.
     *
     * @param numberOfPlayers number of cells in array
     */
    public PlayersInfo(int numberOfPlayers) {
        infoArray = new String[numberOfPlayers];
    }

    /**
     * Loads all information about given robot and saves it in a cell with given index.
     *
     * @param i index where to store
     */
    public void load(int i, Robot robot) {
        if (robot.getHP() == 0) {
            infoArray[i] = i + " - ";
            infoArray[i] += robot.getName() + ": DEAD ";
            infoArray[i] += robot.getEndurance() + " endurance, ";
            infoArray[i] += robot.getSpeed() + " speed, ";
            infoArray[i] += robot.getAttack() + " attack.";
        } else {
            infoArray[i] = i + " - ";
            infoArray[i] += robot.getName() + ": ";
            infoArray[i] += robot.getPositionAsString() + ", ";
            infoArray[i] += robot.getHP() + " HP, ";
            infoArray[i] += robot.getEndurance() + " endurance, ";
            infoArray[i] += robot.getSpeed() + " speed, ";
            infoArray[i] += robot.getAttack() + " attack.";
        }
    }

    /**
     * Prints information about all players as a string.
     *
     * @return information about all players as a string
     */
    public String print() {
        String result = "";
        for (int i = 0; i < infoArray.length; i++) {
            result += infoArray[i] + "\n";
        }
        return result;
    }
}

