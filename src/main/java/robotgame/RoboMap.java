package robotgame;

/**
 * Class RoboMap creates map made of chars for RobotGame. Chars are stored in a defined array and can be changed manually. Cooperate with Robot class. Position (0;0) is printed as a bottom left hand corner.
 *
 * @author Jaroslaw Pawlak
 */
public class RoboMap {
    private int X;
    private int Y;
    private char map[][];
    public static final char ROBOT_SYMBOL = '#';
    public static final char ACTIVE_ROBOT_SYMBOL = 'O';
    public static final char BONUS_SYMBOL = '$';
    public static final char EMPTY_FIELD_SYMBOL = '.';

    /**
     * Creates a map (char array) of sizes equal given+2, filled with dots (char 183), lines on sides and pluses in corners.
     *
     * @param width  map width
     * @param height map height
     */
    public RoboMap(int width, int height) {
        X = width + 2;
        Y = height + 2;
        map = new char[X][Y];

        for (int i = 0; i < X; i++) { //fill entire map with empty field symbols
            for (int j = 0; j < Y; j++) {
                map[i][j] = EMPTY_FIELD_SYMBOL;
            }
        }

        //corners
        map[0][0] = '+';
        map[X - 1][0] = '+';
        map[0][Y - 1] = '+';
        map[X - 1][Y - 1] = '+';
        //end corners

        for (int i = 1; i < X - 1; i++) { //top and botom edges
            map[i][0] = '-';
            map[i][Y - 1] = '-';
        }
        for (int i = 1; i < Y - 1; i++) { //right and left hand sides
            map[0][i] = '|';
            map[X - 1][i] = '|';
        }

    }

    public String asString() {
        String result = "";

        for (int y = Y - 1; y >= 0; y--) {
            for (int x = 0; x <= X - 1; x++) {
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
