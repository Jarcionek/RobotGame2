package robotgame;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Random;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotGameAcceptanceTest {

    @Before
    public void injectDependencies() {
        RobotGame.scanner = mock(InputReader.class);
        RobotGame.outputStream = mock(PrintStream.class);
        RobotGame.popUp = mock(PopUp.class);
        RobotGame.weightedListRandom = new Random();
        RobotGame.bonusRandom = new Random();
        RobotGame.playerOrderRandom =  new Random();
        RobotGame.programTerminator = mock(ProgramTerminator.class);
    }

    @Test
    public void canPlayVerySimpleGame() {
        // given
        doThrow(new TestPassed()).when(RobotGame.programTerminator).exit();

        when(RobotGame.scanner.next())
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
            RobotGame.main(null);
        } catch (TestPassed ignored) {}

        // then
        //TODO Jarek: record mock interactions and assert on them
    }

}
