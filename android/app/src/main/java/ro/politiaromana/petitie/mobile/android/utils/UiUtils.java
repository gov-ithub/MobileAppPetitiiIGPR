package ro.politiaromana.petitie.mobile.android.utils;

import android.content.Context;
import android.util.TypedValue;


public final class UiUtils {

    public static float pxToDp(Context context, int px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxToSp(Context context, int px) {
        return px / context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private UiUtils() {}
}
