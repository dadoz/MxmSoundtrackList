package com.application.mxm.soundtracks.data;

import com.application.mxm.soundtracks.data.local.Local;
import com.application.mxm.soundtracks.data.local.TrackLocalDataSource;
import com.application.mxm.soundtracks.data.remote.Remote;
import com.application.mxm.soundtracks.data.remote.TrackNetworkDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TracksRepositoryModule {

    @Provides
    @Singleton
    @Local
    TrackDataSource provideStargazerLocalDataSource() {
        return new TrackLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    TrackDataSource provideStargazerRemoteDataSource() {
        return new TrackNetworkDataSource();
    }
}
