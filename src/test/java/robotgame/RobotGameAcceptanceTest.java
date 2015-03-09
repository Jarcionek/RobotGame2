package robotgame;

import org.junit.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotGameAcceptanceTest {

    private final InputReader inputReader = mock(InputReader.class);
    private final OutputPrinter outputPrinter = mock(OutputPrinter.class);
    private final PopUp popUp = mock(PopUp.class);
    private final RandomNumberGenerator weightedListRandomNumberGenerator = new RandomNumberGenerator();
    private final RandomNumberGenerator bonusRandomNumberGenerator = new RandomNumberGenerator();
    private final RandomNumberGenerator playerOrderRandomNumberGenerator = new RandomNumberGenerator();
    private final ProgramTerminator programTerminator = mock(ProgramTerminator.class);

    private final RobotGame robotGame = new RobotGame(inputReader, outputPrinter, popUp, weightedListRandomNumberGenerator, bonusRandomNumberGenerator, playerOrderRandomNumberGenerator, programTerminator);

    @Test
    public void canPlayVerySimpleGame() {
        // given
        doThrow(new TestPassed()).when(programTerminator).exit();

        when(inputReader.next())
                .thenReturn("2") // number of players
                .thenReturn("10") // width
                .thenReturn("10") // height
                // players' names
                .thenReturn("One")
                .thenReturn("Two")
                // attributes of first player
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                // attributes of second player
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                .thenReturn("a")
                // starting position of first player
                .thenReturn("1")
                .thenReturn("1")
                .thenReturn("north")
                // starting position of second player
                .thenReturn("1")
                .thenReturn("2")
                .thenReturn("south")
                // first round, first player
                .thenReturn("a")
                .thenThrow(new AssertionError("no more interactions expected"));

        // when
        try {
            robotGame.start();
        } catch (TestPassed ignored) {}

        // then
        //TODO Jarek: record mock interactions and assert on them
    }

}
