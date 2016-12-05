package ro.politiaromana.petitie.mobile.android.utils;


public final class JavaUtils {

    private JavaUtils() {}

    public static boolean isEmpty(final CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence text) {
        // use inline when converting this to kotlin
        return !isEmpty(text);
    }
}
