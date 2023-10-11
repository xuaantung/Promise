package comp3350.group6.promise.util;

import java.util.regex.Pattern;

public class RegexUtil {

    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    public static final String REGEX_USERNAME = "^[\\w]{6,20}(?<!_)$";
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    public static boolean isMatch(String regex, String input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    public static boolean isEmail(String input) {
        return isMatch(REGEX_EMAIL, input);
    }

    public static boolean isURL(String input) {
        return isMatch(REGEX_URL, input);
    }

    public static boolean isLegalName(String input) {
        return isMatch(REGEX_USERNAME, input);
    }

    public static boolean isLegalDate(String input) {
        return isMatch(REGEX_DATE, input);
    }

    public static boolean isPositiveInteger(String input) {
        return isMatch(REGEX_POSITIVE_INTEGER, input);
    }

    public static boolean isInteger(String input) {
        return isMatch(REGEX_INTEGER, input);
    }

    public static boolean isNonNegativeInteger(String input) {
        return isMatch(REGEX_NOT_NEGATIVE_INTEGER, input);
    }

    public static boolean isPositiveFloat(String input) {
        return isMatch(REGEX_POSITIVE_FLOAT, input);
    }

}
