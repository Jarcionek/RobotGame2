package robotgame;

public class Main {

    private static final InputReader scanner = new InputReader();
    private static final OutputPrinter outputStream = new OutputPrinter();
    private static final PopUp popUp = new PopUp();
    private static final RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
    private static final ProgramTerminator programTerminator = new ProgramTerminator();

    public static void main(String[] args) {
        new RobotGame(
                scanner,
                outputStream,
                popUp,
                randomNumberGenerator,
                randomNumberGenerator,
                randomNumberGenerator,
                programTerminator
        ).start();
    }

}
