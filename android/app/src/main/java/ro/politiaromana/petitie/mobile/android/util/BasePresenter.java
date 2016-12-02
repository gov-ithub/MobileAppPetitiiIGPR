package ro.politiaromana.petitie.mobile.android.util;


import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> implements AbsPresenter<V> {

    protected V view;
    protected CompositeSubscription lifecycleSubscription = new CompositeSubscription();

    @Override
    public void takeView(@NonNull V view) {
        if (view == null) {
            throw new NullPointerException("new view must not be null");
        }
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
        this.lifecycleSubscription.clear();
    }
}
