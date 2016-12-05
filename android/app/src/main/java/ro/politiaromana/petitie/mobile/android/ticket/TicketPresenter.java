package ro.politiaromana.petitie.mobile.android.ticket;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.RealmList;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.util.BasePresenter;
import ro.politiaromana.petitie.mobile.android.util.RxUi;
import rx.Observable;
import rx.observables.ConnectableObservable;

/**
 * Created by andrei.
 */

public class TicketPresenter extends BasePresenter<TicketContract.View>
        implements TicketContract.Presenter {

    private ConnectableObservable<Ticket> ticketFormObservable;

    @Override public void takeView(@NonNull TicketContract.View view) {
        super.takeView(view);
        ticketFormObservable = processForm().replay(1);

        lifecycleSubscription.add(ticketFormObservable.autoConnect().subscribe());
    }

    private Observable<Ticket> processForm() {
        final Observable<String> ticketTypeObservable = view.getTypeObservable();
        final Observable<List<RealmString>> attachmentObservable =
                view.getAttachmentPathListObservable()
                        .compose(mapStringListToRealmStringListTransformer());
        final Observable<String> addressObservable = view.getAddressObservable();
        final Observable<String> descriptionObservable = view.getDescriptionObservable()
                .compose(RxUi.required(view::clearDescriptionError, view::showDescriptionError));

        return Observable.combineLatest(ticketTypeObservable, attachmentObservable,
                addressObservable, descriptionObservable,
                (type, attachmentList, address, description) -> {
                    if(formNotEmpty(type, description)){
                        Ticket ticket = new Ticket(type, description);
                        ticket.address = address;
                        RealmList<RealmString> attachmentPathList = new RealmList<>();
                        attachmentPathList.addAll(attachmentList);
                        ticket.attachmentPathList = attachmentPathList;
                        return ticket;
                    }
                    return null;
                })
                .doOnNext(ticket -> view.onTicketFormValidation(ticket != null))
                .filter(ticket -> ticket != null);
    }

    private static boolean formNotEmpty(String type, String description) {
        return type != null && description != null;
    }

    private Observable.Transformer<List<String>, List<RealmString>> mapStringListToRealmStringListTransformer(){
        return observable -> observable.flatMapIterable(list -> list)
                .map(RealmString::new)
                .toList();
    }

    public ConnectableObservable<Ticket> getTicketFormObservable() {
        return ticketFormObservable;
    }
}
