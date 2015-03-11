package robotgame;

import com.google.common.collect.ImmutableList;

import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Robots implements Iterable<Robot> {

    private final List<Robot> list;

    public Robots(List<String> names) {
        ImmutableList.Builder<Robot> builder = ImmutableList.builder();
        for (int i = 0; i < names.size(); i++) {
            builder.add(new Robot(i, names.get(i)));
        }
        list = builder.build();
    }

    public Robot getRobotAt(int x, int y) {
        for (Robot robot : list) {
            if (robot.getX() == x && robot.getY() == y) {
                return robot;
            }
        }
        return null;
    }

    public List<Robot> getAliveRobots() {
        return list.stream().filter(Robot::isAlive).collect(toList());
    }

    public Robot get(int index) {
        return list.get(index);
    }

    @Override
    public Iterator<Robot> iterator() {
        return list.iterator();
    }

}
