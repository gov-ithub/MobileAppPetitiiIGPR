package ro.politiaromana.petitie.mobile.android.util;


import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;

public final class RxUi {

    private RxUi() {}

    public static Observable<String> formField(@NonNull TextView textView) {
        return RxTextView.textChanges(textView)
                .compose(new FormFieldTransformer());
    }
}
