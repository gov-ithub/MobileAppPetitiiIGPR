package ro.politiaromana.petitie.mobile.android.ticket;

import android.support.annotation.NonNull;

import io.realm.RealmList;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.util.BasePresenter;

/**
 * Created by andrei.
 */

public class TicketPresenter extends BasePresenter<TicketContract.View>
        implements TicketContract.Presenter {

    private Profile profile;

    public TicketPresenter(Profile profile){
        this.profile = profile;
    }

    @Override public void takeView(@NonNull TicketContract.View view) {
        super.takeView(view);
    }

    @Override public void onSendButtonClicked() {
        if(validateForm()){
            Ticket ticket = new Ticket();
            ticket.address = view.getAddress();
            ticket.description = view.getDescription();
            ticket.typeStringValue = view.getType();
            ticket.attachmentPathList = new RealmList<>();
            for(String path : view.getAttachmentList()){
                ticket.attachmentPathList.add(new RealmString(path));
            }
            view.showEmailClient(profile, ticket);
        }
    }

    @Override public void onLocationIconClicked() {
        view.showChoosePlaceScreen();
    }

    private boolean validateForm() {
        if(view.getType() == null || view.getType().isEmpty()){
            return false;
        }
        if(view.getDescription() == null || view.getDescription().isEmpty()){
            view.showDescriptionError();
            return false;
        }
        view.clearDescriptionError();
        return true;
    }
}
