package com.sooch.framework.domain.repository;

import com.sooch.framework.data.entity.Repo;

import java.util.List;

import rx.Observable;

/**
 * Example code.
 * Created by Takashi Sou on 2016/08/08.
 */
public interface UserRepos {

    /**
     * GitHubのリポジトリ一覧を取得する
     * @param user 取得するユーザ名
     * @return List<Repo>をObservableでラップして返却
     */
    Observable<List<Repo>> repos(String user);
}
