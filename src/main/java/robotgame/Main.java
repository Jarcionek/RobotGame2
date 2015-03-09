package robotgame;

public class Main {

    private static final InputReader inputReader = new InputReader();
    private static final OutputPrinter outputPrinter = new OutputPrinter();
    private static final PopUp popUp = new PopUp();
    private static final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
    private static final ProgramTerminator programTerminator = new ProgramTerminator();

    public static void main(String[] args) {
        new RobotGame(
                inputReader,
                outputPrinter,
                popUp,
                randomNumberGenerator,
                randomNumberGenerator,
                randomNumberGenerator,
                programTerminator
        ).start();
    }

}
