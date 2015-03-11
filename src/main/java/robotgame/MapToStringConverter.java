package robotgame;

public class MapToStringConverter {

    private final RoboMap map;
    private final Robots robots;

    public MapToStringConverter(RoboMap map, Robots robots) {
        this.map = map;
        this.robots = robots;
    }

    public String getMapAsString() {
        final char chars[][] = new char[map.getTotalHeight()][map.getTotalWidth()];

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                chars[x][y] = map.getMap()[x][y];
            }
        }

        for (Robot robot : robots) {
            if (robot.getX() != 0 && robot.getY() != 0) {
                chars[robot.getX()][robot.getY()] = RoboMap.ROBOT_SYMBOL;
            }
        }

        String result = "";

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                result += chars[x][y];
            }
            result += "\n";
        }

        return result;
    }

    public String getMapAsStringWithRobotsIds() {
        final char chars[][] = new char[map.getTotalHeight()][map.getTotalWidth()];

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                chars[x][y] = map.getMap()[x][y];
            }
        }

        for (Robot robot : robots) {
            if (robot.getHP() > 0) {
                chars[robot.getX()][robot.getY()] = (char) ('0' + robot.getId());
            }
        }

        String result = "";

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                result += chars[x][y];
            }
            result += "\n";
        }

        return result;
    }

    public String getMapAsStringWithHighlighted(Robot highlightedRobot) {
        final char chars[][] = new char[map.getTotalHeight()][map.getTotalWidth()];

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                chars[x][y] = map.getMap()[x][y];
            }
        }

        for (Robot robot : robots) {
            if (robot.getX() != 0 && robot.getY() != 0) {
                chars[robot.getX()][robot.getY()] = RoboMap.ROBOT_SYMBOL;
            }
        }
        chars[highlightedRobot.getX()][highlightedRobot.getY()] = RoboMap.ACTIVE_ROBOT_SYMBOL;

        String result = "";

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                result += chars[x][y];
            }
            result += "\n";
        }

        return result;
    }

}
