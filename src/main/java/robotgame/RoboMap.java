package robotgame;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * Position (1;1) should be a bottom left hand corner.
 *
 * Indices are 1-based. For width = 3, the valid x positions are 1, 2 and 3.
 */
public class RoboMap {

    private final int width;
    private final int height;

    private final Set<Coordinates> bonuses = new HashSet<>();

    private Robots robots;

    public RoboMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setRobots(Robots robots) {
        this.robots = robots;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<Coordinates> getBonuses() {
        return unmodifiableSet(bonuses);
    }

    public void addBonus(int x, int y) {
        if (bonuses.contains(new Coordinates(x, y))) {
            throw new IllegalArgumentException(String.format("There is already a bonus at (%s,%s)", x, y));
        }
        bonuses.add(new Coordinates(x, y));
    }

    public void removeBonus(int x, int y) {
        if (!bonuses.contains(new Coordinates(x, y))) {
            throw new IllegalArgumentException(String.format("No bonus at (%s,%s), bonuses: %s", x, y, bonuses));
        }
        bonuses.remove(new Coordinates(x, y));
    }

    public boolean isEmpty(int x, int y) {
        return !isWall(x, y) && robots.getRobotAt(x, y) == null && !isBonus(x, y);
    }

    public boolean isWall(int x, int y) {
        return x == 0 || x == width + 1 || y == 0 || y == height + 1;
    }

    public boolean isBonus(int x, int y) {
        return bonuses.stream().filter(c -> c.x() == x && c.y() == y).count() == 1;
    }

}
