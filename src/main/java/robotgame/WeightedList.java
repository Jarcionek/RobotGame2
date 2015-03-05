package robotgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedList {

    private final Random random;

    private final List<String> elements = new ArrayList<>();
    private final List<Integer> weights = new ArrayList<>();

    public WeightedList(Random random) {
        this.random = random;
    }

    public void add(int weight, String element) {
        elements.add(element);
        weights.add(weight);
    }

    public String getRandom() {
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
        return random.nextInt(weights.stream().mapToInt(e -> e).sum());
    }

}
