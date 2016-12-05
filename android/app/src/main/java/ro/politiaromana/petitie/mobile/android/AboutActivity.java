package ro.politiaromana.petitie.mobile.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.databinding.ActivityAboutBinding;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);
    }
}
