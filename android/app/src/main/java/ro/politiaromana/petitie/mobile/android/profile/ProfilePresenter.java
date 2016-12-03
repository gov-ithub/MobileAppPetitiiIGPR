package ro.politiaromana.petitie.mobile.android.profile;


import android.support.annotation.NonNull;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.util.BasePresenter;
import ro.politiaromana.petitie.mobile.android.util.RxUi;
import ro.politiaromana.petitie.mobile.android.util.Validator;
import rx.Observable;
import rx.functions.Action0;
import rx.observables.ConnectableObservable;

import static ro.politiaromana.petitie.mobile.android.util.RxUi.required;

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {

    private ConnectableObservable<Profile> profileFormObservable;

    public ProfilePresenter() {

    }

    @Override
    public void takeView(@NonNull ProfileContract.View view) {
        super.takeView(view);
        // We need the last Profile emitted for further processing (save it on storage)
        profileFormObservable = processForm().replay(1);

        // Start form validation
        lifecycleSubscription.add(profileFormObservable.autoConnect().subscribe());
    }

    private Observable<Profile> processForm() {
        final Observable<String> firstNameObs = view.getFirstNameObservable()
                .compose(required(view::clearFirstNameErrors, view::showFirstNameRequiredError));
        final Observable<String> lastNameObs = view.getLastNameObservable()
                .compose(required(view::clearLastNameErrors, view::showLastNameRequiredError));
        final Observable<String> emailObs = view.getEmailObservable()
                .compose(RxUi.validate(Validator::isValidEmailAddress, view::clearEmailErrors, view::showEmailRequiredError, view::showEmailInvalidError));
        final Observable<String> cnpObs = view.getCNPObservable()
                .compose(RxUi.validate(Validator::isValidCNP, view::clearCNPErrors, view::showCNPRequiredError, view::showCNPInvalidError));
        final Observable<String> addressObs = view.getAddressObservable()
                .compose(RxUi.required(view::clearAddressErrors, view::showAddressRequiredError));
        final Observable<String> countyObs = view.getCountyObservable();
        final Observable<String> phoneObs = view.getPhoneObservable()
                .startWith("") // field is optional
                .compose(RxUi.validate(Validator::isValidROPhoneNumber, view::clearPhoneErrors, DO_NOTHING, view::showPhoneInvalidError));

        return Observable.combineLatest(firstNameObs, lastNameObs, emailObs, cnpObs, addressObs, countyObs, phoneObs,
                (firstName, lastName, email, cnp, address, county, phone) -> {
                    if (fieldsNotEmpty(firstName, lastName, email, cnp, address, county)) {
                        return new Profile(firstName, lastName, email, cnp, address, county, phone);
                    }

                    return null;
                })
                .doOnNext(profile -> view.onProfileFormValidation(profile != null))
                .filter(p -> p != null);
    }

    private static boolean fieldsNotEmpty(String firstName, String lastName, String email, String cnp, String address, String county) {
        return firstName != null && lastName != null && email != null && cnp != null && address != null && county != null;
    }

    private static final Action0 DO_NOTHING = () -> { };

    @Override
    public void bambam() {

    }
}
