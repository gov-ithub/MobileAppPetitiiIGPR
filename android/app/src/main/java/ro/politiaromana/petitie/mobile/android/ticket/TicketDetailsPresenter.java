package ro.politiaromana.petitie.mobile.android.ticket;

import android.support.annotation.Nullable;

import io.realm.RealmList;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.utils.BasePresenter;
import ro.politiaromana.petitie.mobile.android.utils.JavaUtils;
import rx.functions.Action1;


public class TicketDetailsPresenter extends BasePresenter<TicketDetailsContract.View> implements TicketDetailsContract.Presenter {

    @Nullable
    private Action1<Ticket> callback;

    @Override
    public void setOnSendCallback(@Nullable Action1<Ticket> callback) {
        this.callback = callback;
    }

    @Override
    public void onSendButtonClicked() {
        if (callback != null && validateForm()) {
            Ticket ticket = new Ticket();
            ticket.address = view.getAddress();
            ticket.description = view.getDescription();
            ticket.typeStringValue = view.getTicketType();
            ticket.attachmentPathList = new RealmList<>();
            for (String path : view.getAttachmentList()) {
                ticket.attachmentPathList.add(new RealmString(path));
            }

            callback.call(ticket);
            view.onEmailSent();
        }
    }

    @Override
    public void onLocationIconClicked() {
        view.showChoosePlaceScreen();
    }

    private boolean validateForm() {
        if (JavaUtils.isEmpty(view.getTicketType())) {
            return false;
        }
        if (JavaUtils.isEmpty(view.getDescription())) {
            view.showDescriptionRequiredError();
            return false;
        }
        view.clearDescriptionErrors();
        return true;
    }
}
