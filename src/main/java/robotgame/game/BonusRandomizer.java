package robotgame.game;

public class BonusRandomizer {

    /**
     * How many percent of all fields on the map have to be filled with bonuses to stop next bonus from appearing.
     */
    private static final int FULNESS = 10;

    private final RoboMap map;
    private final RandomNumberGenerator randomNumberGenerator;

    public BonusRandomizer(RoboMap map, RandomNumberGenerator randomNumberGenerator) {
        this.map = map;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    /**
     * @param chance 0 to 100, both inclusive
     */
    public Coordinates randomizeBonus(int chance) {
        if (FULNESS / 100.0 >= (double) map.getBonuses().size() / (map.getWidth() * map.getHeight()) && randomNumberGenerator.nextInt(100) < chance) {
            int x;
            int y;
            do {
                x = randomNumberGenerator.nextInt(map.getWidth()) + 1;
                y = randomNumberGenerator.nextInt(map.getHeight()) + 1;
            } while (!map.isEmpty(x, y));
            map.addBonus(x, y);
            return new Coordinates(x, y);
        }
        return null;
    }

}
