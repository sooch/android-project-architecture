package com.sooch.framework.domain.interactor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Takashi Sou on 2016/08/01.
 */
public abstract class AbstractUseCase {

    protected Subscription subscription = Subscriptions.empty();

    protected AbstractUseCase() {
    }

    protected abstract Observable buildUseCaseObservable();

    public void execute(Subscriber subscriber) {
        this.subscription = buildUseCaseObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
