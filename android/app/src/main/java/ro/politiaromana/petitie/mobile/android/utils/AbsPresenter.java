package ro.politiaromana.petitie.mobile.android.utils;


import android.support.annotation.NonNull;

public interface AbsPresenter<View> {

    void takeView(@NonNull View view);

    void dropView();
}
