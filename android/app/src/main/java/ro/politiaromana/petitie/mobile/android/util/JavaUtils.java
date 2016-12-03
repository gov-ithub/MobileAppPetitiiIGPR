package ro.politiaromana.petitie.mobile.android.util;


public final class JavaUtils {

    private JavaUtils() {}

    public static boolean isEmpty(final CharSequence text) {
        return text == null || text.length() == 0;
    }
}
