package robotgame;

import org.junit.Test;

import java.util.Random;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomBoxTest {

    private final Random random = mock(Random.class);

    @Test
    public void returnsRandomElement() {
        RandomBox randomBox = new RandomBox(100, random);

        randomBox.addString(15, "02AP");
        randomBox.addString(8, "03AP");
        randomBox.addString(5, "05AP");
        randomBox.addString(1, "10AP");
        randomBox.addString(15, "05HP");
        randomBox.addString(8, "10HP");
        randomBox.addString(5, "15HP");
        randomBox.addString(3, "20HP");
        randomBox.addString(1, "80HP");
        randomBox.addString(5, "01endurance");
        randomBox.addString(4, "02endurance");
        randomBox.addString(3, "03endurance");
        randomBox.addString(1, "05endurance");
        randomBox.addString(5, "01speed");
        randomBox.addString(4, "02speed");
        randomBox.addString(3, "03speed");
        randomBox.addString(1, "05speed");
        randomBox.addString(5, "01attack");
        randomBox.addString(4, "02attack");
        randomBox.addString(3, "03attack");
        randomBox.addString(1, "05attack");

        when(random.nextInt(100)).thenReturn(5);
        assertThat(randomBox.randomize(), is(equalTo("02AP")));

        when(random.nextInt(100)).thenReturn(99);
        assertThat(randomBox.randomize(), is(equalTo("05attack")));
    }

}
