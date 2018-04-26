package com.application.mxm.soundtracks;

import com.application.mxm.soundtracks.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class StargazersApplication extends DaggerApplication {
    private String owner;
    private String repo;

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return getComponent();
    }

    /**
     * get componente base builder
     * @return
     */
    public AndroidInjector<? extends DaggerApplication> getComponent() {
        return DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
