package ro.politiaromana.petitie.mobile.android.ticket;

import java.util.List;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.util.AbsPresenter;
import rx.Observable;

/**
 * Created by andrei.
 */

public interface TicketContract {

    interface View {

        Observable<String> getTypeObservable();
        Observable<List<String>> getAttachmentPathListObservable();

        Observable<String> getAddressObservable();

        Observable<String> getDescriptionObservable();
        void showDescriptionError();
        void clearDescriptionError();

        void onTicketFormValidation(boolean pB);
        void showEmailClient(Profile profile, Ticket ticket);
    }

    interface Presenter extends AbsPresenter<View> {

    }

}
