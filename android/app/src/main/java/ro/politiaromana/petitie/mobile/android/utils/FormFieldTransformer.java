package ro.politiaromana.petitie.mobile.android.utils;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class FormFieldTransformer implements Observable.Transformer<CharSequence, String> {

    @Override
    public Observable<String> call(Observable<CharSequence> textChanges) {
        return textChanges
                .sample(100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .debounce(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .onBackpressureLatest()
                .map(CharSequence::toString);
    }
}
