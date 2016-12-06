package ro.politiaromana.petitie.mobile.android.ticket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.utils.AbsPresenter;
import rx.functions.Action1;


public interface TicketDetailsContract {

    interface View {

        void setPresenter(@NonNull TicketDetailsContract.Presenter presenter);

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

        void onEmailSent();

        void showChoosePlaceScreen();
    }

    interface Presenter extends AbsPresenter<View> {

        void setOnSendCallback(@Nullable Action1<Ticket> callback);

        void onSendButtonClicked();

        void onLocationIconClicked();
    }
}
