package com.sooch.framework.data.network;

import com.sooch.framework.data.entity.Repo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Retrofit example
 * Created by Takashi Sou on 2016/07/20.
 */
public interface GitHubService {

    /**
     * RxJavaバージョン 戻り値をObservableでラップする
     * @param user
     * @return
     */
    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}