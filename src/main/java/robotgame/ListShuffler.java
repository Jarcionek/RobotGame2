package robotgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListShuffler {

    public <E> List<E> shuffle(List<E> list) {
        List<E> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        return copy;
    }

}
