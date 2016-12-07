package ro.politiaromana.petitie.mobile.android.ticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityNewTicketBinding;
import ro.politiaromana.petitie.mobile.android.domain.GetProfileFromStorage;
import ro.politiaromana.petitie.mobile.android.domain.SaveProfileToStorage;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.profile.ProfileContract;
import ro.politiaromana.petitie.mobile.android.profile.ProfileFragment;
import ro.politiaromana.petitie.mobile.android.profile.ProfilePresenter;
import ro.politiaromana.petitie.mobile.android.utils.ActivityUtils;


public class NewTicketActivity extends AppCompatActivity {

    private ActivityNewTicketBinding binding;
    private ProfileContract.Presenter profilePresenter;
    private TicketDetailsContract.Presenter ticketDetailsPresenter;

    private boolean isMenuNextVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_ticket);
        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);

        String[] steps = getResources().getStringArray(R.array.petition_steps);
        binding.stepIndicator.setStepNames(steps);

        initiatePetitionFlow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_ticket_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem next = menu.findItem(R.id.action_next);
//        MenuItem send = menu.findItem(R.id.action_send);

        next.setEnabled(profilePresenter.isValid());
        next.setVisible(isMenuNextVisible);

//        send.setVisible(!isMenuNextVisible);

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

    private void initiatePetitionFlow() {
        final FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ProfileFragment profileFragment = (ProfileFragment) fm.findFragmentByTag(ProfileFragment.class.getSimpleName());
        if (profileFragment != null) {
            fm.beginTransaction().remove(profileFragment).commit();
        }
        fm.executePendingTransactions();

        profilePresenter = new ProfilePresenter(new GetProfileFromStorage(), new SaveProfileToStorage());
        profilePresenter.setUpdatesCallback((isValid, hasUnsavedChanges) -> supportInvalidateOptionsMenu());

        profileFragment = new ProfileFragment();
        profileFragment.setPresenter(profilePresenter);
        fm.beginTransaction()
                .replace(R.id.petition_steps_container, profileFragment, ProfileFragment.class.getSimpleName())
                .commit();
        binding.stepIndicator.setSelectedStep(0);
    }

    private void nextPetitionFragment() {
        ticketDetailsPresenter = new TicketDetailsPresenter();
        ticketDetailsPresenter.setOnSendCallback(ticket ->
                new GetProfileFromStorage().call()
                        .subscribe(profile -> showEmailClient(profile, ticket)));

        TicketDetailsFragment ticketDetailsFragment = new TicketDetailsFragment();
        ticketDetailsFragment.setPresenter(ticketDetailsPresenter);
        getTransactionWithAnimations()
                .replace(R.id.petition_steps_container, ticketDetailsFragment, TicketDetailsFragment.class.getSimpleName())
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

    public void showEmailClient(Profile profile, Ticket ticket) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"abc@xyz.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ticket.typeStringValue);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prenume: ").append(profile.firstName).append("\n");
        stringBuilder.append("Nume: ").append(profile.lastName).append("\n");
        stringBuilder.append("Email: ").append(profile.email).append("\n");
        stringBuilder.append("CNP: ").append(profile.cnp).append("\n");
        stringBuilder.append("Adresa domiciliu: ").append(profile.address).append("\n");
        stringBuilder.append("Judet: ").append(profile.county).append("\n");
        stringBuilder.append("Telefon: ").append(profile.phone).append("\n\n");
        stringBuilder.append("Locatie petitie: ").append(ticket.address).append("\n");
        stringBuilder.append("Mesaj: ").append(ticket.description);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, stringBuilder.toString());

        ArrayList<Uri> uriList = new ArrayList<>();
        for (RealmString path : ticket.attachmentPathList) {
            File file = new File(path.val);
            uriList.add(Uri.fromFile(file));
        }
        emailIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);

        startActivity(Intent.createChooser(emailIntent, getString(R.string.chooser_title)));
    }
}
