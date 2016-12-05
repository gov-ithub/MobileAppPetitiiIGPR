package ro.politiaromana.petitie.mobile.android.about;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityAboutBinding;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);

        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (aboutFragment == null) {
            aboutFragment = AboutFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), aboutFragment, R.id.container);
        }
    }
}
