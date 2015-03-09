package robotgame;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class RoboMapTest {

    @Test
    public void convertsMapToString() {
        RoboMap map = new RoboMap(3, 3);

        assertThat(map.asString(), is(equalTo(
                "+---+" + "\n" +
                "|...|" + "\n" +
                "|...|" + "\n" +
                "|...|" + "\n" +
                "+---+" + "\n"
        )));
    }

}
