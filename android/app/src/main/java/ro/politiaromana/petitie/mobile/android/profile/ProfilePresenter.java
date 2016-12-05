package ro.politiaromana.petitie.mobile.android.profile;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.utils.BasePresenter;
import ro.politiaromana.petitie.mobile.android.utils.RxUi;
import ro.politiaromana.petitie.mobile.android.utils.Validator;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

import static ro.politiaromana.petitie.mobile.android.utils.RxUi.required;

public class ProfilePresenter extends BasePresenter<ProfileContract.View> implements ProfileContract.Presenter {

    @NonNull
    private final Func0<Observable<Profile>> getProfile;
    @NonNull
    private final Func1<Profile, Observable<Profile>> saveProfile;
    @Nullable
    private Profile loadedProfile;
    private boolean isFormValid = true;
    private boolean hasFormUnsavedChanges = false;
    private ConnectableObservable<Profile> profileFormObservable;
    @Nullable
    private Action2<Boolean, Boolean> updatesCallback;

    public ProfilePresenter(@NonNull Func0<Observable<Profile>> getProfile, @NonNull Func1<Profile, Observable<Profile>> saveProfile) {
        this.getProfile = getProfile;
        this.saveProfile = saveProfile;
    }

    @Override
    public boolean isValid() {
        return isFormValid;
    }

    @Override
    public boolean hasUnsavedChanges() {
        return hasFormUnsavedChanges;
    }

    @Override
    public void setUpdatesCallback(@NonNull Action2<Boolean, Boolean> callback) {
        updatesCallback = callback;
    }

    @Override
    public void takeView(@NonNull ProfileContract.View view) {
        super.takeView(view);
        getProfile.call()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    this.loadedProfile = profile;
                    this.isFormValid = loadedProfile != null;
                    this.hasFormUnsavedChanges = false;
                    view.bind(profile);
                    notifyUpdates();
                    startFormValidation();
                }, view::showError);
    }

    @Override
    public void saveChanges(@Nullable Action0 callback) {
        final Subscription s = profileFormObservable.autoConnect().first()
                .observeOn(Schedulers.io())
                .concatMap(saveProfile::call)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savedProfile -> {
                    this.loadedProfile = savedProfile;
                    this.isFormValid = true;
                    this.hasFormUnsavedChanges = false;
                    notifyUpdates();
                    if (callback != null) {
                        callback.call();
                    }
                }, view::showError);
        lifecycleSubscription.add(s);
    }

    private void startFormValidation() {
        // We need the last Profile emitted for further processing (save it on storage)
        profileFormObservable = processForm().replay(1);

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
                .compose(RxUi.validate(Validator::isValidROPhoneNumber, PHONE_INVALID_DEFAULT, view::clearPhoneErrors, DO_NOTHING, view::showPhoneInvalidError));

        return Observable.combineLatest(firstNameObs, lastNameObs, emailObs, cnpObs, addressObs, countyObs, phoneObs,
                (firstName, lastName, email, cnp, address, county, phone) -> {
                    if (fieldsNotEmpty(firstName, lastName, email, cnp, address, county) && !PHONE_INVALID_DEFAULT.equals(phone)) {
                        return new Profile(firstName, lastName, email, cnp, address, county, phone);
                    }

                    return null;
                })
                .doOnNext(profile -> {
                    isFormValid = profile != null;
                    hasFormUnsavedChanges = loadedProfile != null ? !loadedProfile.isEqualTo(profile) : isFormValid;
                    notifyUpdates();
                })
                .filter(p -> p != null);
    }

    private void notifyUpdates() {
        if (view != null) {
            view.setFormStatus(isFormValid, hasFormUnsavedChanges);
        }
        if (updatesCallback != null) {
            updatesCallback.call(isFormValid, hasFormUnsavedChanges);
        }
    }

    private static boolean fieldsNotEmpty(String firstName, String lastName, String email, String cnp, String address, String county) {
        return firstName != null && lastName != null && email != null && cnp != null && address != null && county != null;
    }

    private static final Action0 DO_NOTHING = () -> { };

    private static final String PHONE_INVALID_DEFAULT = "whatever_just_no_valid_phone";
}
