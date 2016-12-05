package ro.politiaromana.petitie.mobile.android.domain;


import io.realm.Realm;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import rx.Observable;
import rx.functions.Func0;

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class GetProfileFromStorage implements Func0<Observable<Profile>> {

    @Override
    public Observable<Profile> call() {
        return Observable.fromCallable(() -> {
            final Realm realm = Realm.getDefaultInstance();
            Profile detached = null;
            try {
                final Profile profile = realm.where(Profile.class).findFirst();
                if (profile != null) {
                    detached = realm.copyFromRealm(profile);
                }
            } finally {
                realm.close();
            }

            return detached;
        });
    }
}
