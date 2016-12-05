package ro.politiaromana.petitie.mobile.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.databinding.ActivityLegislationBinding;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;

public class LegislationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLegislationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_legislation);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);
    }
}
