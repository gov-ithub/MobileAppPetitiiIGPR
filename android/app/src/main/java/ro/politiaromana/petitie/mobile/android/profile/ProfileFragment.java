package ro.politiaromana.petitie.mobile.android.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.Locale;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentProfileBinding;
import ro.politiaromana.petitie.mobile.android.model.Profile;
import ro.politiaromana.petitie.mobile.android.utils.RxUi;
import rx.Observable;

import static ro.politiaromana.petitie.mobile.android.utils.JavaUtils.isNotEmpty;


public class ProfileFragment extends Fragment implements ProfileContract.View {

    private FragmentProfileBinding binding;
    private ProfileContract.Presenter presenter;
    private CharSequence[] counties;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale.setDefault(new Locale("ro", "RO"));
        counties = getContext().getResources().getTextArray(R.array.counties);
    }

    @Override
    public void setPresenter(@NonNull ProfileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setEnableHintAnimation(true);

        ArrayAdapter<CharSequence> countiesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, counties);

        binding.countySpinner.setAdapter(countiesAdapter);
        binding.phoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        presenter.takeView(this);

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void bind(@Nullable Profile profile) {
        if (profile == null) return;

        binding.setEnableHintAnimation(false);
        binding.executePendingBindings();

        if (isNotEmpty(profile.firstName)) {
            binding.firstNameInput.setText(profile.firstName);
        }

        if (isNotEmpty(profile.lastName)) {
            binding.lastNameInput.setText(profile.lastName);
        }

        if (isNotEmpty(profile.email)) {
            binding.emailInput.setText(profile.email);
        }

        if (isNotEmpty(profile.cnp)) {
            binding.cnpInput.setText(profile.cnp);
        }

        if (isNotEmpty(profile.address)) {
            binding.addressInput.setText(profile.address);
        }

        if (isNotEmpty(profile.county)) {
            final String county = profile.county;
            for (int i = 0; i < counties.length; i++) {
                if (county.equalsIgnoreCase(counties[i].toString())) {
                    binding.countySpinner.setSelection(i);
                    break;
                }
            }
        }

        if (isNotEmpty(profile.phone)) {
            binding.phoneInput.setText(profile.phone);
        }

        binding.setEnableHintAnimation(true);
    }

    @Override
    public void showError(Throwable e) {
        // TODO do a better job for showing an error
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.dropView();
    }

    //region Form fields
    @Override
    public Observable<String> getFirstNameObservable() {
        return RxUi.formField(binding.firstNameInput);
    }

    @Override
    public void showFirstNameRequiredError() {
        binding.firstNameInputLayout.setError(getString(R.string.error_first_name_is_required));
    }

    @Override
    public void clearFirstNameErrors() {
        binding.firstNameInputLayout.setError(null);
    }

    @Override
    public Observable<String> getLastNameObservable() {
        return RxUi.formField(binding.lastNameInput);
    }

    @Override
    public void showLastNameRequiredError() {
        binding.lastNameInputLayout.setError(getString(R.string.error_last_name_is_required));
    }

    @Override
    public void clearLastNameErrors() {
        binding.lastNameInputLayout.setError(null);
    }

    @Override
    public Observable<String> getEmailObservable() {
        return RxUi.formField(binding.emailInput);
    }

    @Override
    public void showEmailRequiredError() {
        binding.emailInputLayout.setError(getString(R.string.error_email_is_required));
    }

    @Override
    public void showEmailInvalidError() {
        binding.emailInputLayout.setError(getString(R.string.error_email_is_invalid));
    }

    @Override
    public void clearEmailErrors() {
        binding.emailInputLayout.setError(null);
    }

    @Override
    public Observable<String> getCNPObservable() {
        return RxUi.formField(binding.cnpInput);
    }

    @Override
    public void showCNPRequiredError() {
        binding.cnpInputLayout.setError(getString(R.string.error_cnp_is_required));
    }

    @Override
    public void showCNPInvalidError() {
        binding.cnpInputLayout.setError(getString(R.string.error_cnp_is_invalid));
    }

    @Override
    public void clearCNPErrors() {
        binding.cnpInputLayout.setError(null);
    }

    @Override
    public Observable<String> getAddressObservable() {
        return RxUi.formField(binding.addressInput);
    }

    @Override
    public void showAddressRequiredError() {
        binding.addressInputLayout.setError(getString(R.string.error_address_is_required));
    }

    @Override
    public void clearAddressErrors() {
        binding.addressInputLayout.setError(null);
    }

    @Override
    public Observable<String> getCountyObservable() {
        return RxAdapterView.itemSelections(binding.countySpinner)
                .map(position -> counties[position].toString());
    }

    @Override
    public Observable<String> getPhoneObservable() {
        return RxUi.formField(binding.phoneInput, false);
    }

    @Override
    public void showPhoneInvalidError() {
        binding.phoneInputLayout.setError(getString(R.string.error_phone_is_invalid));
    }

    @Override
    public void clearPhoneErrors() {
        binding.phoneInputLayout.setError(null);
    }

    @Override
    public void setFormStatus(boolean isValid, boolean shouldSave) { }
    //endregion
}
