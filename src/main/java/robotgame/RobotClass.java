package robotgame;

/**
 * Robot has its name, position and direction which can be change by commands (from commands list) or instantly (when e.g. naming a robot or deleting robot from the map). Robot also has its statistics such as health and action points, speed, endurance and attack.
 * @author Jaroslaw Pawlak
 */
public class RobotClass {
    private String name;
    private int X=0;
    private int Y=0;
    private int direction=1; // 1 - up/north, 2 - right/east, 3 - down/south, 4 - left/west
    private int AP=0; //action points
    private int endurance=3;
    /**
     * multiplier = max hp / endurance, use it to define max hp
     */
    public static final int MULTIPLIER=2;
    private int HP=endurance*MULTIPLIER; //health points
    private String commands[] = {"skip", "move", "m", "left", "l", "right", "r", "hit", "h", "attack", "a", "scan", "s", "help", "?"};
    private int speed=5;
    private int attack=1;
    private int skillPoints=5;

    /**
     * Constructs a robot with given name, position and direction.
     * @param in_name robot's name
     * @param in_X X coordinate
     * @param in_Y Y coordinate
     * @param in_direction direction: 1=north, 2=east, 3=south, 4=west
     */
    public RobotClass(String in_name, int in_X, int in_Y, int in_direction) {
        name = in_name;
        X = in_X;
        Y = in_Y;
        direction = in_direction;
    }

    /**
     * Constructs a robot with given name, position (0;0) and facing north.
     * @param in_name robot's name
     */
    public RobotClass(String in_name) {
        name = in_name;
    }

    /**
     * Constructs a robot with no name, position (0;0) and facing north.
     */
    public RobotClass() {
    }

    /**
     * Changes robot's position and direction.
     * @param in_X X coordinate
     * @param in_Y Y coordinate
     * @param in_direction direction: 1=north, 2=east, 3=south, 4=west
     */
    public void place(int in_X, int in_Y, int in_direction) {
        X = in_X;
        Y = in_Y;
        direction = in_direction;
    }

    /**
     * Changes robot's name.
     * @param newName robot's new name
     */
    public void changeName(String newName) {
        name = newName;
    }

    /**
     * Changes robot's direction. (1 - north, 2 - east, 3 - south, 4 - west)
     * @param newDirection robot's new direction
     */
    public void changeDirection(int newDirection) {
        direction = newDirection;
    }

    /**
     * Commands robot to turn left.
     */
    public void turnLeft() {
        direction--;
        direction = direction==0? 4 : direction;
    }

    /**
     * Commands robot to turn right.
     */
    public void turnRight() {
        direction++;
        direction = direction==5? 1 : direction;
    }

    /**
     * Commands robot to move forward by given amount.
     * @param amount move forward by
     */
    public void moveForward(int amount) {
        if(direction==1) {
            Y += amount;
        } else if (direction==2) {
            X += amount;
        } else if (direction==3) {
            Y -= amount;
        } else if (direction==4) {
            X -= amount;
        }
    }

    /**
     * Commands robot to send its current position and facing direction as a string, e.g. "(7;-2) faces east".
     * @return robot's postion and direction
     */
    public String sendPosition() {
        if(direction==1) {
            return "(" + X + ";" + Y + ") faces north";
        } else if (direction==2) {
            return "(" + X + ";" + Y + ") faces east";
        } else if (direction==3) {
            return "(" + X + ";" + Y + ") faces south";
        } else {
            return "(" + X + ";" + Y + ") faces west";
        }
    }

    /**
     * Commands robot to send its current X coordinate.
     * @return X coordinate
     */
    public int getX() {
        return X;
    }

    /**
     * Commands robot to send its current Y coordinate.
     * @return Y coordinate
     */
    public int getY() {
        return Y;
    }

    /**
     * Commands robot to send its current direction: 1=north, 2=east, 3=south, 4=west
     * @return direction
     */
    public int sendDirection() {
        return direction;
    }

    /**
     * Return robot's name.
     * @return robot's name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes robot's current health points. If bigger than MULTIPLIER*endurance takes value of MULTIPLIER*endurance, if lower than 0, takes 0.
     * @param addHP amount to add
     */
    public void changeHP(int addHP) {
        HP += addHP;
        if (HP>MULTIPLIER*endurance) {
            HP = MULTIPLIER*endurance;
        } else if (HP<0) {
            HP=0;
        }
    }

    /**
     * Returns robot's current health points.
     * @return robot's current health points
     */
    public int getHP() {
        return HP;
    }

    /**
     * Changes robot's endurance.
     * @param addEndurance amount to add
     */
    public void changeEndurance(int addEndurance) {
        endurance += addEndurance;
    }

    /**
     * Returns robot's endurance.
     * @return robot's endurance
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Changes robot's current action points.
     * @param addAP amount to add
     */
    public void changeAP(int addAP) {
        AP += addAP;
    }

    /**
     * Returns robot's current action points.
     * @return robot's current action points
     */
    public int getAP() {
        return AP;
    }
    
    /**
     * Returns X coordinate of a place in front of Robot.
     * @return X coordinate of a place in front of Robot
     */
    public int getFrontX() {
        if(direction==2) {
            return X+1;
        } else if (direction==4) {
            return X-1;
        } else {
            return X;
        }
    }

    /**
     * Returns Y coordinate of a place in front of Robot.
     * @return Y coordinate of a place in front of Robot
     */
    public int getFrontY() {
        if(direction==1) {
            return Y+1;
        } else if (direction==3) {
            return Y-1;
        } else {
            return Y;
        }
    }

    /**
     * Makes sure if robot knows given command (case doesn't matter). Keep in mind that commands can be unique for each robot.
     * @param command command to check
     * @return false if robot understands command, true if not understand it
     */
    public boolean unknownCommand(String command) {
        for (int i=0; i<commands.length; i++) {
            if(command.toLowerCase().equals(commands[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns all commands known by robot as a string, e.g. "move, shoot, stepback"
     * @return known commands as a string
     */
    public String knownCommands() {
        String result = "";

        for (int i=0; i<commands.length; i++) {
            if (commands[i] != null) {
                result += commands[i] + ", ";
            }
        }

        result = result.substring(0, result.length()-2);

        return result;
    }

    /**
     * Returns robot's speed.
     * @return robot's speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Changes robot's speed.
     * @param addHP amount to add
     */
    public void changeSpeed(int addSpeed) {
        speed += addSpeed;
    }

    /**
     * Returns robot's melee weapon strength.
     * @return robot's melee weapon strength
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Changes robot's melee weapon strength.
     * @param addHP amount to add
     */
    public void changeAttack(int addAttack) {
        attack += addAttack;
    }

     /**
     * Returns robot's free skill points.
     * @return robot's free skill points
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * Changes robot's amount of free skill points.
     * @param addSkillPoints added given amount of free skill points
     */
    public void changeSkillPoints(int addSkillPoints) {
        skillPoints += addSkillPoints;
    }
}