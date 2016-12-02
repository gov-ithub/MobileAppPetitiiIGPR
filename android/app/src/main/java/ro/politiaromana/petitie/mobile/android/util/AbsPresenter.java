package ro.politiaromana.petitie.mobile.android.util;


import android.support.annotation.NonNull;

public interface AbsPresenter<View> {

    void takeView(@NonNull View view);

    void dropView();
}
