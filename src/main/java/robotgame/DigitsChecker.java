package robotgame;

/**
 * DigitsChecker takes a string and gives information if it contains only digits or not.
 * If there are only digits in given input, converts it into integer and compares with given minimum and maximum value.
 * DigitsChecker returns also information if a value is out of range.
 * If given string can be parse into integer, but contains more than 9 digits, out of range will be true and the string will not be parsed.
 *
 * @author Jaroslaw Pawlak
 */
public class DigitsChecker {
    private String value;
    private int min;
    private int max;
    private boolean containNoDigits;
    private boolean outOfRange;
    private int result;

    private boolean moreThanTenDigits() {
        if (value.length() > 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks either the string can be parse into integer or not.
     *
     * @return true if the value cannot be parse into integer
     */
    private boolean tenDigitsButTooBig() {
        if (value.length() == 10) {

            char d[] = new char[10];

            for (int i = 0; i < 10; i++) {
                d[i] = value.charAt(i);
            }

            if (d[0] > '2') {
                return true;
            } else if (d[0] == '2' && d[1] > '1') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] > '4') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] > '7') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] > '4') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] == '4' && d[5] > '8') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] == '4' && d[5] == '8' && d[6] > '3') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] == '4' && d[5] == '8' && d[6] == '3' && d[7] > '6') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] == '4' && d[5] == '8' && d[6] == '3' && d[7] == '6' && d[8] > '4') {
                return true;
            } else if (d[0] == '2' && d[1] == '1' && d[2] == '4' && d[3] == '7' && d[4] == '4' && d[5] == '8' && d[6] == '3' && d[7] == '6' && d[8] == '4' && d[9] > '7') {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Creates empty DigitsChecker
     */
    public DigitsChecker() {
    }

    /**
     * Firstly checks if actually loaded string contains only digits.
     * Then if the number contains more than 9 digits, outOfRange gets true.
     * If the string has length not bigger than 9, it finally checks if it is in given range.
     */
    private void check() {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) < (int) '0' || value.charAt(i) > (int) '9') {
                containNoDigits = true;
                break;
            }
        }

        if (containNoDigits || moreThanTenDigits() || tenDigitsButTooBig()) {
            outOfRange = true;
        } else {
            if (Integer.parseInt(value) < min || Integer.parseInt(value) > max) {
                outOfRange = true;
            }

            if (!outOfRange && !containNoDigits) {
                result = Integer.parseInt(value);
            }
        }

    }

    /**
     * Creates DigitsChecker with loaded string to check and min and max value of integer that string can be. Checks automatically.
     *
     * @param input string to check
     * @param min   min value
     * @param max   max value
     */
    public DigitsChecker(String input, int min, int max) {
        value = input;
        this.min = min;
        this.max = max;
        containNoDigits = false;
        outOfRange = false;

        check();
    }

    /**
     * Loads empty DigitsChecker (or assign new values to filled one) with new string and minimum and maximum values. Checks automatically.
     *
     * @param input string to check
     * @param min   min value
     * @param max   max value
     */
    public void load(String input, int min, int max) {
        value = input;
        this.min = min;
        this.max = max;
        containNoDigits = false;
        outOfRange = false;

        check();
    }

    /**
     * Returns given string as an integer - only if it is not out of range, contains only digits and there is no more than 9 digits.
     *
     * @return given string as an integer
     */
    public int getInteger() {
        return result;
    }

    /**
     * Returns information if given string contains no digit characters.
     *
     * @return information if given string contains no digit characters
     */
    public boolean containNoDigits() {
        return containNoDigits;
    }

    /**
     * Returns information if given string is out of range.
     *
     * @return information if given string is out of range
     */
    public boolean outOfRange() {
        return outOfRange;
    }

}