package robotgame;

import java.util.Random;

public class RandomBox {

    private final Random random;
    private final String array[];

    public RandomBox(int numberOfElements, Random random) {
        this.random = random;
        array = new String[numberOfElements];
    }

    /**
     * Loads a string into RandomBox and assigning given probability of choosing it.
     * Remember that real chance of choosing given string is equal given chance devided by number of elements given whilst constructing an object.
     * If given chance is bigger than possible it will be lower. E.g. RandomBox(120) creates an array with 120 cells,
     * addString(80, "stringOne") fills 80 of cells with value "stringOne", real probability of choosing it is 80/120=66,(6)%,
     * if then addString(50, "stringTwo") is used, "stringTwo" will not be put into array 50 times, but only 40 times, because there is not
     * enough space in the created array.
     *
     * @param chance how many times you want to put a string into array, real chance = chance / number of elements
     * @param name   any string
     */
    public void addString(int chance, String name) {
        ArrayElementsCounter arrayElementsCounter = new ArrayElementsCounter();
        arrayElementsCounter.load(array);
        int i = 0;
        if (chance > 100 - arrayElementsCounter.getNumberOfNonNulls()) {
            i = 100 - arrayElementsCounter.getNumberOfNonNulls();
        } else {
            i = chance;
        }
        for (int k = 0; i > 0; k++) {
            if (array[k] == null) {
                array[k] = name;
                i--;
            }
        }
    }

    /**
     * Returns randomly chosen string from an array. If array is not filled completely, null value can be returned.
     *
     * @return randomly chosen string from set of given strings
     */
    public String randomize() {
        return array[random.nextInt(array.length)];
    }
}
