package ro.politiaromana.petitie.mobile.android.petition;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.politiaromana.petitie.mobile.android.R;


public class PetitionFragment extends Fragment {


    public PetitionFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_petition, container, false);
    }
}
