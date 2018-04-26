package com.application.mxm.soundtracks.data;

import android.content.Context;

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
    public Observable<List<Track>> getStargazer(String owner, String repo) {
        if (localDataSource.hasTracks()) {
            //show data from cache
            return localDataSource.getTracks(context, owner, repo);
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource.getTracks(context, owner, repo)
                .doOnNext(localDataSource::setTracks);
    }

    public void refreshStargazer() {
        //TODO implement it
    }

    public void refreshCache() {
        //TODO implement it
    }

}
