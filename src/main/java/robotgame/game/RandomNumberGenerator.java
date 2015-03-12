package robotgame.game;

import java.util.Random;

public class RandomNumberGenerator {

    private static final Random RANDOM = new Random();

    public int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

}
