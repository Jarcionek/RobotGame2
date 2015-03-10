package robotgame;

public class DigitsChecker {

    private String value;
    private int min;
    private int max;
    private boolean containsNonDigits;
    private boolean outOfRange;
    private int result;

    private void check() {
        containsNonDigits = value.chars().map(c -> (char) c).filter(c -> c < '0' || c > '9').count() != 0;

        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            // number too large to be parsed to int or contains non digit characters
            outOfRange = true;
        }

        if (result < min || result > max) {
            outOfRange = true;
        }
    }

    public void load(String input, int min, int max) {
        this.value = input;
        this.min = min;
        this.max = max;
        this.containsNonDigits = false;
        this.outOfRange = false;

        check();
    }

    public int getInteger() {
        if (containsNonDigits || outOfRange) {
            throw new IllegalStateException();
        }
        return result;
    }

    public boolean containsNonDigits() {
        return containsNonDigits;
    }

    public boolean isOutOfRange() {
        return outOfRange;
    }

}