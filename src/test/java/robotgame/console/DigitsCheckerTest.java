package robotgame.console;

import org.junit.Test;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class DigitsCheckerTest {

    private final DigitsChecker digitsChecker = new DigitsChecker();

    @Test
    public void tellsAboutNonDigitCharacters() {
        digitsChecker.load("123abc456", 20, 80);

        assertThat(digitsChecker.containsNonDigits(), is(equalTo(true)));
        assertThat(digitsChecker.isOutOfRange(), is(equalTo(true)));

        try {
            digitsChecker.getInteger();
        } catch (IllegalStateException expected) {
            return;
        }
        fail("expected exception not thrown");
    }

    @Test
    public void tellsAboutValueBelowMinimum() {
        digitsChecker.load("15", 35, 70);

        assertThat(digitsChecker.containsNonDigits(), is(equalTo(false)));
        assertThat(digitsChecker.isOutOfRange(), is(equalTo(true)));

        try {
            digitsChecker.getInteger();
        } catch (IllegalStateException expected) {
            return;
        }
        fail("expected exception not thrown");
    }

    @Test
    public void tellsAboutValueAboveMaximum() {
        digitsChecker.load("99", 35, 70);

        assertThat(digitsChecker.containsNonDigits(), is(equalTo(false)));
        assertThat(digitsChecker.isOutOfRange(), is(equalTo(true)));

        try {
            digitsChecker.getInteger();
        } catch (IllegalStateException expected) {
            return;
        }
        fail("expected exception not thrown");
    }

    @Test
    public void tellsAboutValueGreaterThanIntegerMaxValue() {
        digitsChecker.load("9999999999999999999999999999", 3, 7);

        assertThat(digitsChecker.containsNonDigits(), is(equalTo(false)));
        assertThat(digitsChecker.isOutOfRange(), is(equalTo(true)));

        try {
            digitsChecker.getInteger();
        } catch (IllegalStateException expected) {
            return;
        }
        fail("expected exception not thrown");
    }

    @Test
    public void parsesValidInteger() {
        digitsChecker.load("15", 10, 20);

        assertThat(digitsChecker.containsNonDigits(), is(equalTo(false)));
        assertThat(digitsChecker.isOutOfRange(), is(equalTo(false)));
        assertThat(digitsChecker.getInteger(), is(equalTo(15)));
    }

}
