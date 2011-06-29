/**
 * This class takes array of strings and counts how many its elements differ from null. USED IN RANDOM BOX!!!
 * @author Jarcionek
 */
public class ArrayElementsCounter {
    private String array[];

    /**
     * Loads a given array of strings into memory of object of ArrayElementsCounter class.
     * @param arrayName name of string array to load
     */
    public void load(String[] array) {
        this.array = array;
    }

    /**
     * Counts how many of elements of given array differ from null and returns value.
     * @return how many elements differ from null
     */
    public int CountElements() {
        int notNull=0;

        for (int i=0; i<array.length; i++) {
            if (array[i] != null) {
                notNull++;
            }
        }

        return notNull;
    }
}
