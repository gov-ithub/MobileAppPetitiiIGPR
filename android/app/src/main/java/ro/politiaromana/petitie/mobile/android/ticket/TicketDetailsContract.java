package ro.politiaromana.petitie.mobile.android.ticket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.utils.AbsPresenter;


public interface TicketDetailsContract {

    interface View {

        @NonNull
        String getTicketType();

        @NonNull
        List<String> getAttachmentList();

        @Nullable
        String getAddress();

        @NonNull
        String getDescription();

        void showDescriptionRequiredError();

        void clearDescriptionErrors();

        void showEmailClient(Profile profile, Ticket ticket);

        void showChoosePlaceScreen();
    }

    interface Presenter extends AbsPresenter<View> {

        void onSendButtonClicked();

        void onLocationIconClicked();
    }
}
