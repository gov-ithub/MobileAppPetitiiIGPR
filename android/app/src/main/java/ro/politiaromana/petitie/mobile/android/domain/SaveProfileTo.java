package ro.politiaromana.petitie.mobile.android.domain;


import android.support.annotation.NonNull;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import rx.Observable;
import rx.functions.Func1;

public class SaveProfileTo implements Func1<Profile, Observable<Profile>> {

    @NonNull
    private final RealmConfiguration realmConfiguration;

    public SaveProfileTo(@NonNull RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    @Override
    public Observable<Profile> call(Profile profile) {
        return Observable.fromCallable(() -> {
            final Realm realm = Realm.getInstance(realmConfiguration);
            try {
                realm.executeTransaction(r -> {
                    r.delete(Profile.class);
                    r.copyToRealm(profile);
                });
            } finally {
                realm.close();
            }

            return profile;
        });
    }
}
