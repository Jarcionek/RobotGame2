package robotgame;

import com.google.common.collect.ImmutableList;
import robotgame.io.InputReader;
import robotgame.io.OutputPrinter;
import robotgame.io.PopUp;
import robotgame.io.ProgramTerminator;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Joiner.on;
import static robotgame.Attribute.AP;
import static robotgame.Attribute.ATTACK;
import static robotgame.Attribute.ENDURANCE;
import static robotgame.Attribute.HP;
import static robotgame.Attribute.SPEED;
import static robotgame.Robot.ENDURANCE_MULTIPLIER;

public class RobotGame {

    private final InputReader inputReader;
    private final OutputPrinter outputPrinter;
    private final PopUp popUp;
    private final RandomNumberGenerator bonusRandomNumberGenerator;
    private final ProgramTerminator programTerminator;
    private final ListShuffler listShuffler;

    public RobotGame(InputReader inputReader,
                     OutputPrinter outputPrinter,
                     PopUp popUp,
                     WeightedList<Bonus> bonuses,
                     RandomNumberGenerator bonusRandomNumberGenerator,
                     ProgramTerminator programTerminator,
                     ListShuffler listShuffler) {
        this.inputReader = inputReader;
        this.outputPrinter = outputPrinter;
        this.popUp = popUp;
        this.bonusRandomNumberGenerator = bonusRandomNumberGenerator;
        this.programTerminator = programTerminator;
        this.listShuffler = listShuffler;

        this.bonuses = bonuses;
    }

    private int numberOfPlayers = 0;
    private DigitsChecker checkInput = new DigitsChecker();
    private static final int MAP_MAX_WIDTH = 75;
    private static final int MAP_MAX_HEIGHT = 25;
    private int mapWidth = 0;
    private int mapHeight = 0;
    private RoboMap map;
    private Robots robots;
    private boolean unknownCommand = false; //used also as unreachable command
    private boolean displayMap = true;


    private MapToStringConverter mapToStringConverter;

    //BONUSES
    private final WeightedList<Bonus> bonuses;
    /**
     * Percentile chance that at the beginning of player's turn, the bonus will appear.
     */
    private static final int CHANCE = 30;
    /**
     * How many percent of all fields on the map have to be filled with bonuses to stop next bonus from appearing.
     */
    private static final int FULNESS = 10;
    private int numberOfBonusesOnTheMap = 0;

