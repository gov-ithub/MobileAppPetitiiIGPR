package ro.politiaromana.petitie.mobile.android.ticket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentTicketBinding;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.model.RealmString;
import ro.politiaromana.petitie.mobile.android.model.Ticket;
import ro.politiaromana.petitie.mobile.android.util.CameraUtil;

import static android.app.Activity.RESULT_OK;

/**
 * Created by andrei.
 */

public class TicketFragment extends Fragment implements TicketContract.View {

    private static final int REQUEST_IMAGE_CAPTURE_1 = 1;
    private static final int REQUEST_IMAGE_CAPTURE_2 = 2;
    private static final int REQUEST_IMAGE_CAPTURE_3 = 3;

    private static final String KEY_PROFILE = "key_profile";

    private FragmentTicketBinding binding;
    private TicketContract.Presenter presenter;
    private CharSequence[] ticketTypeArray;
    private List<String> attachmentPathList;

    private File mCurrentPhotoFile;

    public static TicketFragment newInstance(Profile profile) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_PROFILE,profile);
        TicketFragment fragment = new TicketFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

        binding.sendTicket.setOnClickListener(v -> {
            presenter.onSendButtonClicked();
        });

        Profile profile = new Profile();

        if(getArguments() != null){
            profile = ((Profile) getArguments().getSerializable(KEY_PROFILE));
        }

        this.presenter = new TicketPresenter(profile);
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

    @Override public String getType() {
        return ((String) binding.ticketTypeSpinner.getSelectedItem());
    }

    @Override public List<String> getAttachmentList() {
        return attachmentPathList;
    }

    @Override public String getAddress() {
        return binding.addressInput.getText().toString();
    }

    @Override public String getDescription() {
        return binding.descriptionInput.getText().toString();
    }

    @Override public void showDescriptionError() {
        binding.descriptionInputLayout.setError(getString(R.string.error_description_required));
    }

    @Override public void clearDescriptionError(){
        binding.descriptionInputLayout.setError(null);
    }

    @Override public void showEmailClient(Profile profile, Ticket ticket) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"abc@xyz.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ticket.typeStringValue);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prenume: ").append(profile.firstName).append("\n");
        stringBuilder.append("Nume: ").append(profile.lastName).append("\n");
        stringBuilder.append("Email: ").append(profile.email).append("\n");
        stringBuilder.append("CNP: ").append(profile.cnp).append("\n");
        stringBuilder.append("Adresa domiciliu: ").append(profile.address).append("\n");
        stringBuilder.append("Judet: ").append(profile.county).append("\n");
        stringBuilder.append("Telefon: ").append(profile.phone).append("\n\n");
        stringBuilder.append("Locatie petitie: ").append(ticket.address).append("\n");
        stringBuilder.append("Mesaj: ").append(ticket.description);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, stringBuilder.toString());

        ArrayList<Uri> uriList = new ArrayList<>();
        for (RealmString path : ticket.attachmentPathList) {
            File file = new File(path.val);
            uriList.add(Uri.fromFile(file));
        }
        emailIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);

        startActivity(Intent.createChooser(emailIntent, getString(R.string.chooser_title)));
    }
}
