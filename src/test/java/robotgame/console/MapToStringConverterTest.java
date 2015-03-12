package robotgame.console;

import org.junit.Test;
import robotgame.game.RoboMap;
import robotgame.game.Robots;

import java.util.Collections;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class MapToStringConverterTest {

    @Test
    public void convertsMapToString() {
        MapToStringConverter mapToStringConverter = new MapToStringConverter(new RoboMap(4, 3), new Robots(Collections.<String>emptyList()));

        assertThat(mapToStringConverter.getMapAsString(), is(equalTo(
                "+----+" + "\n" +
                "|....|" + "\n" +
                "|....|" + "\n" +
                "|....|" + "\n" +
                "+----+" + "\n"
        )));
    }

}
