package robotgame.console;

import robotgame.console.io.InputReader;
import robotgame.console.io.OutputPrinter;
import robotgame.console.io.PopUp;
import robotgame.console.io.ProgramTerminator;
import robotgame.game.AvailableBonuses;
import robotgame.game.ListShuffler;
import robotgame.game.RandomNumberGenerator;

public class Main {

    private static final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public static void main(String[] args) {
        new ConsoleRobotGame(
                new InputReader(),
                new OutputPrinter(),
                new PopUp(),
                new AvailableBonuses(randomNumberGenerator),
                randomNumberGenerator,
                new ProgramTerminator(),
                new ListShuffler()
        ).start();
    }

}
