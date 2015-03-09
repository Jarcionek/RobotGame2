package robotgame;

import static robotgame.RoboMap.WALL_HORIZONTAL;
import static robotgame.RoboMap.WALL_VERTICAL;

public class RobotGame {

    private final InputReader inputReader;
    private final OutputPrinter outputPrinter;
    private final PopUp popUp;
    private final RandomNumberGenerator weightedListRandomNumberGenerator;
    private final RandomNumberGenerator bonusRandomNumberGenerator;
    private final RandomNumberGenerator playerOrderRandomNumberGenerator;
    private final ProgramTerminator programTerminator;

    public RobotGame(InputReader inputReader,
                     OutputPrinter outputPrinter,
                     PopUp popUp,
                     RandomNumberGenerator weightedListRandomNumberGenerator,
                     RandomNumberGenerator bonusRandomNumberGenerator,
                     RandomNumberGenerator playerOrderRandomNumberGenerator,
                     ProgramTerminator programTerminator) {
        this.inputReader = inputReader;
        this.outputPrinter = outputPrinter;
        this.popUp = popUp;
        this.weightedListRandomNumberGenerator = weightedListRandomNumberGenerator;
        this.bonusRandomNumberGenerator = bonusRandomNumberGenerator;
        this.playerOrderRandomNumberGenerator = playerOrderRandomNumberGenerator;
        this.programTerminator = programTerminator;

        this.bonuses = new WeightedList<>(weightedListRandomNumberGenerator);
    }

    private int numberOfPlayers = 0;
    private DigitsChecker checkInput = new DigitsChecker();
    private static final int MAP_MAX_WIDTH = 75;
    private static final int MAP_MAX_HEIGHT = 25;
    private int mapWidth = 0;
    private int mapHeight = 0;
    private RoboMap map;
    private RobotClass Robot[];
    private int order[];
    private boolean unknownCommand = false; //used also as unreachable command
    private boolean displayMap = true;

    //BONUSES
    private final WeightedList<String> bonuses;
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

