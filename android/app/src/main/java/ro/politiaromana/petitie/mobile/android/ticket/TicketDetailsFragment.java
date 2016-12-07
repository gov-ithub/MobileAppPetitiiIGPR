package ro.politiaromana.petitie.mobile.android.ticket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentTicketBinding;
import ro.politiaromana.petitie.mobile.android.utils.CameraUtil;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.mikepenz.iconics.Iconics.TAG;

public class TicketDetailsFragment extends Fragment implements TicketDetailsContract.View {

    private static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    private static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_3 = 3;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 4;

    private FragmentTicketBinding binding;
    private TicketDetailsContract.Presenter presenter;
    private CharSequence[] ticketTypeArray;
    private List<String> attachmentPathList = new ArrayList<>(4);

    private File currentPhotoFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ticketTypeArray = getResources().getTextArray(R.array.ticket_type_array);
    }

    @Override
    public void setPresenter(@NonNull TicketDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false);

        ArrayAdapter<CharSequence> ticketTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ticketTypeArray);

        binding.ticketTypeSpinner.setAdapter(ticketTypeAdapter);

        binding.image1.setOnClickListener(v ->
                currentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketDetailsFragment.this,
                        REQUEST_IMAGE_CAPTURE_1)
        );
        binding.image2.setOnClickListener(v ->
                currentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketDetailsFragment.this,
                        REQUEST_IMAGE_CAPTURE_2)
        );
        binding.image3.setOnClickListener(v ->
                currentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketDetailsFragment.this,
                        REQUEST_IMAGE_CAPTURE_3)
        );

        binding.sendTicket.setOnClickListener(v -> presenter.onSendButtonClicked());

        binding.iconLocation.setOnClickListener(v -> presenter.onLocationIconClicked());

        this.presenter.takeView(this);

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK
                && (requestCode == REQUEST_IMAGE_CAPTURE_1
                || requestCode == REQUEST_IMAGE_CAPTURE_2
                || requestCode == REQUEST_IMAGE_CAPTURE_3)) {
            onImageFailure();
            return;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_1 && resultCode == RESULT_OK) {
            this.onImageSuccess(binding.image1);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_2 && resultCode == RESULT_OK) {
            this.onImageSuccess(binding.image2);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_3 && resultCode == RESULT_OK) {
            this.onImageSuccess(binding.image3);
        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                binding.addressInput.setText(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Toast.makeText(getActivity(), R.string.error_place_not_found, Toast.LENGTH_SHORT).show();
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void onImageSuccess(ImageView imageView) {
        Glide.with(this)
                .load(currentPhotoFile)
                .centerCrop()
                .into(imageView);
        attachmentPathList.add(currentPhotoFile.getAbsolutePath());
        currentPhotoFile = null;
    }

    private void onImageFailure() {
        if (currentPhotoFile != null) {
            currentPhotoFile.delete();
            currentPhotoFile = null;
        }
    }

    @Override
    public void showChoosePlaceScreen() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getActivity(), getString(R.string.error_google_play_services), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.dropView();
    }

    @NonNull
    @Override
    public String getTicketType() {
        return ((String) binding.ticketTypeSpinner.getSelectedItem());
    }

    @NonNull
    @Override
    public List<String> getAttachmentList() {
        return attachmentPathList;
    }

    @Override
    public String getAddress() {
        return binding.addressInput.getText().toString();
    }

    @NonNull
    @Override
    public String getDescription() {
        return binding.descriptionInput.getText().toString();
    }

    @Override
    public void showDescriptionRequiredError() {
        binding.descriptionInputLayout.setError(getString(R.string.error_message_required));
    }

    @Override
    public void clearDescriptionErrors() {
        binding.descriptionInputLayout.setError(null);
    }

    @Override
    public void onEmailSent() {
        binding.sendTicket.postDelayed(() -> {
            binding.sendTicket.setText(R.string.action_done);
            binding.sendTicket.setOnClickListener(v -> getActivity().finish());
        }, 1000);
    }
}
