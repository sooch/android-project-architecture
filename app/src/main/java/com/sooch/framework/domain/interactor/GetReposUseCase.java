package com.sooch.framework.domain.interactor;

import com.sooch.framework.data.entity.Repo;
import com.sooch.framework.data.network.HttpRetryPolicy;
import com.sooch.framework.domain.repository.UserRepos;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Example code
 * Created by Takashi Sou on 2016/08/08.
 */
public class GetReposUseCase extends AbstractUseCase {

    private String user;
    private final UserRepos userRepos;

    /**
     * コンストラクタ
     * @param user 取得するユーザ名
     * @param userRepos 取得処理を行うモジュールをDI
     */
    @Inject
    public GetReposUseCase(String user, UserRepos userRepos) {
        this.user = user;
        this.userRepos = userRepos;
    }

    @Override
    protected Observable<List<Repo>> buildUseCaseObservable() {
        return this.userRepos.repos(user);
    }

    @Override
    public void execute(Subscriber subscriber) {
        subscription = buildUseCaseObservable()
                .subscribeOn(Schedulers.newThread())
                .flatMap(repos -> Observable.from(repos))
                .retryWhen(new HttpRetryPolicy(2, 500))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
