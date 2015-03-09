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
    public void canPlayVerySimpleGame() {
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
        inOrder.verify(outputPrinter).println("+----------+" + "\n" +
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
                "+----------+");
        inOrder.verify(outputPrinter).print("Your robot kills Two. ");
        inOrder.verify(popUp).show("Your robot kills Two! ", "One less!");
        inOrder.verify(outputPrinter).print("" + "\n" +
                "");
        inOrder.verify(popUp).show("ONE IS A WINNER!", "VICTORY");
        inOrder.verify(programTerminator).exit();

        inOrder.verifyNoMoreInteractions();
    }

}
