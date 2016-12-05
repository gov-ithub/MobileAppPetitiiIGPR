package ro.politiaromana.petitie.mobile.android.petition;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityPetitionBinding;
import ro.politiaromana.petitie.mobile.android.domain.GetProfileFromStorage;
import ro.politiaromana.petitie.mobile.android.domain.SaveProfileTo;
import ro.politiaromana.petitie.mobile.android.profile.ProfileContract;
import ro.politiaromana.petitie.mobile.android.profile.ProfileFragment;
import ro.politiaromana.petitie.mobile.android.profile.ProfilePresenter;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;


public class PetitionActivity extends AppCompatActivity implements PetitionContract.View {

    private Realm tempRealm;

    private ActivityPetitionBinding binding;
    private PetitionContract.Presenter presenter;
    private ProfileContract.Presenter profilePresenter;

    private boolean isMenuNextVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_petition);
        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);

        tempRealm = Realm.getInstance(configureInMemoryRealm());

        presenter = new PetitionPresenter();

        presenter.takeView(this);

        String[] steps = getResources().getStringArray(R.array.petition_steps);
        binding.stepIndicator.setStepNames(steps);

        initiatePetitionFlow();

        Toast.makeText(this, "In progress!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_petition_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem next = menu.findItem(R.id.action_next);
        MenuItem send = menu.findItem(R.id.action_send);

        next.setEnabled(profilePresenter.isValid());
        next.setVisible(isMenuNextVisible);

        send.setVisible(!isMenuNextVisible);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                profilePresenter.saveChanges(() -> {
                    isMenuNextVisible = false;
                    nextPetitionFragment();
                });
                return true;
            case R.id.action_send:
                Toast.makeText(this, "Sending!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!goBackOneStep()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean goBackOneStep() {
        FragmentManager childFm = getSupportFragmentManager();
        if (childFm.getBackStackEntryCount() > 0) {
            childFm.popBackStack();
            binding.stepIndicator.previousStep();
            isMenuNextVisible = true;
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tempRealm.close();
    }

    private void initiatePetitionFlow() {
        final FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ProfileFragment profileFragment = (ProfileFragment) fm.findFragmentByTag(ProfileFragment.class.getSimpleName());
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }
        fm.executePendingTransactions();

        profilePresenter = new ProfilePresenter(new GetProfileFromStorage(), new SaveProfileTo(tempRealm.getConfiguration()));
        profilePresenter.setUpdatesCallback((isValid, hasUnsavedChanges) -> supportInvalidateOptionsMenu());

        profileFragment = new ProfileFragment();
        profileFragment.setPresenter(profilePresenter);
        fm.beginTransaction()
                .replace(R.id.petition_steps_container, profileFragment, ProfileFragment.class.getSimpleName())
                .commit();
        binding.stepIndicator.setSelectedStep(0);
    }

    private void nextPetitionFragment() {
        Fragment petitionFragment = new PetitionFragment();
        getTransactionWithAnimations()
                .replace(R.id.petition_steps_container, petitionFragment, PetitionFragment.class.getSimpleName())
                .addToBackStack("petitions")
                .commit();
        binding.stepIndicator.nextStep();
    }

    @SuppressLint("CommitTransaction")
    private FragmentTransaction getTransactionWithAnimations() {
        return getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.in_right, R.anim.out_left, R.anim.in_left, R.anim.out_right);
    }

    private static RealmConfiguration configureInMemoryRealm() {
        return new RealmConfiguration.Builder()
                .name("temp.realm")
                .deleteRealmIfMigrationNeeded()
                .inMemory()
                .build();
    }
}
