package ro.politiaromana.petitie.mobile.android.utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

public final class RxUi {

    public static Observable<String> formField(@NonNull TextView textView) {
        return formField(textView, true);
    }

    public static Observable<String> formField(@NonNull TextView textView, boolean skipDefault) {
        final Observable<String> formObservable = RxTextView.textChanges(textView)
                .compose(new FormFieldTransformer());

        if (!skipDefault) {
            return formObservable;
        }

        // We want to pass along only a non-empty default value
        if (JavaUtils.isEmpty(textView.getText())) {
            return formObservable.skip(1);
        } else {
            return formObservable;
        }
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
        return validate(predicate, null, actionOk, actionNull, actionInvalid);
    }

    public static Observable.Transformer<String, String> validate(@NonNull Func1<String, Boolean> predicate,
                                                                  @Nullable String invalidDefault,
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
                return invalidDefault;
            }

            actionOk.call();
            return text;
        });
    }

    private RxUi() {}
}
