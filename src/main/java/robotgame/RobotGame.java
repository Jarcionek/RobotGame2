package robotgame;

import com.google.common.collect.ImmutableList;
import robotgame.io.InputReader;
import robotgame.io.OutputPrinter;
import robotgame.io.PopUp;
import robotgame.io.ProgramTerminator;

import java.util.ArrayList;
import java.util.List;

import static robotgame.Attribute.AP;
import static robotgame.Attribute.ATTACK;
import static robotgame.Attribute.ENDURANCE;
import static robotgame.Attribute.HP;
import static robotgame.Attribute.SPEED;
import static robotgame.RoboMap.WALL_HORIZONTAL;
import static robotgame.RoboMap.WALL_VERTICAL;
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
    private List<Robot> robots;
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

                for (String existingName : names) {
                    if (name.equalsIgnoreCase(existingName)) {
                        repeatedName = true;
                        outputPrinter.println("Another player has already chosen that name.");
                        break;
                    }
                }

            } while (repeatedName);

            names.add(name);
        }

        List<String> shuffledNames = listShuffler.shuffle(names);

        ImmutableList.Builder<Robot> builder = ImmutableList.builder();
        for (int i = 0; i < shuffledNames.size(); i++) {
            builder.add(new Robot(i, shuffledNames.get(i)));
        }
        robots = builder.build();
    }

    private void allocateSkillPoints() {
        for (int i = 0; i < numberOfPlayers; i++) {
            outputPrinter.println("===================================");
            outputPrinter.println("Allocate " + robots.get(i).getName() + "'s skill points.");
            popUp.show("Allocate " + robots.get(i).getName() + "'s skill points.", "Allocate skill points");
            outputPrinter.println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
            String possibilities[] = {"endurance", "e", "speed", "s", "attack", "a"};

            String result = "";
            for (String possibility : possibilities) {
                result += possibility + ", ";
            }
            result = result.substring(0, result.length() - 2);

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

                    for (String possibility : possibilities) {
                        if (input.toLowerCase().equals(possibility)) {
                            incorrect_input = false;
                        }
                    }

                    if (incorrect_input) {
                        outputPrinter.println("Incorrect skill.");
                        outputPrinter.println("You can allocate skill points to the following skills: " + result);
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
            int X = 0;
            int Y = 0;
            int direction = 0;
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
                        X = checkInput.getInteger();
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
                        Y = checkInput.getInteger();
                    }

                } while (checkInput.containsNonDigits() || checkInput.isOutOfRange());

                robots.get(i).place(X, Y, 1);

                for (int find = 0; find < i; find++) { //if there is another robot with that position loaded
                    if (robots.get(i).getX() == robots.get(find).getX() && robots.get(i).getY() == robots.get(find).getY()) {
                        outputPrinter.println("There is already another robot.");
                        alreadyUsed = true;
                        break;
                    }
                } //end if there is another robot with that position loaded

            } while (alreadyUsed); //end get X and Y

            do { //get direction
                outputPrinter.print("Enter " + robots.get(i).getName() + "'s facing direction: ");
                String input = inputReader.next().toLowerCase();
                incorrect_direction = false;

                if (input.equals("north") || input.equals("east") || input.equals("south") || input.equals("west") ||
                        input.equals("up") || input.equals("right") || input.equals("down") || input.equals("left") ||
                        input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {

                    if (input.equals("north") || input.equals("up") || input.equals("1")) {
                        direction = 1;
                    } else if (input.equals("east") || input.equals("right") || input.equals("2")) {
                        direction = 2;
                    } else if (input.equals("south") || input.equals("down") || input.equals("3")) {
                        direction = 3;
                    } else {
                        direction = 4;
                    }
                } else {
                    outputPrinter.println("Incorrect value. Your robot can only face north, east, south or west.");
                    incorrect_direction = true;
                }

            } while (incorrect_direction); //end get direction

            String antiCheat = inputReader.nextLine();
            robots.get(i).place(X, Y, direction); //despite robot was created after taking X and Y, it was done without direction (assigned north)
            map.loadRobot(robots.get(i), RoboMap.ROBOT_SYMBOL); //load robot to the map
        }
    }

    //COMMANDS

    /**
     * move command, in case of attempt of moving into another robot or wall, the information is printed; bonus are taken automatically.
     *
     * @param name name of the current player's robot
     */
    private void move(Robot name) {
        if (map.getBoxInFrontOf(name) == RoboMap.BONUS_SYMBOL) { //if bonus
            map.loadRobot(name, RoboMap.EMPTY_FIELD_SYMBOL);
            name.moveForward(1);
            name.changeAP(-1);
            map.loadRobot(name, RoboMap.ROBOT_SYMBOL);
            numberOfBonusesOnTheMap--;

            Bonus bonus = bonuses.getRandom();
            int amount = bonus.getModifier();
            Attribute attribute = bonus.getAttribute();

            if (attribute.equals(AP)) { //what kind of bonus is it
                name.changeAP(amount);
                outputPrinter.println(amount + " action points added. " + name.getAP() + " AP left.");
                popUp.show(amount + " action points added. " + name.getAP() + " AP left.", "Bonus taken");
            } else if (attribute.equals(HP)) {
                name.changeHP(amount);
                if (name.getHP() == ENDURANCE_MULTIPLIER * name.getEndurance()) {
                    outputPrinter.println("You have been completely healed.");
                    popUp.show("You have been completely healed.", "Bonus taken");
                } else {
                    outputPrinter.println("You have been healed for " + amount + " points. Your current HP is " + name.getHP() + ".");
                    popUp.show("You have been healed for " + amount + " points. Your current HP is " + name.getHP() + ".", "Bonus taken");
                }
            } else if (attribute.equals(ENDURANCE)) {
                name.changeEndurance(amount);
                outputPrinter.println("Your endurance has been increased by " + amount + ". Your current endurance is " + name.getEndurance() + ".");
                popUp.show("Your endurance has been increased by " + amount + ". Your current endurance is " + name.getEndurance() + ".", "Bonus taken");
            } else if (attribute.equals(SPEED)) {
                name.changeSpeed(amount);
                outputPrinter.println("Your speed has been increased by " + amount + ". Your current speed is " + name.getSpeed() + ".");
                popUp.show("Your speed has been increased by " + amount + ". Your current speed is " + name.getSpeed() + ".", "Bonus taken");
            } else if (attribute.equals(ATTACK)) {
                name.changeAttack(amount);
                outputPrinter.println("Your attack has been increased by " + amount + ". Your current attack is " + name.getAttack() + ".");
                popUp.show("Your attack has been increased by " + amount + ". Your current attack is " + name.getAttack() + ".", "Bonus taken");
            }
        } else if (map.getBoxInFrontOf(name) != RoboMap.EMPTY_FIELD_SYMBOL) { //if not empty
            outputPrinter.println("There is an obstacle in front of your robot preventing it from moving forward.");
            unknownCommand = true;
        } else { //if empty
            map.loadRobot(name, RoboMap.EMPTY_FIELD_SYMBOL);
            name.moveForward(1);
            name.changeAP(-1);
            map.loadRobot(name, RoboMap.ROBOT_SYMBOL);
        } //end move command
    }

    /**
     * Attack command: informs if there is nothing to attack, in case of attempt of attacking bonus asks for confirmation, in case of hitting another robot, map is not printed.
     *
     * @param robot name of the current player's robot
     */
    private void attack(Robot robot) {
        if (map.getBoxInFrontOf(robot) == RoboMap.EMPTY_FIELD_SYMBOL || //if there is nothing to hit
                map.getBoxInFrontOf(robot) == WALL_VERTICAL ||
                map.getBoxInFrontOf(robot) == WALL_HORIZONTAL) { //if there is nothing to hit - condition end
            outputPrinter.println("There are no robots in front of you.");
            unknownCommand = true;
        } else if (map.getBoxInFrontOf(robot) == RoboMap.ROBOT_SYMBOL) { //if there is another robot in front of current one
            for (int find = 0; find < numberOfPlayers; find++) { //find which robot is standing in front of current one
                if (robot.getFrontX() == robots.get(find).getX() && robot.getFrontY() == robots.get(find).getY()) {
                    robots.get(find).changeHP(-robot.getAttack());
                    robot.changeAP(-1);
                    displayMap = false;

                    if (robots.get(find).getHP() > 0) { //check if not killed
                        outputPrinter.println("Your robot hits " + robots.get(find).getName() + " for " + robot.getAttack() + " HP");
                        outputPrinter.println(robots.get(find).getName() + "'s HP is " + robots.get(find).getHP() + ". " + robot.getAP() + " AP left.");
                    } else { //if killed
                        map.loadRobot(robots.get(find), RoboMap.EMPTY_FIELD_SYMBOL); //change character in the map array
                        robots.get(find).place(0, 0, 1); //remove from map

                        map.loadRobot(robot, RoboMap.ACTIVE_ROBOT_SYMBOL);
                        outputPrinter.print(mapToStringConverter.getMapAsString());
                        map.loadRobot(robot, RoboMap.ROBOT_SYMBOL);

                        outputPrinter.print("Your robot kills " + robots.get(find).getName() + ". ");
                        popUp.show("Your robot kills " + robots.get(find).getName() + "! ", "One less!");

                        int playersAlive = 0;
                        for (int k = 0; k < numberOfPlayers; k++) { //check how many players are still in game
                            if (robots.get(k).getHP() > 0) {
                                playersAlive++;
                            }
                        } //end check how many players are still in game

                        if (playersAlive == 1) { //if there is only one player remaining
                            for (int k = 0; k < numberOfPlayers; k++) { //find that player's number
                                if (robots.get(k).getHP() > 0) {
                                    outputPrinter.print("\n");
                                    popUp.show(robots.get(k).getName().toUpperCase() + " IS A WINNER!", "VICTORY");
                                    programTerminator.exit(); //finish game
                                }
                            } //end find that player's number
                        } //end if there is only one player remaining

                        outputPrinter.println(robot.getAP() + " AP left.");

                    } //end check if not killed
                    break; //if found, there is no need of looking further for
                }
            } //end find which robot is standing in front of current one
        } else { //if there is bonus in front of robot
            outputPrinter.println("Are you sure that you want to attack and destroy that bonus?");
            String input = inputReader.next();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                map.destroyBonus(robot.getFrontX(), robot.getFrontY());
                numberOfBonusesOnTheMap--;
                robot.changeAP(-1);
            }
        }
    }

    /**
     * Prints a map with robots represented as different digits, throw JOptionPane with stats of all robots
     */
    private void scan() {
        outputPrinter.print(mapToStringConverter.getMapAsStringWithRobotsIds());

        PlayersInfo Information = new PlayersInfo(numberOfPlayers);
        for (int player = 0; player < numberOfPlayers; player++) {
            Information.load(player, robots.get(player));
        }
        popUp.show(Information.print(), "Players' informations");
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
                "edges of the map: + - |\n" +
                "empty field: " + RoboMap.EMPTY_FIELD_SYMBOL + "\n" +
                "your robot: " + RoboMap.ACTIVE_ROBOT_SYMBOL + "\n" +
                "random bonus: " + RoboMap.BONUS_SYMBOL + "\n" +
                "other player's robot: " + RoboMap.ROBOT_SYMBOL;
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
                            map.loadRobot(robots.get(i), RoboMap.ACTIVE_ROBOT_SYMBOL);

                            outputPrinter.print(mapToStringConverter.getMapAsString());

                            map.loadRobot(robots.get(i), RoboMap.ROBOT_SYMBOL);

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