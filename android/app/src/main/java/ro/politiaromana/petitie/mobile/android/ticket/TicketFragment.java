package ro.politiaromana.petitie.mobile.android.ticket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentTicketBinding;
import ro.politiaromana.petitie.mobile.android.util.CameraUtil;
import ro.politiaromana.petitie.mobile.android.util.RxUi;
import rx.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by andrei.
 */

public class TicketFragment extends Fragment implements TicketContract.View {

    private static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    private static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_3 = 3;

    private FragmentTicketBinding binding;
    private TicketContract.Presenter presenter;
    private CharSequence[] ticketTypeArray;
    private List<String> attachmentPathList;

    private File mCurrentPhotoFile;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ticketTypeArray = getResources().getTextArray(R.array.ticket_type_array);
        this.attachmentPathList = new ArrayList<>();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket, container, false);

        ArrayAdapter<CharSequence> ticketTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ticketTypeArray);

        binding.ticketTypeSpinner.setAdapter(ticketTypeAdapter);

        binding.image1.setOnClickListener(v ->
                mCurrentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketFragment.this,
                REQUEST_IMAGE_CAPTURE_1)
        );
        binding.image2.setOnClickListener(v ->
                mCurrentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketFragment.this,
                REQUEST_IMAGE_CAPTURE_2)
        );
        binding.image3.setOnClickListener(v ->
                mCurrentPhotoFile = CameraUtil.dispatchTakePictureIntent(TicketFragment.this,
                REQUEST_IMAGE_CAPTURE_3)
        );

        this.presenter = new TicketPresenter();
        this.presenter.takeView(this);

        return binding.getRoot();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
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
    }

    private void onImageSuccess(ImageView imageView) {
        Glide.with(this)
                .load(mCurrentPhotoFile)
                .centerCrop()
                .into(imageView);
        attachmentPathList.add(mCurrentPhotoFile.getAbsolutePath());
        mCurrentPhotoFile = null;
    }

    private void onImageFailure(){
        if(mCurrentPhotoFile != null){
            mCurrentPhotoFile.delete();
            mCurrentPhotoFile = null;
        }
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
