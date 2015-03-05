package robotgame;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ArrayElementsCounterTest {

    private final ArrayElementsCounter arrayElementsCounter = new ArrayElementsCounter();

    @Test
    public void returnsLengthOfTheArrayWhenNoNulls() {
        String[] array = {"1", "2", "3"};

        arrayElementsCounter.load(array);

        assertThat(arrayElementsCounter.getNumberOfNonNulls(), is(equalTo(3)));
    }

    @Test
    public void returnsNumberOfNullsInArrayWhenSomeNulls() {
        String[] array = {null, "2", null, "5"};

        arrayElementsCounter.load(array);

        assertThat(arrayElementsCounter.getNumberOfNonNulls(), is(equalTo(2)));
    }

    @Test
    public void returnsZeroWhenAllNulls() {
        String[] array = {null, null};

        arrayElementsCounter.load(array);

        assertThat(arrayElementsCounter.getNumberOfNonNulls(), is(equalTo(0)));
    }

}
