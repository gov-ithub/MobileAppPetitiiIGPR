package ro.politiaromana.petitie.mobile.android.utils;


import android.support.annotation.NonNull;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Pattern;

public final class Validator {

    private Validator() {}

    private static final Pattern EMAIL_ADDRESS_PATTERN
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isValidEmailAddress(@NonNull final String emailAddress) {
        return EMAIL_ADDRESS_PATTERN.matcher(emailAddress).matches();
    }

    public static boolean isValidCNP(@NonNull final String cnp) {
        // TODO: implement this
        return true;
    }

    public static boolean isValidROPhoneNumber(@NonNull final String phoneNumber) {
        try {
            final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
            final Phonenumber.PhoneNumber number = util.parse(phoneNumber, "RO");

            return util.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