    //LOAD DATA FROM USER
    private void getNumberOfPlayers() {
        do {
            outputPrinter.print("Enter the number of players: ");
            String input = inputReader.next();
            checkInput.load(input, 1, 10);

            if (checkInput.containsNonDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.isOutOfRange()) {
                outputPrinter.println("At least one player is required. Maximum 10 players.");
            } else {
                numberOfPlayers = checkInput.getInteger();
            }

        } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());
    }

    private void getMapSize() {
        do {
            outputPrinter.print("Enter map's width: ");
            String input = inputReader.next();
            checkInput.load(input, 10, MAP_MAX_WIDTH);

            if (checkInput.containsNonDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.isOutOfRange()) {
                outputPrinter.println("Map's width cannot be lower than 10 or bigger than " + MAP_MAX_WIDTH + ".");
            } else {
                mapWidth = checkInput.getInteger();
            }

        } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());


        do {
            outputPrinter.print("Enter map's height: ");
            String input = inputReader.next();
            checkInput.load(input, 10, MAP_MAX_HEIGHT);

            if (checkInput.containsNonDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.isOutOfRange()) {
                outputPrinter.println("Map's height cannot be lower than 10 or bigger than " + MAP_MAX_HEIGHT + ".");
            } else {
                mapHeight = checkInput.getInteger();
            }

        } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());

        map = new RoboMap(mapWidth, mapHeight);
    }

    private void createPlayers() {
        List<String> names = new ArrayList<>(numberOfPlayers);

        for (int i = 1; i <= numberOfPlayers; i++) {
            String name;
            boolean repeatedName;

            do {
                outputPrinter.print("Enter " + i + ". robot's name: ");
                name = inputReader.next();
                repeatedName = false;

                if (names.contains(name)) {
                    repeatedName = true;
                    outputPrinter.println("Another player has already chosen that name.");
                }

            } while (repeatedName);

            names.add(name);
        }

        robots = new Robots(listShuffler.shuffle(names));
        map.setRobots(robots);
    }

    private void allocateSkillPoints() {
        for (int i = 0; i < numberOfPlayers; i++) {
            outputPrinter.println("===================================");
            outputPrinter.println("Allocate " + robots.get(i).getName() + "'s skill points.");
            popUp.show("Allocate " + robots.get(i).getName() + "'s skill points.", "Allocate skill points");
            outputPrinter.println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
            List<String> possibilities = ImmutableList.of("endurance", "e", "speed", "s", "attack", "a");

            outputPrinter.println("Each point added to endurance increases your HP by " + ENDURANCE_MULTIPLIER + " points.");
            outputPrinter.println("Each point added to speed increases your AP by 1 point.");
            outputPrinter.println("Each point added to attack increases dealing damages by 1 point.");
            outputPrinter.println("Which skill you would like to add point to?");
            outputPrinter.println("TIP: instead of writing entire skill name,\nyou can just write its first letter.");
            do {
                boolean incorrect_input = true;
                do {
                    outputPrinter.println("You have " + robots.get(i).getSkillPoints() + " skill points to allocate.");
                    String input = inputReader.next();

                    if (possibilities.contains(input.toLowerCase())) {
                        incorrect_input = false;
                    }

                    if (incorrect_input) {
                        outputPrinter.println("Incorrect skill.");
                        outputPrinter.println("You can allocate skill points to the following skills: " + on(", ").join(possibilities));
                    } else {
                        robots.get(i).changeSkillPoints(-1);
                        if (input.toLowerCase().equals("endurance") || input.toLowerCase().equals("e")) {
                            robots.get(i).changeEndurance(1);
                            outputPrinter.println("Your endurance is " + robots.get(i).getEndurance() + " now.");
                        } else if (input.toLowerCase().equals("speed") || input.toLowerCase().equals("s")) {
                            robots.get(i).changeSpeed(1);
                            outputPrinter.println("Your speed is " + robots.get(i).getSpeed() + " now.");
                        } else if (input.toLowerCase().equals("attack") || input.toLowerCase().equals("a")) {
                            robots.get(i).changeAttack(1);
                            outputPrinter.println("Your attack is " + robots.get(i).getAttack() + " now.");
                        }
                    }

                } while (incorrect_input);
            } while (robots.get(i).getSkillPoints() > 0);
            String antiCheat = inputReader.nextLine(); //suppose that player one has 5 free skill points, write "e e e e e e" and press enter - six "e" would cause automatically adding one point of next player to his endurance
            robots.get(i).changeHP(ENDURANCE_MULTIPLIER * robots.get(i).getEndurance());
        }
        outputPrinter.println("===================================");
    }

    /**
     * Randomizes with given chance if bonus will appear in the moment of calling method.
     * If randomized at non empty field, randomizing is repeated.
     * Throws dialog window with coordinates of new bonus if appears.
     *
     * @param chance percentile chance, if lower than 0 becomes 0, if higher than 100 becomes 100
     */
    private void randomizeBonus(int chance) {
        if (FULNESS / 100.0 >= (double) numberOfBonusesOnTheMap / (mapWidth * mapHeight) && bonusRandomNumberGenerator.nextInt(100) < chance) {
            int x;
            int y;
            do {
                x = bonusRandomNumberGenerator.nextInt(mapWidth) + 1;
                y = bonusRandomNumberGenerator.nextInt(mapHeight) + 1;
            } while (!map.isEmpty(x, y));
            map.addBonus(x, y);
            numberOfBonusesOnTheMap++;
            popUp.show("New bonus has appeared at (" + x + ";" + y + ")!", "New bonus!");
        }
    }

    private void placeRobotsOnTheMap() {
        for (int i = 0; i < numberOfPlayers; i++) {
            int x = 0;
            int y = 0;
            Direction direction = null;
            boolean incorrect_direction = false;
            boolean alreadyUsed = false;

            do { //get X and Y
                alreadyUsed = false; //postion already used by another player

                outputPrinter.print(mapToStringConverter.getMapAsString());

                do {
                    outputPrinter.print("Enter " + robots.get(i).getName() + "'s X coordinate: ");
                    String input = inputReader.next();
                    checkInput.load(input, 1, mapWidth);

                    if (checkInput.containsNonDigits()) {
                        outputPrinter.println("Incorrect value. Only digits are allowed.");
                    } else if (checkInput.isOutOfRange()) {
                        outputPrinter.println("Out of map.");
                    } else {
                        x = checkInput.getInteger();
                    }

                } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());

                do {
                    outputPrinter.print("Enter " + robots.get(i).getName() + "'s Y coordinate: ");
                    String input = inputReader.next();
                    checkInput.load(input, 1, mapHeight);

                    if (checkInput.containsNonDigits()) {
                        outputPrinter.println("Incorrect value. Only digits are allowed.");
                    } else if (checkInput.isOutOfRange()) {
                        outputPrinter.println("Out of map.");
                    } else {
                        y = checkInput.getInteger();
                    }

                } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());

                if (robots.getRobotAt(x, y) != null) {
                    outputPrinter.println("There is already another robot.");
                    alreadyUsed = true;
                }

            } while (alreadyUsed); //end get X and Y

            do { //get direction
                outputPrinter.print("Enter " + robots.get(i).getName() + "'s facing direction: ");
                String input = inputReader.next().toLowerCase();
                incorrect_direction = false;

                try {
                    direction = Direction.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    outputPrinter.println("Incorrect value. Your robot can only face north, east, south or west.");
                    incorrect_direction = true;
                }

            } while (incorrect_direction); //end get direction

            String antiCheat = inputReader.nextLine();
            robots.get(i).place(x, y, direction);
        }
    }

    //COMMANDS

    /**
     * move command, in case of attempt of moving into another robot or wall, the information is printed; bonus are taken automatically.
     *
     * @param robot current robot
     */
    private void move(Robot robot) {
        if (map.isBonus(robot.getFrontX(), robot.getFrontY())) { //if bonus
            robot.moveForward(1);
            robot.changeAP(-1);
            map.removeBonus(robot.getX(), robot.getY());
            numberOfBonusesOnTheMap--;

            Bonus bonus = bonuses.getRandom();
            int amount = bonus.getDelta();
            Attribute attribute = bonus.getAttribute();

            if (attribute.equals(AP)) { //what kind of bonus is it
                robot.changeAP(amount);
                outputPrinter.println(amount + " action points added. " + robot.getAP() + " AP left.");
                popUp.show(amount + " action points added. " + robot.getAP() + " AP left.", "Bonus taken");
            } else if (attribute.equals(HP)) {
                robot.changeHP(amount);
                if (robot.getHP() == ENDURANCE_MULTIPLIER * robot.getEndurance()) {
                    outputPrinter.println("You have been completely healed.");
                    popUp.show("You have been completely healed.", "Bonus taken");
                } else {
                    outputPrinter.println("You have been healed for " + amount + " points. Your current HP is " + robot.getHP() + ".");
                    popUp.show("You have been healed for " + amount + " points. Your current HP is " + robot.getHP() + ".", "Bonus taken");
                }
            } else if (attribute.equals(ENDURANCE)) {
                robot.changeEndurance(amount);
                outputPrinter.println("Your endurance has been increased by " + amount + ". Your current endurance is " + robot.getEndurance() + ".");
                popUp.show("Your endurance has been increased by " + amount + ". Your current endurance is " + robot.getEndurance() + ".", "Bonus taken");
            } else if (attribute.equals(SPEED)) {
                robot.changeSpeed(amount);
                outputPrinter.println("Your speed has been increased by " + amount + ". Your current speed is " + robot.getSpeed() + ".");
                popUp.show("Your speed has been increased by " + amount + ". Your current speed is " + robot.getSpeed() + ".", "Bonus taken");
            } else if (attribute.equals(ATTACK)) {
                robot.changeAttack(amount);
                outputPrinter.println("Your attack has been increased by " + amount + ". Your current attack is " + robot.getAttack() + ".");
                popUp.show("Your attack has been increased by " + amount + ". Your current attack is " + robot.getAttack() + ".", "Bonus taken");
            }
        } else if (!map.isEmpty(robot.getFrontX(), robot.getFrontY())) { //if not empty
            outputPrinter.println("There is an obstacle in front of your robot preventing it from moving forward.");
            unknownCommand = true;
        } else { //if empty
            robot.moveForward(1);
            robot.changeAP(-1);
        } //end move command
    }

    /**
     * Attack command: informs if there is nothing to attack, in case of attempt of attacking bonus asks for confirmation, in case of hitting another robot, map is not printed.
     *
     * @param robot current robot
     */
    private void attack(Robot robot) {
        if (map.isBonus(robot.getFrontX(), robot.getFrontY())) { //if there is bonus in front of robot
            outputPrinter.println("Are you sure that you want to attack and destroy that bonus?");
            String input = inputReader.next();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                map.removeBonus(robot.getFrontX(), robot.getFrontY());
                numberOfBonusesOnTheMap--;
                robot.changeAP(-1);
            }
            return;
        }

        Robot robotInFront = robots.getRobotAt(robot.getFrontX(), robot.getFrontY());
        if (robotInFront == null) {
            outputPrinter.println("There are no robots in front of you.");
            unknownCommand = true;
            return;
        }

        robotInFront.changeHP(-robot.getAttack());
        robot.changeAP(-1);
        displayMap = false;

        if (robotInFront.isAlive()) { //check if not killed
            outputPrinter.println("Your robot hits " + robotInFront.getName() + " for " + robot.getAttack() + " HP");
            outputPrinter.println(robotInFront.getName() + "'s HP is " + robotInFront.getHP() + ". " + robot.getAP() + " AP left.");
        } else { //if killed
            outputPrinter.print(mapToStringConverter.getMapAsStringWithHighlighted(robot));

            outputPrinter.print("Your robot kills " + robotInFront.getName() + ". ");
            popUp.show("Your robot kills " + robotInFront.getName() + "! ", "One less!");

            if (robots.getAliveRobots().size() == 1) {
                outputPrinter.print("\n");
                popUp.show(robots.getAliveRobots().get(0).getName().toUpperCase() + " IS A WINNER!", "VICTORY");
                programTerminator.exit();
            }

            outputPrinter.println(robot.getAP() + " AP left.");
        }
    }

    /**
     * Prints a map with robots represented as different digits and shows JOptionPane with stats of all robots
     */
    private void scan() {
        outputPrinter.print(mapToStringConverter.getMapAsStringWithRobotsIds());

        String result = "";
        for (Robot robot : robots) {

            String line = robot.getId() + " - ";
            if (robot.isAlive()) {
                line += robot.getName() + ": ";
                line += robot.getPositionAsString() + ", ";
                line += robot.getHP() + " HP, ";
            } else {
                line += robot.getName() + ": DEAD ";
            }
            line += robot.getEndurance() + " endurance, ";
            line += robot.getSpeed() + " speed, ";
            line += robot.getAttack() + " attack.";

            result += line + "\n";
        }

        popUp.show(result, "Players' informations");
    }

    /**
     * Returns help text with stats of given Robot
     *
     * @param robot this Robot's skills will be shown, should be the name of the current player robot
     * @return help text with stats of given Robot
     */
    private String printHelp(Robot robot) {
        return robot.getName() + "'s statistics:\n" +
                robot.getEndurance() + " endurance - denifes your maximum health points. Each point added to endurance increases your max HP by " + ENDURANCE_MULTIPLIER + " points.\n" +
                robot.getSpeed() + " speed - defines your maximum action points in turn. Each point added to speed increases your max AP by 1 point. AP are automatically restored every round.\n" +
                robot.getAttack() + " attack - defines how many HP you remove attacked enemy, Each point added to attack increases removing value by 1 point.\n" +
                robot.getHP() + " HP - if you lose them all, you will be throw out from further game. You cannot have more HP than your endurance allows you to have.\n" +
                robot.getAP() + " AP - defines how many moves/actions can you perform in each turn. It is possible to have more AP than your speed allows you to have by collecting bonuses.\n" +
                "\n" +
                "Commands understood by robots:\n" +
                "skip - confirmation required, allows you to stay in your current position by setting your current AP at 0. AP cannot be cumulated whilst using this command.\n" +
                "move (m) - moves forward facing direction, cannot move onto other robots or map edges.\n" +
                "left (l) - turns robot left.\n" +
                "right (r) - turns robot right.\n" +
                "attack/hit (a/h) - attacks robot standing in front of you. Asks for confirmation if bonus want to be attacked.\n" +
                "scan (s) - does not require AP to perform. Shows you positions and statistics of all players.\n" +
                "\n" +
                "Bonuses are randomly appearing items on the map, which can be collected by robots.\n" +
                "They can increase your statistics, add action points or heal you.\n" +
                "Bonuses can be destroyed with one attack.\n" +
                "\n" +
                "Symbols on the map:\n" +
                "edges of the map: " + MapToStringConverter.WALL_CORNER + " " + MapToStringConverter.WALL_HORIZONTAL + " " + MapToStringConverter.WALL_VERTICAL + "\n" +
                "empty field: " + MapToStringConverter.EMPTY_FIELD_SYMBOL + "\n" +
                "your robot: " + MapToStringConverter.ACTIVE_ROBOT_SYMBOL + "\n" +
                "random bonus: " + MapToStringConverter.BONUS_SYMBOL + "\n" +
                "other player's robot: " + MapToStringConverter.ROBOT_SYMBOL;
    }

    public void start() {
        getNumberOfPlayers();
        getMapSize();
        createPlayers();
        mapToStringConverter = new MapToStringConverter(map, robots);
        allocateSkillPoints();
        placeRobotsOnTheMap();

        int round = 0;
        while (true) {
            round++;
            outputPrinter.println("=========\nRound " + round + "\n=========");
            popUp.show("Round " + round, "Round");

            for (int i = 0; i < numberOfPlayers; i++) { //player number
                if (robots.get(i).getHP() > 0) { //if player alive
                    robots.get(i).changeAP(robots.get(i).getSpeed()); //restore AP depending on Robot's speed
                    unknownCommand = false;
                    displayMap = true;

                    popUp.show(robots.get(i).getName() + "'s turn.", "Turn");

                    if (i == 0 && round == 1) {
                        randomizeBonus(100);
                    } else {
                        randomizeBonus(CHANCE);
                    }

                    do {

                        if (!unknownCommand && displayMap) { //don't print map and information about position and AP left when unknown or unreachable command
                            outputPrinter.print(mapToStringConverter.getMapAsStringWithHighlighted(robots.get(i)));

                            outputPrinter.println(robots.get(i).getName() + "'s turn. " + robots.get(i).getHP() + " HP");
                            outputPrinter.println("Current position: " + robots.get(i).getPositionAsString());
                            outputPrinter.println(robots.get(i).getAP() + " AP left.");
                        } //end map and info printer

                        displayMap = true;
                        unknownCommand = false;
                        String input = inputReader.next();

                        if (robots.get(i).unknownCommand(input)) { //COMMANDS
                            outputPrinter.println("Unknown command. Your robot understand only following commands: " + robots.get(i).knownCommands());
                            unknownCommand = true;
                        } else if (input.toLowerCase().equals("skip")) {
                            outputPrinter.println("Are you sure that you want to skip your turn?");
                            input = inputReader.next();
                            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                                robots.get(i).changeAP(-robots.get(i).getAP());
                            }
                        } else if (input.toLowerCase().equals("move") || input.toLowerCase().equals("m")) { //start move command
                            move(robots.get(i));
                        } else if (input.toLowerCase().equals("left") || input.toLowerCase().equals("l")) {
                            robots.get(i).turnLeft();
                            robots.get(i).changeAP(-1);
                        } else if (input.toLowerCase().equals("right") || input.toLowerCase().equals("r")) {
                            robots.get(i).turnRight();
                            robots.get(i).changeAP(-1);
                        } else if (input.toLowerCase().equals("hit") || input.toLowerCase().equals("h") || input.toLowerCase().equals("attack") || input.toLowerCase().equals("a")) {
                            attack(robots.get(i));
                        } else if (input.toLowerCase().equals("scan") || input.toLowerCase().equals("s")) {
                            scan();
                        } else if (input.toLowerCase().equals("help") || input.equals("?")) {
                            popUp.show(printHelp(robots.get(i)), "Help");
                        } //END COMMANDS

                    } while (unknownCommand || robots.get(i).getAP() > 0);
                    String antiCheat = inputReader.nextLine(); //suppose that player one has 5 AP, write "m m m m m m" and press enter - six move command would be automatically treat as next player move

                } //end if player alive
            } //end player number
        }
    }
}