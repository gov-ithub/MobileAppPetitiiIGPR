package ro.politiaromana.petitie.mobile.android.ticket;

import io.realm.RealmList;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.utils.BasePresenter;


public class TicketPresenter extends BasePresenter<TicketDetailsContract.View> implements TicketDetailsContract.Presenter {

    @Override
    public void onSendButtonClicked() {
        if (validateForm()) {
            Ticket ticket = new Ticket();
            ticket.address = view.getAddress();
            ticket.description = view.getDescription();
            ticket.typeStringValue = view.getTicketType();
            ticket.attachmentPathList = new RealmList<>();
            for (String path : view.getAttachmentList()) {
                ticket.attachmentPathList.add(new RealmString(path));
            }
            view.showEmailClient(new Profile(), ticket);
        }
    }

    @Override
    public void onLocationIconClicked() {
        view.showChoosePlaceScreen();
    }

    private boolean validateForm() {
        if (view.getTicketType() == null || view.getTicketType().isEmpty()) {
            return false;
        }
        if (view.getDescription() == null || view.getDescription().isEmpty()) {
            view.showDescriptionRequiredError();
            return false;
        }
        view.clearDescriptionErrors();
        return true;
    }
}
