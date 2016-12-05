package ro.politiaromana.petitie.mobile.android.petition;


import ro.politiaromana.petitie.mobile.android.utils.AbsPresenter;

public interface PetitionContract {

    interface View {

        boolean goBackOneStep();

    }

    interface Presenter extends AbsPresenter<View> {

    }
}
