package robotgame;

import java.util.Arrays;

public class ArrayElementsCounter {

    private String array[];

    public void load(String[] array) {
        this.array = array;
    }

    public int getNumberOfNonNulls() {
        return (int) Arrays.stream(array).filter(e -> e != null).count();
    }

}
