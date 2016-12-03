package ro.politiaromana.petitie.mobile.android.util;


import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

public final class RxUi {

    private RxUi() {}

    public static Observable<String> formField(@NonNull TextView textView) {
        return RxTextView.textChanges(textView)
                .compose(new FormFieldTransformer());
    }

    public static Observable.Transformer<String, String> required(@NonNull Action0 actionOk,
                                                                  @NonNull Action0 actionNull) {
        return in -> in.map(text -> {
            if (JavaUtils.isEmpty(text)) {
                actionNull.call();
                return null;
            }

            actionOk.call();
            return text;
        });
    }

    public static Observable.Transformer<String, String> validate(@NonNull Func1<String, Boolean> predicate,
                                                                  @NonNull Action0 actionOk,
                                                                  @NonNull Action0 actionNull,
                                                                  @NonNull Action0 actionInvalid) {
        return in -> in.map(text -> {
            if (JavaUtils.isEmpty(text)) {
                actionNull.call();
                return null;
            }

            if (!predicate.call(text)) {
                actionInvalid.call();
                return null;
            }

            actionOk.call();
            return text;
        });
    }
}
