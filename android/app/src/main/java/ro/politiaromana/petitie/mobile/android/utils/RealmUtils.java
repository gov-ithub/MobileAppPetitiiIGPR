package ro.politiaromana.petitie.mobile.android.utils;


import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Emitter;
import rx.Observable;

public final class RealmUtils {

    public static Observable<Realm> getManagedRealm() {
        return Observable.fromEmitter(emitter -> {
            final Realm realm = Realm.getDefaultInstance();
            emitter.setCancellation(realm::close);
            emitter.onNext(realm);
        }, Emitter.BackpressureMode.NONE);
    }

    public static <RlmObject extends RealmObject> Observable.Transformer<RealmResults<RlmObject>, RlmObject> nullIfNoResult() {
        return observable -> observable
                .filter(RealmResults::isLoaded)
                .map(rs -> {
                    if (rs.size() > 0) {
                        return rs.first();
                    }
                    return null;
                });
    }

    private RealmUtils() {}
}
