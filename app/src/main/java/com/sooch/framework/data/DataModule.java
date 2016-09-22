package com.sooch.framework.data;

import com.sooch.framework.data.repository.UserRepoImpl;
import com.sooch.framework.domain.repository.UserRepos;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Takashi Sou on 2016/09/22.
 */
@Module
public class DataModule {
    @Singleton
    @Provides
    public UserRepos provideUserRepository() {
        return new UserRepoImpl();
    }
}
