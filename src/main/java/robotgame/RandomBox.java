package robotgame;

import java.util.Random;

/**
 * This class helps choosing randomly a string with given probability. Firstly you have to choose how many elements you want to have in your set of strings (proposed 100). Then you load strings with probability assigned to them. What is important, real chance is equal given chance devided by number of elements.
 *
 * @author Jaroslaw Pawlak
 */
public class RandomBox {
    private String random[];

    /**
     * Constructs RandomBox with given maximum number of strings.
     *
     * @param numberOfElements maximum number of strings
     */
    public RandomBox(int numberOfElements) {
        random = new String[numberOfElements];
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
        ArrayElementsCounter Counter = new ArrayElementsCounter();
        Counter.load(random);
        int i = 0;
        if (chance > 100 - Counter.getNumberOfNonNulls()) {
            i = 100 - Counter.getNumberOfNonNulls();
        } else {
            i = chance;
        }
        for (int k = 0; i > 0; k++) {
            if (random[k] == null) {
                random[k] = name;
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
        Random choose = new Random();
        return random[choose.nextInt(random.length)];
    }
}
