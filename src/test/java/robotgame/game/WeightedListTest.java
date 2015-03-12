package robotgame.game;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeightedListTest {

    private final RandomNumberGenerator random = mock(RandomNumberGenerator.class);

    private final WeightedList<String> weightedList = new WeightedList<>(random);

    @Test
    public void returnsRandomElementWhenTotalWeightIsOneHundred() {
        when(random.nextInt(100)).thenReturn(5, 99);

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
        String firstRandomElement = weightedList.getRandom();
        String secondRandomElement = weightedList.getRandom();

        assertThat(firstRandomElement, is(equalTo("02AP")));
        assertThat(secondRandomElement, is(equalTo("05attack")));
    }

    @Test
    public void returnsRandomElementWhenTotalWeightBelowOneHundred() {
        when(random.nextInt(14)).thenReturn(3);

        weightedList.add(14, "a");
        String randomElement = weightedList.getRandom();

        assertThat(randomElement, is(equalTo("a")));
    }

    @Test
    public void returnsRandomElementWhenTotalWeightAboveOneHundred() {
        when(random.nextInt(150)).thenReturn(140);

        weightedList.add(120, "a");
        weightedList.add(30, "b");
        String randomElement = weightedList.getRandom();

        assertThat(randomElement, is(equalTo("b")));
    }

    @Test
    public void returnsRandomElementsWhenWeightIsOne() {
        when(random.nextInt(2)).thenReturn(0, 1);

        weightedList.add(1, "a");
        weightedList.add(1, "b");
        String firstRandomElement = weightedList.getRandom();
        String secondRandomElement = weightedList.getRandom();

        assertThat(firstRandomElement, is(equalTo("a")));
        assertThat(secondRandomElement, is(equalTo("b")));
    }

}
