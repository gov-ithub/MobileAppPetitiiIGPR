package ro.politiaromana.petitie.mobile.android.widget;

import android.content.Context;
import android.util.AttributeSet;

import ro.politiaromana.petitie.mobile.android.R;


public class StepView extends android.support.v7.widget.AppCompatTextView {

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.politiaromana_StepIndicator_Step);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
