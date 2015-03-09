package robotgame;

import java.util.Random;

public class VerboseRandomNumberGenerator extends RandomNumberGenerator {

    private final String name;
    private final Random random = new Random();

    public VerboseRandomNumberGenerator(String name) {
        this.name = name;
    }

    @Override
    public int nextInt(int bound) {
        int value = random.nextInt(bound);
        System.out.println(String.format("%s.nextInt(%s); // -> %s", name, bound, value));
        return value;
    }

}
