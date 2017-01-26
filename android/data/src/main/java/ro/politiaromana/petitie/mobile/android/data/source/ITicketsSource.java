package ro.politiaromana.petitie.mobile.android.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ro.politiaromana.petitie.mobile.android.data.model.DataTicket;
import rx.Observable;

/**
 * Created by andrei.
 */

public interface ITicketsSource {

    Observable<List<DataTicket>> getTickets();

    Observable<DataTicket> getTicket(@NonNull String taskId);




}