            if (checkInput.containNoDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.outOfRange()) {
                outputPrinter.println("At least one player is required. Maximum 10 players.");
            } else {
                numberOfPlayers = Integer.parseInt(input);
            }

        } while (checkInput.containNoDigits() || checkInput.outOfRange());
    }

    private void getMapSize() {
        do {
            outputPrinter.print("Enter map's width: ");
            String input = inputReader.next();
            checkInput.load(input, 10, MAP_MAX_WIDTH);

            if (checkInput.containNoDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.outOfRange()) {
                outputPrinter.println("Map's width cannot be lower than 10 or bigger than " + MAP_MAX_WIDTH + ".");
            } else {
                mapWidth = Integer.parseInt(input);
            }

        } while (checkInput.containNoDigits() || checkInput.outOfRange());


        do {
            outputPrinter.print("Enter map's height: ");
            String input = inputReader.next();
            checkInput.load(input, 10, MAP_MAX_HEIGHT);

            if (checkInput.containNoDigits()) {
                outputPrinter.println("Incorrect value. Only digits are allowed.");
            } else if (checkInput.outOfRange()) {
                outputPrinter.println("Map's height cannot be lower than 10 or bigger than " + MAP_MAX_HEIGHT + ".");
            } else {
                mapHeight = Integer.parseInt(input);
            }

        } while (checkInput.containNoDigits() || checkInput.outOfRange());

        map = new RoboMap(mapWidth, mapHeight);
    }

    private void getRobotsNames() {
        Robot = new RobotClass[numberOfPlayers + 1]; //Robot[0] is not being used, first player is Robot[1], second player is Robot[2] and so on

        for (int i = 1; i <= numberOfPlayers; i++) {
            String name;
            boolean repeatedName = false;

            do {
                outputPrinter.print("Enter " + i + ". robot's name: ");
                name = inputReader.next();
                repeatedName = false;

                for (int j = 1; j < i; j++) {
                    if (name.toLowerCase().equals(Robot[j].getName().toLowerCase())) {
                        repeatedName = true;
                        outputPrinter.println("Another player has already chosen that name.");
                        break;
                    }
                }

            } while (repeatedName);

            Robot[i] = new RobotClass();
            Robot[i].changeName(name);
        }
    }

    private void randomizePlayersOrder() {
        order = new int[numberOfPlayers + 1]; //as with RobotClass objects, first cell is not in use

        for (int i = 1; i <= numberOfPlayers; i++) {
            order[i] = i;
        }

        for (int i = 0; i < numberOfPlayers * numberOfPlayers; i++) {
            int x;
            int choiceOne = playerOrderRandomNumberGenerator.nextInt(numberOfPlayers) + 1;
            int choiceTwo = playerOrderRandomNumberGenerator.nextInt(numberOfPlayers) + 1;
            x = order[choiceOne];
            order[choiceOne] = order[choiceTwo];
            order[choiceTwo] = x;
        }
    }

    private void allocateSkillPoints() {
        for (int i = 1; i <= numberOfPlayers; i++) {
            outputPrinter.println("===================================");
            outputPrinter.println("Allocate " + Robot[order[i]].getName() + "'s skill points.");
            popUp.show("Allocate " + Robot[order[i]].getName() + "'s skill points.", "Allocate skill points");
            outputPrinter.println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
            String possibilities[] = {"endurance", "e", "speed", "s", "attack", "a"};

            String result = "";
            for (int j = 0; j < possibilities.length; j++) {
                result += possibilities[j] + ", ";
            }
            result = result.substring(0, result.length() - 2);

            outputPrinter.println("Each point added to endurance increases your HP by " + RobotClass.MULTIPLIER + " points.");
            outputPrinter.println("Each point added to speed increases your AP by 1 point.");
            outputPrinter.println("Each point added to attack increases dealing damages by 1 point.");
            outputPrinter.println("Which skill you would like to add point to?");
            outputPrinter.println("TIP: instead of writing entire skill name,\nyou can just write its first letter.");
            do {
                boolean incorrect_input = true;
                do {
                    outputPrinter.println("You have " + Robot[order[i]].getSkillPoints() + " skill points to allocate.");
                    String input = inputReader.next();

                    for (int j = 0; j < possibilities.length; j++) {
                        if (input.toLowerCase().equals(possibilities[j])) {
                            incorrect_input = false;
                        }
                    }

                    if (incorrect_input) {
                        outputPrinter.println("Incorrect skill.");
                        outputPrinter.println("You can allocate skill points to the following skills: " + result);
                    } else {
                        Robot[order[i]].changeSkillPoints(-1);
                        if (input.toLowerCase().equals("endurance") || input.toLowerCase().equals("e")) {
                            Robot[order[i]].changeEndurance(1);
                            outputPrinter.println("Your endurance is " + Robot[order[i]].getEndurance() + " now.");
                        } else if (input.toLowerCase().equals("speed") || input.toLowerCase().equals("s")) {
                            Robot[order[i]].changeSpeed(1);
                            outputPrinter.println("Your speed is " + Robot[order[i]].getSpeed() + " now.");
                        } else if (input.toLowerCase().equals("attack") || input.toLowerCase().equals("a")) {
                            Robot[order[i]].changeAttack(1);
                            outputPrinter.println("Your attack is " + Robot[order[i]].getAttack() + " now.");
                        }
                    }

                } while (incorrect_input);
            } while (Robot[order[i]].getSkillPoints() > 0);
            String antiCheat = inputReader.nextLine(); //suppose that player one has 5 free skill points, write "e e e e e e" and press enter - six "e" would cause automatically adding one point of next player to his endurance
            Robot[order[i]].changeHP(RobotClass.MULTIPLIER * Robot[order[i]].getEndurance());
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
            } while (map.getBox(x, y) != RoboMap.EMPTY_FIELD_SYMBOL);
            map.changeBox(x, y, RoboMap.BONUS_SYMBOL);
            numberOfBonusesOnTheMap++;
            popUp.show("New bonus has appeared at (" + x + ";" + y + ")!", "New bonus!");
        }
    }

    private void placeRobotsOnTheMap() {
        for (int i = 1; i <= numberOfPlayers; i++) {
            int X = 0;
            int Y = 0;
            int direction = 0;
            boolean incorrect_direction = false;
            boolean alreadyUsed = false;

            do { //get X and Y
                alreadyUsed = false; //postion already used by another player

                outputPrinter.print(map.asString());

                do {
                    outputPrinter.print("Enter " + Robot[order[i]].getName() + "'s X coordinate: ");
                    String input = inputReader.next();
                    checkInput.load(input, 1, mapWidth);

                    if (checkInput.containNoDigits()) {
                        outputPrinter.println("Incorrect value. Only digits are allowed.");
                    } else if (checkInput.outOfRange()) {
                        outputPrinter.println("Out of map.");
                    } else {
                        X = Integer.parseInt(input);
                    }

                } while (checkInput.containNoDigits() || checkInput.outOfRange());

                do {
                    outputPrinter.print("Enter " + Robot[order[i]].getName() + "'s Y coordinate: ");
                    String input = inputReader.next();
                    checkInput.load(input, 1, mapHeight);

                    if (checkInput.containNoDigits()) {
                        outputPrinter.println("Incorrect value. Only digits are allowed.");
                    } else if (checkInput.outOfRange()) {
                        outputPrinter.println("Out of map.");
                    } else {
                        Y = Integer.parseInt(input);
                    }

                } while (checkInput.containNoDigits() || checkInput.outOfRange());

                Robot[order[i]].place(X, Y, 1);

                for (int find = 1; find < i; find++) { //if there is another robot with that position loaded
                    if (Robot[order[i]].getX() == Robot[order[find]].getX() && Robot[order[i]].getY() == Robot[order[find]].getY()) {
                        outputPrinter.println("There is already another robot.");
                        alreadyUsed = true;
                        break;
                    }
                } //end if there is another robot with that position loaded

            } while (alreadyUsed); //end get X and Y

            do { //get direction
                outputPrinter.print("Enter " + Robot[order[i]].getName() + "'s facing direction: ");
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
            Robot[order[i]].place(X, Y, direction); //despite robot was created after taking X and Y, it was done without direction (assigned north)
            map.loadRobot(Robot[order[i]], RoboMap.ROBOT_SYMBOL); //load robot to the map
        }
    }

    private void declareBonuses() {
        bonuses.add(15, "02AP");
        bonuses.add(8, "03AP");
        bonuses.add(5, "05AP");
        bonuses.add(1, "10AP");
        bonuses.add(15, "05HP");
        bonuses.add(8, "10HP");
        bonuses.add(5, "15HP");
        bonuses.add(3, "20HP");
        bonuses.add(1, "80HP");
        bonuses.add(5, "01endurance");
        bonuses.add(4, "02endurance");
        bonuses.add(3, "03endurance");
        bonuses.add(1, "05endurance");
        bonuses.add(5, "01speed");
        bonuses.add(4, "02speed");
        bonuses.add(3, "03speed");
        bonuses.add(1, "05speed");
        bonuses.add(5, "01attack");
        bonuses.add(4, "02attack");
        bonuses.add(3, "03attack");
        bonuses.add(1, "05attack");
    }

    //COMMANDS

    /**
     * move command, in case of attempt of moving into another robot or wall, the information is printed; bonus are taken automatically.
     *
     * @param name name of the current player's robot
     */
    private void move(RobotClass name) {
        if (map.getBoxInFrontOf(name) == RoboMap.BONUS_SYMBOL) { //if bonus
            map.loadRobot(name, RoboMap.EMPTY_FIELD_SYMBOL);
            name.moveForward(1);
            name.changeAP(-1);
            map.loadRobot(name, RoboMap.ROBOT_SYMBOL);
            numberOfBonusesOnTheMap--;

            String chosenBonus = "";
            chosenBonus = bonuses.getRandom();
            int amount = Integer.parseInt(chosenBonus.substring(0, 2));
            String skill = chosenBonus.substring(2);

            if (skill.equals("AP")) { //what kind of bonus is it
                name.changeAP(amount);
                outputPrinter.println(amount + " action points added. " + name.getAP() + " AP left.");
                popUp.show(amount + " action points added. " + name.getAP() + " AP left.", "Bonus taken");
            } else if (skill.equals("HP")) {
                name.changeHP(amount);
                if (name.getHP() == RobotClass.MULTIPLIER * name.getEndurance()) {
                    outputPrinter.println("You have been completely healed.");
                    popUp.show("You have been completely healed.", "Bonus taken");
                } else {
                    outputPrinter.println("You have been healed for " + amount + " points. Your current HP is " + name.getHP() + ".");
                    popUp.show("You have been healed for " + amount + " points. Your current HP is " + name.getHP() + ".", "Bonus taken");
                }
            } else if (skill.equals("endurance")) {
                name.changeEndurance(amount);
                outputPrinter.println("Your endurance has been increased by " + amount + ". Your current endurance is " + name.getEndurance() + ".");
                popUp.show("Your endurance has been increased by " + amount + ". Your current endurance is " + name.getEndurance() + ".", "Bonus taken");
            } else if (skill.equals("speed")) {
                name.changeSpeed(amount);
                outputPrinter.println("Your speed has been increased by " + amount + ". Your current speed is " + name.getSpeed() + ".");
                popUp.show("Your speed has been increased by " + amount + ". Your current speed is " + name.getSpeed() + ".", "Bonus taken");
            } else if (skill.equals("attack")) {
                name.changeAttack(amount);
                outputPrinter.println("Your atack has been increased by " + amount + ". Your current attack is " + name.getAttack() + ".");
                popUp.show("Your atack has been increased by " + amount + ". Your current attack is " + name.getAttack() + ".", "Bonus taken");
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
     * @param name name of the current player's robot
     */
    private void attack(RobotClass name) {
        if (map.getBoxInFrontOf(name) == RoboMap.EMPTY_FIELD_SYMBOL || //if there is nothing to hit
                map.getBoxInFrontOf(name) == WALL_VERTICAL ||
                map.getBoxInFrontOf(name) == WALL_HORIZONTAL) { //if there is nothing to hit - condition end
            outputPrinter.println("There are no robots in front of you.");
            unknownCommand = true;
        } else if (map.getBoxInFrontOf(name) == RoboMap.ROBOT_SYMBOL) { //if there is another robot in front of current one
            for (int find = 1; find <= numberOfPlayers; find++) { //find which robot is standing in front of current one
                if (name.getFrontX() == Robot[find].getX() && name.getFrontY() == Robot[find].getY()) {
                    Robot[find].changeHP(-name.getAttack());
                    name.changeAP(-1);
                    displayMap = false;

                    if (Robot[find].getHP() > 0) { //check if not killed
                        outputPrinter.println("Your robot hits " + Robot[find].getName() + " for " + name.getAttack() + " HP");
                        outputPrinter.println(Robot[find].getName() + "'s HP is " + Robot[find].getHP() + ". " + name.getAP() + " AP left.");
                    } else { //if killed
                        map.loadRobot(Robot[find], RoboMap.EMPTY_FIELD_SYMBOL); //change character in the map array
                        Robot[find].place(0, 0, 1); //remove from map

                        map.loadRobot(name, RoboMap.ACTIVE_ROBOT_SYMBOL);
                        outputPrinter.print(map.asString());
                        map.loadRobot(name, RoboMap.ROBOT_SYMBOL);

                        outputPrinter.print("Your robot kills " + Robot[find].getName() + ". ");
                        popUp.show("Your robot kills " + Robot[find].getName() + "! ", "One less!");

                        int playersAlive = 0;
                        for (int k = 1; k <= numberOfPlayers; k++) { //check how many players are still in game
                            if (Robot[k].getHP() > 0) {
                                playersAlive++;
                            }
                        } //end check how many players are still in game

                        if (playersAlive == 1) { //if there is only one player remaining
                            for (int k = 1; k <= numberOfPlayers; k++) { //find that player's number
                                if (Robot[k].getHP() > 0) {
                                    outputPrinter.print("\n");
                                    popUp.show(Robot[k].getName().toUpperCase() + " IS A WINNER!", "VICTORY");
                                    programTerminator.exit(); //finish game
                                }
                            } //end find that player's number
                        } //end if there is only one player remaining

                        outputPrinter.println(name.getAP() + " AP left.");

                    } //end check if not killed
                    break; //if found, there is no need of looking further for
                }
            } //end find which robot is standing in front of current one
        } else { //if there is bonus in front of robot
            outputPrinter.println("Are you sure that you want to attack and destroy that bonus?");
            String input = inputReader.next();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                map.changeBox(name.getFrontX(), name.getFrontY(), RoboMap.EMPTY_FIELD_SYMBOL);
                numberOfBonusesOnTheMap--;
                name.changeAP(-1);
            }
        }
    }

    /**
     * Prints a map with robots represented as different digits, throw JOptionPane with stats of all robots
     *
     * @param name name of the current player robot
     */
    private void scan(RobotClass name) {
        for (int no = 1; no <= numberOfPlayers; no++) { //represents robots as digits
            map.loadRobot(Robot[order[no]], (char) no + 47); //0's code is 48
        }

        outputPrinter.print(map.asString()); //prints map with robots represented as digits

        for (int no = 1; no <= numberOfPlayers; no++) { //representes all robots as inactives robots
            map.loadRobot(Robot[no], RoboMap.ROBOT_SYMBOL);
        }

        map.loadRobot(name, RoboMap.ACTIVE_ROBOT_SYMBOL); //represents current player's robot as active robot

        PlayersInfo Information = new PlayersInfo(numberOfPlayers);
        for (int player = 1; player <= numberOfPlayers; player++) {
            Information.load(player, Robot[order[player]]);
        }
        popUp.show(Information.print(), "Players' informations");
    }

    /**
     * Returns help text with stats of given Robot
     *
     * @param name this Robot's skills will be shown, should be the name of the current player robot
     * @return help text with stats of given Robot
     */
    private String printHelp(RobotClass name) {
        return name.getName() + "'s statistics:\n" +
                name.getEndurance() + " endurance - denifes your maximum health points. Each point added to endurance increases your max HP by " + RobotClass.MULTIPLIER + " points.\n" +
                name.getSpeed() + " speed - defines your maximum action points in turn. Each point added to speed increases your max AP by 1 point. AP are automatically restored every round.\n" +
                name.getAttack() + " attack - defines how many HP you remove attacked enemy, Each point added to attack increases removing value by 1 point.\n" +
                name.getHP() + " HP - if you lose them all, you will be throw out from further game. You cannot have more HP than your endurance allows you to have.\n" +
                name.getAP() + " AP - defines how many moves/actions can you perform in each turn. It is possible to have more AP than your speed allows you to have by collecting bonuses.\n" +
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
        getRobotsNames();
        randomizePlayersOrder();
        allocateSkillPoints();
        placeRobotsOnTheMap();
        declareBonuses();

        for (int i = 1, round = 1; true; round++) {
            outputPrinter.println("=========\nRound " + round + "\n=========");
            popUp.show("Round " + round, "Round");

            for (; i <= numberOfPlayers; i++) { //player number
                if (Robot[order[i]].getHP() > 0) { //if player alive
                    Robot[order[i]].changeAP(Robot[order[i]].getSpeed()); //restore AP depepnding on Robot's speed
                    unknownCommand = false;
                    displayMap = true;

                    popUp.show(Robot[order[i]].getName() + "'s turn.", "Turn");

                    if (i == 1 && round == 1) {
                        randomizeBonus(100);
                    } else {
                        randomizeBonus(CHANCE);
                    }

                    do {

                        if (!unknownCommand && displayMap) { //don't print map and information about position and AP left when unknown or unreachable command
                            map.loadRobot(Robot[order[i]], RoboMap.ACTIVE_ROBOT_SYMBOL);

                            outputPrinter.print(map.asString());

                            map.loadRobot(Robot[order[i]], RoboMap.ROBOT_SYMBOL);

                            outputPrinter.println(Robot[order[i]].getName() + "'s turn. " + Robot[order[i]].getHP() + " HP");
                            outputPrinter.println("Current position: " + Robot[order[i]].sendPosition());
                            outputPrinter.println(Robot[order[i]].getAP() + " AP left.");
                        } //end map and info printer

                        displayMap = true;
                        unknownCommand = false;
                        String input = inputReader.next();

                        if (Robot[order[i]].unknownCommand(input)) { //COMMANDS
                            outputPrinter.println("Unknown command. Your robot understand only following commands: " + Robot[order[i]].knownCommands());
                            unknownCommand = true;
                        } else if (input.toLowerCase().equals("skip")) {
                            outputPrinter.println("Are you sure that you want to skip your turn?");
                            input = inputReader.next();
                            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                                Robot[order[i]].changeAP(-Robot[order[i]].getAP());
                            }
                        } else if (input.toLowerCase().equals("move") || input.toLowerCase().equals("m")) { //start move command
                            move(Robot[order[i]]);
                        } else if (input.toLowerCase().equals("left") || input.toLowerCase().equals("l")) {
                            Robot[order[i]].turnLeft();
                            Robot[order[i]].changeAP(-1);
                        } else if (input.toLowerCase().equals("right") || input.toLowerCase().equals("r")) {
                            Robot[order[i]].turnRight();
                            Robot[order[i]].changeAP(-1);
                        } else if (input.toLowerCase().equals("hit") || input.toLowerCase().equals("h") || input.toLowerCase().equals("attack") || input.toLowerCase().equals("a")) {
                            attack(Robot[order[i]]);
                        } else if (input.toLowerCase().equals("scan") || input.toLowerCase().equals("s")) {
                            scan(Robot[order[i]]);
                        } else if (input.toLowerCase().equals("help") || input.equals("?")) {
                            popUp.show(printHelp(Robot[order[i]]), "Help");
                        } //END COMMANDS

                    } while (unknownCommand || Robot[order[i]].getAP() > 0);
                    String antiCheat = inputReader.nextLine(); //suppose that player one has 5 AP, write "m m m m m m" and press enter - six move command would be automatically treat as next player move

                } //end if player alive
            } //end player number

            i = 1;
        }
    }
}