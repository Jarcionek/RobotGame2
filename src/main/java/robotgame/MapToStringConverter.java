package robotgame;

import java.util.List;

public class MapToStringConverter {

    private final RoboMap map;
    private final List<Robot> robots;

    public MapToStringConverter(RoboMap map, List<Robot> robots) {
        this.map = map;
        this.robots = robots;
    }

    public String getMapAsString() {
        String result = "";

        for (int y = map.getTotalHeight() - 1; y >= 0; y--) {
            for (int x = 0; x <= map.getTotalWidth() - 1; x++) {
                result += map.getMap()[x][y];
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
        throw new UnsupportedOperationException("not yet implemented"); //TODO Jarek:
    }

}
