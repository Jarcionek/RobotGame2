package robotgame.game;

import java.util.ArrayList;
import java.util.List;

public class WeightedList<E> {

    private final RandomNumberGenerator randomNumberGenerator;

    private final List<E> elements = new ArrayList<>();
    private final List<Integer> weights = new ArrayList<>();

    public WeightedList(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void add(int weight, E element) {
        elements.add(element);
        weights.add(weight);
    }

    public E getRandom() {
        int randomValue = randomValueBetweenZeroAndTotalWeight();

        int totalWeight = 0;
        for (int i = 0; i < weights.size(); i++) {
            totalWeight += weights.get(i);
            if (randomValue < totalWeight) {
                return elements.get(i);
            }
        }

        throw new IllegalStateException("should never happen");
    }

    private int randomValueBetweenZeroAndTotalWeight() {
        return randomNumberGenerator.nextInt(weights.stream().mapToInt(e -> e).sum());
    }

}
