package ro.politiaromana.petitie.mobile.android.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityProfileBinding;
import ro.politiaromana.petitie.mobile.android.domain.GetProfileFromStorage;
import ro.politiaromana.petitie.mobile.android.domain.SaveProfileToStorage;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;

public class ProfileActivity extends AppCompatActivity {

    private ProfileContract.Presenter presenter;

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

        presenter = new ProfilePresenter(new GetProfileFromStorage(), new SaveProfileToStorage());
        profileFragment.setPresenter(presenter);

        presenter.setUpdatesCallback((isValid, hasUnsavedChanges) -> supportInvalidateOptionsMenu());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save).setEnabled(presenter.hasUnsavedChanges());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            presenter.saveChanges(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (presenter.hasUnsavedChanges()) {
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setTitle(R.string.profile_save_changes_title)
                    .setMessage(R.string.profile_save_changes_message)
                    .setPositiveButton(R.string.action_yes, (dialog, which) -> presenter.saveChanges(super::onBackPressed))
                    .setNegativeButton(R.string.action_no, (dialog, which) -> super.onBackPressed())
                    .create()
                    .show();
        } else {
            super.onBackPressed();
        }
    }
}
