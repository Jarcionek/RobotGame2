package robotgame;

import org.junit.Test;

import java.util.Random;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeightedListTest {

    private final Random random = mock(Random.class);

    @Test
    public void returnsRandomElement() {
        WeightedList weightedList = new WeightedList(100, random);

        weightedList.add(15, "02AP");
        weightedList.add(8, "03AP");
        weightedList.add(5, "05AP");
        weightedList.add(1, "10AP");
        weightedList.add(15, "05HP");
        weightedList.add(8, "10HP");
        weightedList.add(5, "15HP");
        weightedList.add(3, "20HP");
        weightedList.add(1, "80HP");
        weightedList.add(5, "01endurance");
        weightedList.add(4, "02endurance");
        weightedList.add(3, "03endurance");
        weightedList.add(1, "05endurance");
        weightedList.add(5, "01speed");
        weightedList.add(4, "02speed");
        weightedList.add(3, "03speed");
        weightedList.add(1, "05speed");
        weightedList.add(5, "01attack");
        weightedList.add(4, "02attack");
        weightedList.add(3, "03attack");
        weightedList.add(1, "05attack");

        when(random.nextInt(100)).thenReturn(5);
        assertThat(weightedList.getRandom(), is(equalTo("02AP")));

        when(random.nextInt(100)).thenReturn(99);
        assertThat(weightedList.getRandom(), is(equalTo("05attack")));
    }

}
