package robotgame.game;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Robots implements Iterable<Robot> {

    private final List<Robot> list;

    public Robots(List<String> names) {
        this.list = IntStream.range(0, names.size())
                .mapToObj(i -> new Robot(i, names.get(i)))
                .collect(collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList
                ));
    }

    public Robot getRobotAt(int x, int y) {
        return list.stream()
                .filter(robot -> robot.getX() == x && robot.getY() == y)
                .findFirst().orElse(null);
    }

    public List<Robot> getAliveRobots() {
        return list.stream()
                .filter(Robot::isAlive)
                .collect(toList());
    }

    public Robot get(int index) {
        return list.get(index);
    }

    @Override
    public Iterator<Robot> iterator() {
        return list.iterator();
    }

}
