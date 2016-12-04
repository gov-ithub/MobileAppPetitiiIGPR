package ro.politiaromana.petitie.mobile.android.ticket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentTicketBinding;
import ro.politiaromana.petitie.mobile.android.util.RxUi;
import rx.Observable;

/**
 * Created by andrei.
 */

public class TicketFragment extends Fragment implements TicketContract.View {

    private FragmentTicketBinding binding;
    private TicketContract.Presenter presenter;
    private CharSequence[] ticketTypeArray;
    private List<String> attachmentPathList;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ticketTypeArray = getResources().getTextArray(R.array.ticket_type_array);
        this.attachmentPathList = new ArrayList<>();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false);

        ArrayAdapter<CharSequence> ticketTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ticketTypeArray);

        binding.ticketTypeSpinner.setAdapter(ticketTypeAdapter);

        this.presenter = new TicketPresenter();
        this.presenter.takeView(this);

        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.dropView();
    }

    @Override public Observable<String> getTypeObservable() {
        return RxAdapterView.itemSelections(binding.ticketTypeSpinner)
                .skip(1)
                .map(position -> ticketTypeArray[position].toString());
    }

    @Override public Observable<List<String>> getAttachmentPathListObservable() {
        return Observable.just(attachmentPathList);
    }

    @Override public Observable<String> getAddressObservable() {
        return RxUi.formField(binding.addressInput);
    }

    @Override public Observable<String> getDescriptionObservable() {
        return RxUi.formField(binding.descriptionInput);
    }

    @Override public void showDescriptionError() {
        binding.descriptionInputLayout.setError(getString(R.string.error_description_required));
    }

    @Override public void clearDescriptionError(){
        binding.descriptionInputLayout.setError(null);
    }

    @Override public void onTicketFormValidation(boolean isValid) {
        Toast.makeText(getContext(), "valid: " + isValid, Toast.LENGTH_SHORT).show();
    }
}
