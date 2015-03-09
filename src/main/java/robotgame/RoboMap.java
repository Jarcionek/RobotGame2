package robotgame;

/**
 * Position (0;0) is printed as a bottom left hand corner.
 */
public class RoboMap {

    public static final char ROBOT_SYMBOL = '#';
    public static final char ACTIVE_ROBOT_SYMBOL = 'O';
    public static final char BONUS_SYMBOL = '$';
    public static final char EMPTY_FIELD_SYMBOL = '.';
    public static final char WALL_CORNER = '+';
    public static final char WALL_HORIZONTAL = '-';
    public static final char WALL_VERTICAL = '|';

    private final int totalWidth;
    private final int totalHeight;
    private final char map[][];

    public RoboMap(int width, int height) {
        totalWidth = width + 2;
        totalHeight = height + 2;
        map = new char[totalWidth][totalHeight];

        for (int i = 0; i < totalWidth; i++) {
            for (int j = 0; j < totalHeight; j++) {
                map[i][j] = EMPTY_FIELD_SYMBOL;
            }
        }

        map[0][0] = WALL_CORNER;
        map[totalWidth - 1][0] = WALL_CORNER;
        map[0][totalHeight - 1] = WALL_CORNER;
        map[totalWidth - 1][totalHeight - 1] = WALL_CORNER;

        for (int i = 1; i < totalWidth - 1; i++) {
            map[i][0] = WALL_HORIZONTAL;
            map[i][totalHeight - 1] = WALL_HORIZONTAL;
        }

        for (int i = 1; i < totalHeight - 1; i++) {
            map[0][i] = WALL_VERTICAL;
            map[totalWidth - 1][i] = WALL_VERTICAL;
        }
    }

    public String asString() {
        String result = "";

        for (int y = totalHeight - 1; y >= 0; y--) {
            for (int x = 0; x <= totalWidth - 1; x++) {
                result += map[x][y];
            }
            result += "\n";
        }

        return result;
    }

    /**
     * Takes position of given robot and changes symbol on the map where robot currently is.
     *
     * @param robotName Robot's name
     * @param symbol    new symbol given as a char
     */
    public void loadRobot(RobotClass robotName, char symbol) {
        if (robotName.getX() != 0 && robotName.getY() != 0) {
            map[robotName.getX()][robotName.getY()] = symbol;
        }

    }

    /**
     * Takes position of given robot and changes symbol on the map where robot currently is. REMINDER: 183 - dot, 215 - cross.
     *
     * @param robotName Robot's name
     * @param symbol    new symbol given as an int (automaticaly converted into char)
     */
    public void loadRobot(RobotClass robotName, int symbol) {
        if (robotName.getX() != 0 && robotName.getY() != 0) {
            map[robotName.getX()][robotName.getY()] = (char) symbol;
        }
    }

    /**
     * Changes symbol on the map in the given position.
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param newSymbol new symbol given as a char
     */
    public void changeBox(int x, int y, char newSymbol) {
        map[x][y] = newSymbol;
    }

    /**
     * Returns the symbol at given location.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the symbol at given location
     */
    public char getBox(int x, int y) {
        return map[x][y];
    }

    /**
     * Returns the symbol from the box in front of given robot.
     *
     * @param robotName robot name
     * @return symbol in front of given robot
     */
    public char getBoxInFrontOf(RobotClass robotName) {
        return map[robotName.getFrontX()][robotName.getFrontY()];
    }

}
