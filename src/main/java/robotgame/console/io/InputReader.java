package robotgame.console.io;

import java.util.Scanner;

public class InputReader {

    private static final Scanner SCANNER = new Scanner(System.in);

    public String next() {
        return SCANNER.next();
    }

    public String nextLine() {
        return SCANNER.nextLine();
    }

}
