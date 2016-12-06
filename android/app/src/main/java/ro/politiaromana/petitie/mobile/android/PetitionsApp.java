package ro.politiaromana.petitie.mobile.android;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import timber.log.Timber;


public class PetitionsApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        Timber.plant(new Timber.DebugTree());
    }
}
