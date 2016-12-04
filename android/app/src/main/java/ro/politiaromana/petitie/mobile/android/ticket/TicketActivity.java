package ro.politiaromana.petitie.mobile.android.ticket;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.ActivityProfileBinding;
import ro.politiaromana.petitie.mobile.android.util.ActivityUtils;

/**
 * Created by andrei.
 */

public class TicketActivity extends AppCompatActivity{

    public static void start(Context context) {
        Intent starter = new Intent(context, TicketActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityProfileBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_profile);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);

        FragmentManager fm = getSupportFragmentManager();
        TicketFragment ticketFragment = (TicketFragment) fm.findFragmentById(R.id.profile_container);
        if (ticketFragment == null) {
            ticketFragment = new TicketFragment();
            ActivityUtils.addFragmentToActivity(fm, ticketFragment, R.id.profile_container);
        }
    }
}
