package ro.politiaromana.petitie.mobile.android.widget;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.utils.UiUtils;


public class StepIndicator extends LinearLayout {

    private TextView stepLabel;
    private LayoutTransition transition;

    private String[] steps;
    private int currentStepIndex;

    public StepIndicator(Context context) {
        this(context, null);
    }

    public StepIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.politiaromana_StepIndicator);
    }

    public StepIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setStepNames(String[] steps) {
        this.steps = steps;
        addSteps(this.steps);
    }

    public int previousStep() {
        if (currentStepIndex == 0) {
            return currentStepIndex;
        }

        setLayoutTransition(getPreviousStepTransition());

        removeView(stepLabel);
        getChildAt(currentStepIndex).setSelected(false);
        getChildAt(--currentStepIndex).setSelected(true);
        addView(stepLabel, currentStepIndex + 1);

        stepLabel.setText(steps[currentStepIndex]);

        return currentStepIndex;
    }

    public int nextStep() {
        if (currentStepIndex == steps.length - 1) {
            return currentStepIndex;
        }

        setLayoutTransition(getNextStepTransition());

        removeView(stepLabel);
        getChildAt(currentStepIndex).setSelected(false);
        getChildAt(++currentStepIndex).setSelected(true);
        addView(stepLabel, currentStepIndex + 1);

        stepLabel.setText(steps[currentStepIndex]);

        return currentStepIndex;
    }

    public void setSelectedStep(int index) {
        if (index < 0 || index > steps.length - 1 || index == currentStepIndex) {
            return;
        }

        if (index < currentStepIndex) {
            setLayoutTransition(getPreviousStepTransition());
        } else {
            setLayoutTransition(getNextStepTransition());
        }

        removeView(stepLabel);
        getChildAt(currentStepIndex).setSelected(false);
        currentStepIndex = index;
        getChildAt(currentStepIndex).setSelected(true);
        addView(stepLabel, currentStepIndex + 1);
        stepLabel.setText(steps[currentStepIndex]);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_step_indicator, this, true);
        transition = new LayoutTransition();

        setLayoutTransition(getNextStepTransition());

        stepLabel = (TextView) findViewById(R.id.step_label);
    }

    private void addSteps(String[] steps) {
        removeAllViews();

        for (int i = 0; i < steps.length; i++) {
            StepView stepView = new StepView(getContext());
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) UiUtils.dpToPx(getContext(), 5f);
            params.setMargins(margin, margin, margin, margin);
            stepView.setText(String.valueOf(i + 1));
            addView(stepView, params);
        }

        currentStepIndex = 0;
        stepLabel.setText(steps[currentStepIndex]);

        getChildAt(currentStepIndex).setSelected(true);
        addView(stepLabel, currentStepIndex + 1);
    }

    private LayoutTransition getPreviousStepTransition() {
        transition.setStartDelay(LayoutTransition.APPEARING, 300);
        transition.setDuration(300);
        return transition;
    }

    private LayoutTransition getNextStepTransition() {
        transition.setStartDelay(LayoutTransition.APPEARING, 450);
        transition.setDuration(300);
        return transition;
    }
}
