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

    public static boolean isValidCNP(@NonNull final String cnpString) {
        int[] cnp = new int[13];
        int[] control = {2,7,9,1,4,6,3,5,8,2,7,9};
        if (cnpString.length() != 13) {
            return false;
        }
        char[] cnpArray = cnpString.toCharArray();
        for (int i = 0; i < 13; i++) {
            cnp[i] = Character.getNumericValue(cnpArray[i]);
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnp[i] * control[i]);
        }
        int remainder = sum % 11 == 10 ? 1 : sum % 11;
        return remainder == cnp[12];
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
