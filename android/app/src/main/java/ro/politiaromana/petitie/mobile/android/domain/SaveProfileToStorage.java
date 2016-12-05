package ro.politiaromana.petitie.mobile.android.domain;


import io.realm.Realm;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import rx.Observable;
import rx.functions.Func1;

public class SaveProfileToStorage implements Func1<Profile, Observable<Profile>> {

    @Override
    public Observable<Profile> call(Profile profile) {
        return Observable.fromCallable(() -> {
            final Realm realm = Realm.getDefaultInstance();
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
