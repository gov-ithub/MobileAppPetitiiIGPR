package ro.politiaromana.petitie.mobile.android;

import android.app.Application;

import io.realm.Realm;
import timber.log.Timber;


public class PetitionsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        Timber.plant(new Timber.DebugTree());
    }
}
