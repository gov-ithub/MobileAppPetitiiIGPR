package ro.politiaromana.petitie.mobile.android.profile;


import ro.politiaromana.petitie.mobile.android.util.AbsPresenter;
import rx.Observable;

public interface ProfileContract {

    interface View {

        //region Form fields
        Observable<String> getFirstNameObservable();

        void showFirstNameRequiredError();

        void clearFirstNameErrors();

        Observable<String> getLastNameObservable();

        void showLastNameRequiredError();

        void clearLastNameErrors();

        Observable<String> getEmailObservable();

        void showEmailRequiredError();

        void showEmailInvalidError();

        void clearEmailErrors();

        Observable<String> getCNPObservable();

        void showCNPRequiredError();

        void showCNPInvalidError();

        void clearCNPErrors();

        Observable<String> getAddressObservable();

        void showAddressRequiredError();

        void clearAddressErrors();

        Observable<String> getCountyObservable();

        Observable<String> getPhoneObservable();

        void showPhoneInvalidError();

        void clearPhoneErrors();
        //endregion
    }

    interface Presenter extends AbsPresenter<View> {

        void bambam();
    }
}
