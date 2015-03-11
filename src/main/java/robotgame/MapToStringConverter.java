package robotgame;

public class MapToStringConverter {

    public static final char ROBOT_SYMBOL = '#';
    public static final char ACTIVE_ROBOT_SYMBOL = 'O';
    public static final char BONUS_SYMBOL = '$';
    public static final char EMPTY_FIELD_SYMBOL = '.';
    public static final char WALL_CORNER = '+';
    public static final char WALL_HORIZONTAL = '-';
    public static final char WALL_VERTICAL = '|';

    private final RoboMap map;
    private final Robots robots;

    public MapToStringConverter(RoboMap map, Robots robots) {
        this.map = map;
        this.robots = robots;
    }

    public String getMapAsString() {
        final char[][] chars = mapWithBonuses();

        for (Robot robot : robots) {
            if (robot.isOnMap()) {
                chars[robot.getX()][robot.getY()] = MapToStringConverter.ROBOT_SYMBOL;
            }
        }

        return asString(chars);
    }

    public String getMapAsStringWithRobotsIds() {
        final char[][] chars = mapWithBonuses();

        for (Robot robot : robots) {
            if (robot.isAlive()) {
                chars[robot.getX()][robot.getY()] = (char) ('0' + robot.getId());
            }
        }

        return asString(chars);
    }

    public String getMapAsStringWithHighlighted(Robot highlightedRobot) {
        final char[][] chars = mapWithBonuses();

        for (Robot robot : robots) {
            if (robot.isAlive()) {
                chars[robot.getX()][robot.getY()] = MapToStringConverter.ROBOT_SYMBOL;
            }
        }
        chars[highlightedRobot.getX()][highlightedRobot.getY()] = MapToStringConverter.ACTIVE_ROBOT_SYMBOL;

        return asString(chars);
    }

    private char[][] mapWithBonuses() {
        final char[][] chars = emptyMap();

        for (Coordinates coordinates : map.getBonuses()) {
            chars[coordinates.x()][coordinates.y()] = MapToStringConverter.BONUS_SYMBOL;
        }

        return chars;
    }

    private char[][] emptyMap() {
        int totalWidth = map.getWidth() + 2;
        int totalHeight = map.getHeight() + 2;

        final char chars[][] = new char[totalWidth][totalHeight];

        for (int x = 1; x < totalWidth - 1; x++) {
            for (int y = 1; y < totalHeight - 1; y++) {
                chars[x][y] = EMPTY_FIELD_SYMBOL;
            }
        }

        chars[0][0] = WALL_CORNER;
        chars[totalWidth - 1][0] = WALL_CORNER;
        chars[0][totalHeight - 1] = WALL_CORNER;
        chars[totalWidth - 1][totalHeight - 1] = WALL_CORNER;

        for (int x = 1; x < totalWidth - 1; x++) {
            chars[x][0] = WALL_HORIZONTAL;
            chars[x][totalHeight - 1] = WALL_HORIZONTAL;
        }

        for (int y = 1; y < totalHeight - 1; y++) {
            chars[0][y] = WALL_VERTICAL;
            chars[totalWidth - 1][y] = WALL_VERTICAL;
        }

        return chars;
    }

    private String asString(char[][] chars) {
        String result = "";
        int height = chars[0].length;

        for (int y = height - 1; y >= 0; y--) {
            for (char[] row : chars) {
                result += row[y];
            }
            result += "\n";
        }

        return result;
    }

}
