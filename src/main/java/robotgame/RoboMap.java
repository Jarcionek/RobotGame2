package robotgame;

import java.util.HashSet;
import java.util.Set;

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

    private final Set<Coordinates> bonuses = new HashSet<>();

    private Robots robots;

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

    public void setRobots(Robots robots) {
        this.robots = robots;
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public char[][] getMap() {
        return map;
    }

    /**
     * Takes position of given robot and changes symbol on the map where robot currently is.
     *
     * @param symbol    new symbol given as a char
     */
    public void loadRobot(Robot robot, char symbol) { //TODO Jarek: remove
        if (robot.getX() != 0 && robot.getY() != 0) {
            map[robot.getX()][robot.getY()] = symbol;
        }
    }

    public void addBonus(int x, int y) {
        if (bonuses.contains(new Coordinates(x, y))) {
            throw new IllegalArgumentException(String.format("There is already a bonus at (%s,%s)", x, y));
        }
        bonuses.add(new Coordinates(x, y));
        map[x][y] = BONUS_SYMBOL;
    }

    public void removeBonus(int x, int y) {
        if (!bonuses.contains(new Coordinates(x, y))) {
            throw new IllegalArgumentException(String.format("No bonus at (%s,%s), bonuses: %s", x, y, bonuses));
        }
        bonuses.remove(new Coordinates(x, y));
        map[x][y] = EMPTY_FIELD_SYMBOL;
    }

    public boolean isEmpty(int x, int y) {
        return !isWall(x, y) && robots.getRobotAt(x, y) == null && !isBonus(x, y);
    }

    public boolean isWall(int x, int y) {
        return x == 0 || x == totalWidth - 1 || y == 0 || y == totalHeight - 1;
    }

    public boolean isBonus(int x, int y) {
        return bonuses.stream().filter(c -> c.x() == x && c.y() == y).count() == 1;
    }


    private class Coordinates {

        private final int x;
        private final int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Coordinates that = (Coordinates) o;

            return this.x == that.x && this.y == that.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return String.format("(%s,%s)", x, y);
        }
    }

}
