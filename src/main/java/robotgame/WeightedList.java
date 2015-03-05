package robotgame;

import java.util.Random;

public class WeightedList {

    private final Random random;
    private final String array[];

    public WeightedList(int numberOfElements, Random random) {
        this.random = random;
        array = new String[numberOfElements];
    }

    public void add(int weight, String element) {
        ArrayElementsCounter arrayElementsCounter = new ArrayElementsCounter();
        arrayElementsCounter.load(array);
        int i = 0;
        if (weight <= 100 - arrayElementsCounter.getNumberOfNonNulls()) {
            i = weight;
        }
        for (int k = 0; i > 0; k++) {
            if (array[k] == null) {
                array[k] = element;
                i--;
            }
        }
    }

    public String getRandom() {
        return array[random.nextInt(array.length)];
    }

}
