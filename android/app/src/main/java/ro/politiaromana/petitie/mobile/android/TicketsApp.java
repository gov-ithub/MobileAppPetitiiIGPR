package ro.politiaromana.petitie.mobile.android;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class TicketsApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        configureRealm();

        Timber.plant(new Timber.DebugTree());
    }

    private void configureRealm() {
        Realm.init(this);
        Realm.setDefaultConfiguration(createDefaultConfig());
    }

    private static RealmConfiguration createDefaultConfig() {
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
