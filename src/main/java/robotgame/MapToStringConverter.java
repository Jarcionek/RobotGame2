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
}
