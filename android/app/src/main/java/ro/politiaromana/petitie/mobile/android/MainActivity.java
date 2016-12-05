package ro.politiaromana.petitie.mobile.android;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ro.politiaromana.petitie.mobile.android.databinding.ActivityMainBinding;
import ro.politiaromana.petitie.mobile.android.ticket.TicketActivity;
import ro.politiaromana.petitie.mobile.android.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ActivityUtils.setupSimpleToolbar(this, binding.toolbar);
        setupDrawer();
    }

    private void setupDrawer() {
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(binding.toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(buildDrawerHeader())
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_profile).withIcon(GoogleMaterial.Icon.gmd_account_circle).withSelectable(false).withIdentifier(DRAWER_ITEM_PROFILE),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_legislation).withIcon(GoogleMaterial.Icon.gmd_assignment).withSelectable(false).withIdentifier(DRAWER_ITEM_LEGISLATION),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_feedback).withIcon(GoogleMaterial.Icon.gmd_announcement).withSelectable(false).withIdentifier(DRAWER_ITEM_FEEDBACK),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_invite_friends).withIcon(GoogleMaterial.Icon.gmd_supervisor_account).withSelectable(false).withIdentifier(DRAWER_ITEM_INVITES)
                )
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_faq).withIcon(GoogleMaterial.Icon.gmd_help).withSelectable(false).withIdentifier(DRAWER_ITEM_FAQ),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(GoogleMaterial.Icon.gmd_info).withSelectable(false).withIdentifier(DRAWER_ITEM_ABOUT)
                )
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    @DrawerItem final int id = (int) drawerItem.getIdentifier();
                    switch (id) {
                        case DRAWER_ITEM_PROFILE:
//                            startActivity(new Intent(this, ProfileActivity.class));
                            TicketActivity.start(this);
                            break;
                        case DRAWER_ITEM_LEGISLATION:
                            startActivity(new Intent(this, LegislationActivity.class));
                            break;
                        case DRAWER_ITEM_FEEDBACK:
                            // TODO: send email with feedback
                            Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
                            break;
                        case DRAWER_ITEM_INVITES:
                            // TODO: use Firebase Invites
                            Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show();
                            break;
                        case DRAWER_ITEM_FAQ:
                            startActivity(new Intent(this, FAQActivity.class));
                            break;
                        case DRAWER_ITEM_ABOUT:
                            startActivity(new Intent(this, AboutActivity.class));
                            break;
                    }
                    return true;
                })
                .build();
    }

    private AccountHeader buildDrawerHeader() {
        return new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.header)
                .build();
    }


    public static final int DRAWER_ITEM_PROFILE = 1;
    public static final int DRAWER_ITEM_LEGISLATION = 2;
    public static final int DRAWER_ITEM_FEEDBACK = 3;
    public static final int DRAWER_ITEM_INVITES = 4;
    public static final int DRAWER_ITEM_FAQ = 5;
    public static final int DRAWER_ITEM_ABOUT = 6;

    @IntDef(value = {DRAWER_ITEM_PROFILE, DRAWER_ITEM_LEGISLATION, DRAWER_ITEM_FEEDBACK,
            DRAWER_ITEM_INVITES, DRAWER_ITEM_FAQ, DRAWER_ITEM_ABOUT})
    @Retention(RetentionPolicy.SOURCE)
    @interface DrawerItem {}
}
