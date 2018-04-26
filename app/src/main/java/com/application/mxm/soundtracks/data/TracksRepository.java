package com.application.mxm.soundtracks.data;

import android.content.Context;

import com.application.mxm.soundtracks.BuildConfig;
import com.application.mxm.soundtracks.data.local.Local;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.Remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

public class TracksRepository {

    private final TrackDataSource localDataSource;
    private final TrackDataSource networkDataSource;
    private final Context context;

    @Inject
    TracksRepository(Context context, @Local TrackDataSource localDataSource, @Remote TrackDataSource networkDataSource) {
        this.localDataSource = localDataSource;
        this.networkDataSource = networkDataSource;
        this.context = context;
    }

    /**
     * get cached or network data
     * @return
     */
    public Observable<List<Track>> getTracks(String page, String pageSize, String country, String fHasLyrics) {
        if (localDataSource.hasTracks()) {
            //show data from cache
            return localDataSource.getTracks(page, pageSize, country, fHasLyrics, BuildConfig.API_KEY);
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource.getTracks(page, pageSize, country, fHasLyrics, BuildConfig.API_KEY)
                .doOnNext(localDataSource::setTracks);
    }

    public void refreshCache() {
        //TODO implement it
    }
}
