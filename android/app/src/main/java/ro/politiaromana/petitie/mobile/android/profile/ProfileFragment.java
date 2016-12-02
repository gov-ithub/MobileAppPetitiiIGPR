package ro.politiaromana.petitie.mobile.android.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.politiaromana.petitie.mobile.android.R;
import ro.politiaromana.petitie.mobile.android.databinding.FragmentProfileBinding;
import ro.politiaromana.petitie.mobile.android.util.RxUi;
import rx.Observable;


public class ProfileFragment extends Fragment implements ProfileContract.View {

    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        return binding.getRoot();
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
        return null;
    }

    @Override
    public Observable<String> getPhoneObservable() {
        return RxUi.formField(binding.phoneInput);
    }

    @Override
    public void showPhoneInvalidError() {
        binding.phoneInputLayout.setError(getString(R.string.error_phone_is_invalid));
    }

    @Override
    public void clearPhoneErrors() {
        binding.phoneInputLayout.setError(null);
    }
    //endregion
}
