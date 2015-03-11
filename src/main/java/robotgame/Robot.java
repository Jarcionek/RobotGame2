package robotgame;

public class Robot {

    /**
     * multiplier = max hp / endurance, use it to define max hp
     */
    public static final int ENDURANCE_MULTIPLIER = 2;
    private static final String COMMANDS[] = {"skip", "move", "m", "left", "l", "right", "r", "hit", "h", "attack", "a", "scan", "s", "help", "?"};

    private final int id;
    private final String name;

    private int x = -1;
    private int y = -1;
    private Direction direction;

    private int endurance = 3;
    private int speed = 5;
    private int attack = 1;

    private int skillPoints = 5;
    private int ap = 0; // action points
    private int hp = endurance * ENDURANCE_MULTIPLIER; // health points

    public Robot(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void place(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnLeft() {
        direction = direction.left();
    }

    public void turnRight() {
        direction = direction.right();
    }

    public void moveForward(int distance) {
        switch (direction) {
            case NORTH: y += distance; break;
            case EAST:  x += distance; break;
            case SOUTH: y -= distance; break;
            case WEST:  x -= distance; break;
        }
    }

    public String getPositionAsString() {
        return String.format("(%s;%s) faces %s", x, y, direction.name().toLowerCase());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * If modified HP would be greater than ENDURANCE_MULTIPLIER * endurance, it will be equal to
     * ENDURANCE_MULTIPLIER * endurance instead. If it would be lower than 0, it will be 0.
     */
    public void changeHP(int delta) {
        hp += delta;
        if (hp > ENDURANCE_MULTIPLIER * endurance) {
            hp = ENDURANCE_MULTIPLIER * endurance;
        } else if (hp <= 0) {
            hp = 0;
            x = -1;
            y = -1;
            direction = null;
        }
    }

    public int getHP() {
        return hp;
    }

    public void changeEndurance(int delta) {
        endurance += delta;
    }

    public int getEndurance() {
        return endurance;
    }

    public void changeAP(int delta) {
        ap += delta;
    }

    public int getAP() {
        return ap;
    }

    public int getFrontX() {
        switch (direction) {
            case EAST: return x + 1;
            case WEST: return x - 1;
            default: return x;
        }
    }

    public int getFrontY() {
        switch (direction) {
            case NORTH: return y + 1;
            case SOUTH: return y - 1;
            default: return y;
        }
    }

    /**
     * Makes sure if robot knows given command (case doesn't matter). Keep in mind that commands can be unique for each robot.
     *
     * @param command command to check
     * @return false if robot understands command, true if not understand it
     */
    public boolean unknownCommand(String command) {
        for (int i = 0; i < COMMANDS.length; i++) {
            if (command.toLowerCase().equals(COMMANDS[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns all commands known by robot as a string, e.g. "move, shoot, stepback"
     *
     * @return known commands as a string
     */
    public String knownCommands() {
        String result = "";

        for (int i = 0; i < COMMANDS.length; i++) {
            if (COMMANDS[i] != null) {
                result += COMMANDS[i] + ", ";
            }
        }

        result = result.substring(0, result.length() - 2);

        return result;
    }

    public int getSpeed() {
        return speed;
    }

    public void changeSpeed(int delta) {
        speed += delta;
    }

    public int getAttack() {
        return attack;
    }

    public void changeAttack(int delta) {
        attack += delta;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void changeSkillPoints(int delta) {
        skillPoints += delta;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isOnMap() {
        return x > 0 && y > 0;
    }

}