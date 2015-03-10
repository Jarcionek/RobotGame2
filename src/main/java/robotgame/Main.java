package robotgame;

import robotgame.io.InputReader;
import robotgame.io.OutputPrinter;
import robotgame.io.PopUp;
import robotgame.io.ProgramTerminator;

public class Main {

    private static final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    public static void main(String[] args) {
        new RobotGame(
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
