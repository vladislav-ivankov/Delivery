package by.ivankov.delivery.validate;

public class Validator {

    private static final String PHONE_NUMBER_REGEX = "^[0-9]{9}$";
    private static final String AGE_REGEX = "^(0|[1-9]\\d{0,2})$";

    public boolean phoneNumberValidate(String stringNum) {
        boolean match = stringNum.matches(PHONE_NUMBER_REGEX);
        return match;
    }

    public boolean ageValidate(String stringNum) {
        boolean match = stringNum.matches(AGE_REGEX);
        return match;
    }
}
