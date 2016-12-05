package ro.politiaromana.petitie.mobile.android.ticket;

import java.util.List;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.util.AbsPresenter;

/**
 * Created by andrei.
 */

public interface TicketContract {

    interface View {

        String getType();
        List<String> getAttachmentList();
        String getAddress();
        String getDescription();

        void showDescriptionError();
        void clearDescriptionError();

        void showEmailClient(Profile profile, Ticket ticket);
    }

    interface Presenter extends AbsPresenter<View> {
        void onSendButtonClicked();
    }

}
