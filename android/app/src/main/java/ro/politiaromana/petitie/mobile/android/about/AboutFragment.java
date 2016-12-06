package ro.politiaromana.petitie.mobile.android.about;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import ro.politiaromana.petitie.mobile.android.BuildConfig;
import ro.politiaromana.petitie.mobile.android.R;

/**
 * Created by andrei.
 */

public class AboutFragment extends PreferenceFragmentCompat {

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.setPreferencesFromResource(R.xml.preferences_about, rootKey);
        Preference buildVersionPreference = this.findPreference(getString(R.string.key_build_version));
        buildVersionPreference.setSummary("v"+BuildConfig.VERSION_NAME);
    }
}
