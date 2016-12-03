package ro.politiaromana.petitie.mobile.android.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityProfileBinding;
import ro.politiaromana.petitie.mobile.android.util.ActivityUtils;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.profile_container);
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), profileFragment, R.id.profile_container);
        }
    }
}
