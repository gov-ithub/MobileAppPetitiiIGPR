package ro.politiaromana.petitie.mobile.android.profile;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.utils.AbsPresenter;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action2;

public interface ProfileContract {

    interface View {

        void setPresenter(@NonNull ProfileContract.Presenter presenter);

        void bind(@Nullable Profile profile);

        void showError(Throwable e);

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

        void setFormStatus(boolean isValid, boolean shouldBeSaved);
        //endregion
    }

    interface Presenter extends AbsPresenter<View> {

        boolean isValid();

        boolean hasUnsavedChanges();

        void setUpdatesCallback(@NonNull Action2<Boolean, Boolean> callback);

        void saveChanges(@Nullable Action0 callback);
    }
}
