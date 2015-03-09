package robotgame;

public class RobotClass {

    /**
     * multiplier = max hp / endurance, use it to define max hp
     */
    public static final int MULTIPLIER = 2;
    private static final String COMMANDS[] = {"skip", "move", "m", "left", "l", "right", "r", "hit", "h", "attack", "a", "scan", "s", "help", "?"};

    private final String name;

    private int x = 0;
    private int y = 0;
    private int direction = 1; // 1 - up/north, 2 - right/east, 3 - down/south, 4 - left/west

    private int endurance = 3;
    private int speed = 5;
    private int attack = 1;

    private int skillPoints = 5;
    private int ap = 0; // action points
    private int hp = endurance * MULTIPLIER; // health points

    public RobotClass(String name) {
        this.name = name;
    }

    /**
     * Changes robot's position and direction.
     *
     * @param in_X         X coordinate
     * @param in_Y         Y coordinate
     * @param in_direction direction: 1=north, 2=east, 3=south, 4=west
     */
    public void place(int in_X, int in_Y, int in_direction) {
        x = in_X;
        y = in_Y;
        direction = in_direction;
    }

    /**
     * Commands robot to turn left.
     */
    public void turnLeft() {
        direction--;
        direction = direction == 0 ? 4 : direction;
    }

    /**
     * Commands robot to turn right.
     */
    public void turnRight() {
        direction++;
        direction = direction == 5 ? 1 : direction;
    }

    /**
     * Commands robot to move forward by given amount.
     *
     * @param amount move forward by
     */
    public void moveForward(int amount) {
        if (direction == 1) {
            y += amount;
        } else if (direction == 2) {
            x += amount;
        } else if (direction == 3) {
            y -= amount;
        } else if (direction == 4) {
            x -= amount;
        }
    }

    public String getPositionAsString() {
        if (direction == 1) {
            return "(" + x + ";" + y + ") faces north";
        } else if (direction == 2) {
            return "(" + x + ";" + y + ") faces east";
        } else if (direction == 3) {
            return "(" + x + ";" + y + ") faces south";
        } else {
            return "(" + x + ";" + y + ") faces west";
        }
    }

    /**
     * Commands robot to send its current X coordinate.
     *
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Commands robot to send its current Y coordinate.
     *
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Return robot's name.
     *
     * @return robot's name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes robot's current health points. If bigger than MULTIPLIER*endurance takes value of MULTIPLIER*endurance, if lower than 0, takes 0.
     *
     * @param addHP amount to add
     */
    public void changeHP(int addHP) {
        hp += addHP;
        if (hp > MULTIPLIER * endurance) {
            hp = MULTIPLIER * endurance;
        } else if (hp < 0) {
            hp = 0;
        }
    }

    /**
     * Returns robot's current health points.
     *
     * @return robot's current health points
     */
    public int getHP() {
        return hp;
    }

    /**
     * Changes robot's endurance.
     *
     * @param addEndurance amount to add
     */
    public void changeEndurance(int addEndurance) {
        endurance += addEndurance;
    }

    /**
     * Returns robot's endurance.
     *
     * @return robot's endurance
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Changes robot's current action points.
     *
     * @param addAP amount to add
     */
    public void changeAP(int addAP) {
        ap += addAP;
    }

    /**
     * Returns robot's current action points.
     *
     * @return robot's current action points
     */
    public int getAP() {
        return ap;
    }

    /**
     * Returns X coordinate of a place in front of Robot.
     *
     * @return X coordinate of a place in front of Robot
     */
    public int getFrontX() {
        if (direction == 2) {
            return x + 1;
        } else if (direction == 4) {
            return x - 1;
        } else {
            return x;
        }
    }

    /**
     * Returns Y coordinate of a place in front of Robot.
     *
     * @return Y coordinate of a place in front of Robot
     */
    public int getFrontY() {
        if (direction == 1) {
            return y + 1;
        } else if (direction == 3) {
            return y - 1;
        } else {
            return y;
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

    /**
     * Returns robot's speed.
     *
     * @return robot's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Changes robot's speed.
     *
     * @param addHP amount to add
     */
    public void changeSpeed(int addSpeed) {
        speed += addSpeed;
    }

    /**
     * Returns robot's melee weapon strength.
     *
     * @return robot's melee weapon strength
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Changes robot's melee weapon strength.
     *
     * @param addHP amount to add
     */
    public void changeAttack(int addAttack) {
        attack += addAttack;
    }

    /**
     * Returns robot's free skill points.
     *
     * @return robot's free skill points
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * Changes robot's amount of free skill points.
     *
     * @param addSkillPoints added given amount of free skill points
     */
    public void changeSkillPoints(int addSkillPoints) {
        skillPoints += addSkillPoints;
    }
}