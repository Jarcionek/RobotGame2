package robotgame;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class RobotGameAcceptanceTest {

    private final VerboseFakeInputReader inputReader = spy(new VerboseFakeInputReader("inputReader"));
    private final OutputPrinter outputPrinter = spy(new VerboseOutputPrinter("outputPrinter"));
    private final PopUp popUp = spy(new VerbosePopUp("popUp"));
    private final VerboseFakeRandomNumberGenerator weightedListRandomNumberGenerator = spy(new VerboseFakeRandomNumberGenerator("weightedListRandomNumberGenerator"));
    private final VerboseFakeRandomNumberGenerator bonusRandomNumberGenerator = spy(new VerboseFakeRandomNumberGenerator("bonusRandomNumberGenerator"));
    private final VerboseFakeRandomNumberGenerator playerOrderRandomNumberGenerator = spy(new VerboseFakeRandomNumberGenerator("playerOrderRandomNumberGenerator"));
    private final ProgramTerminator programTerminator = spy(new VerboseProgramTerminator("programTerminator"));

    private final RobotGame robotGame = new RobotGame(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

    @Test
    public void playsSimpleGame() {
        // given
        inputReader
                .willReturn("2") // number of players
                .willReturn("10") // width
                .willReturn("10") // height
                // players' names
                .willReturn("One")
                .willReturn("Two")
                // attributes of first player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()
                // attributes of second player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()
                // starting position of first player
                .willReturn("1")
                .willReturn("1")
                .willReturn("north")
                .willReturn("") // nextLine()
                // starting position of second player
                .willReturn("1")
                .willReturn("2")
                .willReturn("south")
                .willReturn("") // nextLine()
                // first round, first player
                .willReturn("a");

        playerOrderRandomNumberGenerator
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0);

        bonusRandomNumberGenerator
                .willReturn(76)
                .willReturn(9)
                .willReturn(3);

        // when
        try {
            robotGame.start();
        } catch (TestPassed ignored) {}

        // then
        InOrder inOrder = inOrder(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter 1. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "One"
        inOrder.verify(outputPrinter).print("Enter 2. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Two"
        inOrder.verify(playerOrderRandomNumberGenerator, times(8)).nextInt(2); // -> 0, 1, 0, 0, 0, 1, 0, 0
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate One's skill points.");
        inOrder.verify(popUp).show("Allocate One's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Two's skill points.");
        inOrder.verify(popUp).show("Allocate Two's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter One's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter One's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter One's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "north"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Two's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Two's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).print("Enter Two's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "south"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 1" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 1", "Round");
        inOrder.verify(popUp).show("One's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 76
        inOrder.verify(bonusRandomNumberGenerator, times(2)).nextInt(10); // -> 9, 3
        inOrder.verify(popUp).show("New bonus has appeared at (10;4)!", "New bonus!");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.........$|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|O.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces north");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.........$|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills Two. ");
        inOrder.verify(popUp).show("Your robot kills Two! ", "One less!");
        inOrder.verify(outputPrinter).print("" + "\n" +
                "");
        inOrder.verify(popUp).show("ONE IS A WINNER!", "VICTORY");
        inOrder.verify(programTerminator).exit();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void playsGameWithMultiplePlayersAndMultipleRounds() {
        // given
        inputReader
                .willReturn("4") // number of players
                .willReturn("10") // width
                .willReturn("10") // height

                        // players' names
                .willReturn("One")
                .willReturn("Two")
                .willReturn("Three")
                .willReturn("Four")

                        // attributes of first player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // attributes of second player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // attributes of third player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // attributes of fourth player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // starting position of first player
                .willReturn("1")
                .willReturn("1")
                .willReturn("north")
                .willReturn("") // nextLine()

                        // starting position of second player
                .willReturn("1")
                .willReturn("10")
                .willReturn("east")
                .willReturn("") // nextLine()

                        // starting position of third player
                .willReturn("10")
                .willReturn("10")
                .willReturn("south")
                .willReturn("") // nextLine()

                        // starting position of fourth player
                .willReturn("10")
                .willReturn("1")
                .willReturn("west")
                .willReturn("") // nextLine()

                        // first round, first player
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // first round, second player
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // first round, third player
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // first round, fourth player
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // second round, first player
                .willReturn("r")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // second round, second player
                .willReturn("r")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // second round, third player
                .willReturn("r")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // second round, fourth player
                .willReturn("r")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("m")
                .willReturn("") // nextLine()

                        // third round, first player
                .willReturn("a") // kill second player
                .willReturn("m")
                .willReturn("r")
                .willReturn("a") // kill third player
                .willReturn("m")
                .willReturn("") // nextLine()

                        // third round, fourth player
                .willReturn("r")
                .willReturn("a"); // kill first player

        playerOrderRandomNumberGenerator
                .willReturn(1)
                .willReturn(0)
                .willReturn(1)
                .willReturn(1)
                .willReturn(3)
                .willReturn(3)
                .willReturn(1)
                .willReturn(3)
                .willReturn(2)
                .willReturn(1)
                .willReturn(3)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(1)
                .willReturn(2)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(2)
                .willReturn(1)
                .willReturn(1)
                .willReturn(2)
                .willReturn(3)
                .willReturn(2)
                .willReturn(0)
                .willReturn(3)
                .willReturn(0)
                .willReturn(3);
        
        bonusRandomNumberGenerator
                .willReturn(99)
                .willReturn(2)
                .willReturn(2)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99)
                .willReturn(99);

        // when
        try {
            robotGame.start();
        } catch (TestPassed ignored) {}

        // then
        InOrder inOrder = inOrder(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "4"
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter 1. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "One"
        inOrder.verify(outputPrinter).print("Enter 2. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Two"
        inOrder.verify(outputPrinter).print("Enter 3. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Three"
        inOrder.verify(outputPrinter).print("Enter 4. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Four"
        inOrder.verify(playerOrderRandomNumberGenerator, times(32)).nextInt(4);
                // -> 1
                // -> 0
                // -> 1
                // -> 1
                // -> 3
                // -> 3
                // -> 1
                // -> 3
                // -> 2
                // -> 1
                // -> 3
                // -> 1
                // -> 0
                // -> 0
                // -> 0
                // -> 1
                // -> 2
                // -> 1
                // -> 0
                // -> 0
                // -> 0
                // -> 0
                // -> 2
                // -> 1
                // -> 1
                // -> 2
                // -> 3
                // -> 2
                // -> 0
                // -> 3
                // -> 0
                // -> 3
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate One's skill points.");
        inOrder.verify(popUp).show("Allocate One's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Four's skill points.");
        inOrder.verify(popUp).show("Allocate Four's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Three's skill points.");
        inOrder.verify(popUp).show("Allocate Three's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Two's skill points.");
        inOrder.verify(popUp).show("Allocate Two's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter One's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter One's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter One's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "north"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Four's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Four's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter Four's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "east"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Three's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter Three's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter Three's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "south"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Two's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter Two's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Two's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "west"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 1" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 1", "Round");
        inOrder.verify(popUp).show("One's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(bonusRandomNumberGenerator, times(2)).nextInt(10); // -> 2, 2
        inOrder.verify(popUp).show("New bonus has appeared at (3;3)!", "New bonus!");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|O........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces north");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|O.........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;2) faces north");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;3) faces north");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;4) faces north");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|#........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;5) faces north");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Four's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|O........#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;10) faces east");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.O.......#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (2;10) faces east");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..O......#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (3;10) faces east");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|...O.....#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (4;10) faces east");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|....O....#|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;10) faces east");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Three's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#...O|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;10) faces south");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|.........O|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;9) faces south");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|.........O|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;8) faces south");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.........O|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;7) faces south");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#........O|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........#|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;6) faces south");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Two's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.........O|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;1) faces west");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|........O.|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (9;1) faces west");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.......O..|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (8;1) faces west");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|......O...|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (7;1) faces west");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;1) faces west");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 2" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 2", "Round");
        inOrder.verify(popUp).show("One's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;6) faces north");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;6) faces east");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.O........|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (2;6) faces east");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..O.......|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (3;6) faces east");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|...O......|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (4;6) faces east");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Four's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....O....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;10) faces east");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|.....O....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;10) faces south");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;9) faces south");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;8) faces south");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|....#.....|" + "\n" +
                "|.........#|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Four's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;7) faces south");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Three's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.........O|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;5) faces south");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.........O|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (10;5) faces west");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|........O.|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (9;5) faces west");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.......O..|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (8;5) faces west");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|......O...|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....#.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Three's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (7;5) faces west");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Two's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....O.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;1) faces west");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|....O.....|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;1) faces north");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|....O.....|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;2) faces north");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.....#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.O.....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;3) faces north");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....##....|" + "\n" +
                "|.....#....|" + "\n" +
                "|....O.....|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;4) faces north");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 3" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 3", "Round");
        inOrder.verify(popUp).show("One's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....O#....|" + "\n" +
                "|....##....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;6) faces east");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....O.....|" + "\n" +
                "|....##....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills Four. ");
        inOrder.verify(popUp).show("Your robot kills Four! ", "One less!");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|....##....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;6) faces east");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|....##....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("One's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (6;6) faces south");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....O....|" + "\n" +
                "|....#.....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills Three. ");
        inOrder.verify(popUp).show("Your robot kills Three! ", "One less!");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Two's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 99
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....O#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;5) faces north");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....O#....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Two's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (5;5) faces east");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|....O.....|" + "\n" +
                "|..........|" + "\n" +
                "|..$.......|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills One. ");
        inOrder.verify(popUp).show("Your robot kills One! ", "One less!");
        inOrder.verify(outputPrinter).print("" + "\n" +
                "");
        inOrder.verify(popUp).show("TWO IS A WINNER!", "VICTORY");
        inOrder.verify(programTerminator).exit();
    }

    @Test
    public void playsGameUsingAllCommands() {
        // given
        inputReader
                .willReturn("2") // number of players
                .willReturn("10") // width
                .willReturn("10") // height

                        // players' names
                .willReturn("First Robot")
                .willReturn("Second Robot")

                        // attributes of first player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // attributes of second player
                .willReturn("a")
                .willReturn("a")
                .willReturn("s")
                .willReturn("s")
                .willReturn("s")
                .willReturn("") // nextLine()

                        // starting position of first player
                .willReturn("1")
                .willReturn("1")
                .willReturn("north")
                .willReturn("") // nextLine()

                        // starting position of second player
                .willReturn("1")
                .willReturn("3")
                .willReturn("south")
                .willReturn("") // nextLine()

                        // first round, first player
                .willReturn("r")
                .willReturn("attack") // attack bonus
                .willReturn("yes")
                .willReturn("l")
                .willReturn("m")
                .willReturn("help")
                .willReturn("scan")
                .willReturn("skip")
                .willReturn("yes")
                .willReturn("") // nextLine()

                        // first round, second player
                .willReturn("h")
                .willReturn("hit");

        playerOrderRandomNumberGenerator
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0);

        bonusRandomNumberGenerator
                .willReturn(95)
                .willReturn(1)
                .willReturn(0)
                .willReturn(95);

        // when
        try {
            robotGame.start();
        } catch (TestPassed ignored) {}

        // then
        InOrder inOrder = inOrder(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter 1. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "First Robot"
        inOrder.verify(outputPrinter).print("Enter 2. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Second Robot"
        inOrder.verify(playerOrderRandomNumberGenerator, times(8)).nextInt(2); // -> 0, 1, 0, 0, 0, 1, 0, 0
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate First Robot's skill points.");
        inOrder.verify(popUp).show("Allocate First Robot's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Second Robot's skill points.");
        inOrder.verify(popUp).show("Allocate Second Robot's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "s"
        inOrder.verify(outputPrinter).println("Your speed is 6 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "s"
        inOrder.verify(outputPrinter).println("Your speed is 7 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "s"
        inOrder.verify(outputPrinter).println("Your speed is 8 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter First Robot's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter First Robot's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter First Robot's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "north"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Second Robot's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Second Robot's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "3"
        inOrder.verify(outputPrinter).print("Enter Second Robot's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "south"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 1" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 1", "Round");
        inOrder.verify(popUp).show("First Robot's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 95
        inOrder.verify(bonusRandomNumberGenerator, times(2)).nextInt(10); // -> 1, 0
        inOrder.verify(popUp).show("New bonus has appeared at (2;1)!", "New bonus!");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|O$........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces north");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "r"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|O$........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces east");
        inOrder.verify(outputPrinter).println("4 AP left.");
        inOrder.verify(inputReader).next(); // -> "attack"
        inOrder.verify(outputPrinter).println("Are you sure that you want to attack and destroy that bonus?");
        inOrder.verify(inputReader).next(); // -> "yes"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces east");
        inOrder.verify(outputPrinter).println("3 AP left.");
        inOrder.verify(inputReader).next(); // -> "l"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces north");
        inOrder.verify(outputPrinter).println("2 AP left.");
        inOrder.verify(inputReader).next(); // -> "m"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;2) faces north");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "help"
        inOrder.verify(popUp).show("First Robot's statistics:" + "\n" +
                "3 endurance - denifes your maximum health points. Each point added to endurance increases your max HP by 2 points." + "\n" +
                "5 speed - defines your maximum action points in turn. Each point added to speed increases your max AP by 1 point. AP are automatically restored every round." + "\n" +
                "6 attack - defines how many HP you remove attacked enemy, Each point added to attack increases removing value by 1 point." + "\n" +
                "6 HP - if you lose them all, you will be throw out from further game. You cannot have more HP than your endurance allows you to have." + "\n" +
                "1 AP - defines how many moves/actions can you perform in each turn. It is possible to have more AP than your speed allows you to have by collecting bonuses." + "\n" +
                "" + "\n" +
                "Commands understood by robots:" + "\n" +
                "skip - confirmation required, allows you to stay in your current position by setting your current AP at 0. AP cannot be cumulated whilst using this command." + "\n" +
                "move (m) - moves forward facing direction, cannot move onto other robots or map edges." + "\n" +
                "left (l) - turns robot left." + "\n" +
                "right (r) - turns robot right." + "\n" +
                "attack/hit (a/h) - attacks robot standing in front of you. Asks for confirmation if bonus want to be attacked." + "\n" +
                "scan (s) - does not require AP to perform. Shows you positions and statistics of all players." + "\n" +
                "" + "\n" +
                "Bonuses are randomly appearing items on the map, which can be collected by robots." + "\n" +
                "They can increase your statistics, add action points or heal you." + "\n" +
                "Bonuses can be destroyed with one attack." + "\n" +
                "" + "\n" +
                "Symbols on the map:" + "\n" +
                "edges of the map: + - |" + "\n" +
                "empty field: ." + "\n" +
                "your robot: O" + "\n" +
                "random bonus: $" + "\n" +
                "other player's robot: #", "Help");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;2) faces north");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "scan"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|1.........|" + "\n" +
                "|0.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(popUp).show("0 - First Robot: (1;2) faces north, 6 HP, 3 endurance, 5 speed, 6 attack." + "\n" +
                "1 - Second Robot: (1;3) faces south, 6 HP, 3 endurance, 8 speed, 3 attack." + "\n" +
                "", "Players' informations");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("First Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;2) faces north");
        inOrder.verify(outputPrinter).println("1 AP left.");
        inOrder.verify(inputReader).next(); // -> "skip"
        inOrder.verify(outputPrinter).println("Are you sure that you want to skip your turn?");
        inOrder.verify(inputReader).next(); // -> "yes"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Second Robot's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 95
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|#.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Second Robot's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;3) faces south");
        inOrder.verify(outputPrinter).println("8 AP left.");
        inOrder.verify(inputReader).next(); // -> "h"
        inOrder.verify(outputPrinter).println("Your robot hits First Robot for 3 HP");
        inOrder.verify(outputPrinter).println("First Robot's HP is 3. 7 AP left.");
        inOrder.verify(inputReader).next(); // -> "hit"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills First Robot. ");
        inOrder.verify(popUp).show("Your robot kills First Robot! ", "One less!");
        inOrder.verify(outputPrinter).print("" + "\n" +
                "");
        inOrder.verify(popUp).show("SECOND ROBOT IS A WINNER!", "VICTORY");
        inOrder.verify(programTerminator).exit();
    }

    @Test
    public void playsGameWithInvalidCommands() {
        // given
        inputReader
                        // number of players
                .willReturn("gdfg")
                .willReturn("10000")
                .willReturn("2")

                        // width
                .willReturn("dsgsg")
                .willReturn("123456")
                .willReturn("2")
                .willReturn("10")

                        // height
                .willReturn("hdhdh")
                .willReturn("999999")
                .willReturn("3")
                .willReturn("10")

                        // players' names
                .willReturn("Robot Name")
                .willReturn("Robot Name") // invalid
                .willReturn("Another Robot Name")

                        // attributes of first player
                .willReturn("invalidSkill")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("") // nextLine()

                        // attributes of second player
                .willReturn("a")
                .willReturn("a")
                .willReturn("a")
                .willReturn("s")
                .willReturn("s")
                .willReturn("") // nextLine()

                        // starting position of first player
                        // X
                .willReturn("abc")
                .willReturn("456")
                .willReturn("1")
                        // Y
                .willReturn("abc")
                .willReturn("99")
                .willReturn("1")
                        // direction
                .willReturn("nonExistingDirection")
                .willReturn("east")
                .willReturn("") // nextLine()

                        // starting position of second player
                        // already used position
                .willReturn("1")
                .willReturn("1")
                        // correct position
                .willReturn("1")
                .willReturn("2")
                .willReturn("south")
                .willReturn("") // nextLine()

                        // first round, first player
                .willReturn("a") // nothing in front
                .willReturn("skip")
                .willReturn("yes")
                .willReturn("") // nextLine()

                        // first round, second player
                .willReturn("unknownCommand")
                .willReturn("a")
                .willReturn("a");

        playerOrderRandomNumberGenerator
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0)
                .willReturn(0)
                .willReturn(1)
                .willReturn(0)
                .willReturn(0);

        bonusRandomNumberGenerator
                .willReturn(95)
                .willReturn(5)
                .willReturn(5)
                .willReturn(95);

        // when
        try {
            robotGame.start();
        } catch (TestPassed ignored) {}

        // then
        InOrder inOrder = inOrder(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "gdfg"
        inOrder.verify(outputPrinter).println("Incorrect value. Only digits are allowed.");
        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "10000"
        inOrder.verify(outputPrinter).println("At least one player is required. Maximum 10 players.");
        inOrder.verify(outputPrinter).print("Enter the number of players: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "dsgsg"
        inOrder.verify(outputPrinter).println("Incorrect value. Only digits are allowed.");
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "123456"
        inOrder.verify(outputPrinter).println("Map's width cannot be lower than 10 or bigger than 75.");
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).println("Map's width cannot be lower than 10 or bigger than 75.");
        inOrder.verify(outputPrinter).print("Enter map's width: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "hdhdh"
        inOrder.verify(outputPrinter).println("Incorrect value. Only digits are allowed.");
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "999999"
        inOrder.verify(outputPrinter).println("Map's height cannot be lower than 10 or bigger than 25.");
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "3"
        inOrder.verify(outputPrinter).println("Map's height cannot be lower than 10 or bigger than 25.");
        inOrder.verify(outputPrinter).print("Enter map's height: ");
        inOrder.verify(inputReader).next(); // -> "10"
        inOrder.verify(outputPrinter).print("Enter 1. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Robot Name"
        inOrder.verify(outputPrinter).print("Enter 2. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Robot Name"
        inOrder.verify(outputPrinter).println("Another player has already chosen that name.");
        inOrder.verify(outputPrinter).print("Enter 2. robot's name: ");
        inOrder.verify(inputReader).next(); // -> "Another Robot Name"
        inOrder.verify(playerOrderRandomNumberGenerator, times(8)).nextInt(2); // -> 0, 1, 0, 0, 0, 1, 0, 0
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Robot Name's skill points.");
        inOrder.verify(popUp).show("Allocate Robot Name's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "invalidSkill"
        inOrder.verify(outputPrinter).println("Incorrect skill.");
        inOrder.verify(outputPrinter).println("You can allocate skill points to the following skills: endurance, e, speed, s, attack, a");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 5 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 6 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).println("Allocate Another Robot Name's skill points.");
        inOrder.verify(popUp).show("Allocate Another Robot Name's skill points.", "Allocate skill points");
        inOrder.verify(outputPrinter).println("By default, each robot has 3 endurance, 5 speed and 1 attack.");
        inOrder.verify(outputPrinter).println("Each point added to endurance increases your HP by 2 points.");
        inOrder.verify(outputPrinter).println("Each point added to speed increases your AP by 1 point.");
        inOrder.verify(outputPrinter).println("Each point added to attack increases dealing damages by 1 point.");
        inOrder.verify(outputPrinter).println("Which skill you would like to add point to?");
        inOrder.verify(outputPrinter).println("TIP: instead of writing entire skill name," + "\n" +
                "you can just write its first letter.");
        inOrder.verify(outputPrinter).println("You have 5 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 2 now.");
        inOrder.verify(outputPrinter).println("You have 4 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 3 now.");
        inOrder.verify(outputPrinter).println("You have 3 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your attack is 4 now.");
        inOrder.verify(outputPrinter).println("You have 2 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "s"
        inOrder.verify(outputPrinter).println("Your speed is 6 now.");
        inOrder.verify(outputPrinter).println("You have 1 skill points to allocate.");
        inOrder.verify(inputReader).next(); // -> "s"
        inOrder.verify(outputPrinter).println("Your speed is 7 now.");
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("===================================");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Robot Name's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "abc"
        inOrder.verify(outputPrinter).println("Incorrect value. Only digits are allowed.");
        inOrder.verify(outputPrinter).print("Enter Robot Name's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "456"
        inOrder.verify(outputPrinter).println("Out of map.");
        inOrder.verify(outputPrinter).print("Enter Robot Name's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Robot Name's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "abc"
        inOrder.verify(outputPrinter).println("Incorrect value. Only digits are allowed.");
        inOrder.verify(outputPrinter).print("Enter Robot Name's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "99"
        inOrder.verify(outputPrinter).println("Out of map.");
        inOrder.verify(outputPrinter).print("Enter Robot Name's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Robot Name's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "nonExistingDirection"
        inOrder.verify(outputPrinter).println("Incorrect value. Your robot can only face north, east, south or west.");
        inOrder.verify(outputPrinter).print("Enter Robot Name's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "east"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Another Robot Name's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Another Robot Name's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).println("There is already another robot.");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Enter Another Robot Name's X coordinate: ");
        inOrder.verify(inputReader).next(); // -> "1"
        inOrder.verify(outputPrinter).print("Enter Another Robot Name's Y coordinate: ");
        inOrder.verify(inputReader).next(); // -> "2"
        inOrder.verify(outputPrinter).print("Enter Another Robot Name's facing direction: ");
        inOrder.verify(inputReader).next(); // -> "south"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(outputPrinter).println("=========" + "\n" +
                "Round 1" + "\n" +
                "=========");
        inOrder.verify(popUp).show("Round 1", "Round");
        inOrder.verify(popUp).show("Robot Name's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 95
        inOrder.verify(bonusRandomNumberGenerator, times(2)).nextInt(10); // -> 5, 5
        inOrder.verify(popUp).show("New bonus has appeared at (6;6)!", "New bonus!");
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....$....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|#.........|" + "\n" +
                "|O.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Robot Name's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;1) faces east");
        inOrder.verify(outputPrinter).println("5 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("There are no robots in front of you.");
        inOrder.verify(inputReader).next(); // -> "skip"
        inOrder.verify(outputPrinter).println("Are you sure that you want to skip your turn?");
        inOrder.verify(inputReader).next(); // -> "yes"
        inOrder.verify(inputReader).nextLine(); // -> ""
        inOrder.verify(popUp).show("Another Robot Name's turn.", "Turn");
        inOrder.verify(bonusRandomNumberGenerator).nextInt(100); // -> 95
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....$....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|#.........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).println("Another Robot Name's turn. 6 HP");
        inOrder.verify(outputPrinter).println("Current position: (1;2) faces south");
        inOrder.verify(outputPrinter).println("7 AP left.");
        inOrder.verify(inputReader).next(); // -> "unknownCommand"
        inOrder.verify(outputPrinter).println("Unknown command. Your robot understand only following commands: skip, move, m, left, l, right, r, hit, h, attack, a, scan, s, help, ?");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).println("Your robot hits Robot Name for 4 HP");
        inOrder.verify(outputPrinter).println("Robot Name's HP is 2. 6 AP left.");
        inOrder.verify(inputReader).next(); // -> "a"
        inOrder.verify(outputPrinter).print("+----------+" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|.....$....|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|..........|" + "\n" +
                "|O.........|" + "\n" +
                "|..........|" + "\n" +
                "+----------+" + "\n" +
                "");
        inOrder.verify(outputPrinter).print("Your robot kills Robot Name. ");
        inOrder.verify(popUp).show("Your robot kills Robot Name! ", "One less!");
        inOrder.verify(outputPrinter).print("" + "\n" +
                "");
        inOrder.verify(popUp).show("ANOTHER ROBOT NAME IS A WINNER!", "VICTORY");
        inOrder.verify(programTerminator).exit();
    }

}
