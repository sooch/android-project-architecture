package com.sooch.framework.data.repository;

import com.sooch.framework.data.network.GitHubService;
import com.sooch.framework.domain.repository.UserRepos;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Example code.
 * Created by Takashi Sou on 2016/08/08.
 */
public class UserRepoImpl implements UserRepos {

    @Override
    public Observable repos(String user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        return service.listRepos("octocat");
    }
}
