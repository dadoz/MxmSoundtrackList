package com.application.mxm.soundtracks.data;

import android.content.Context;

import com.application.mxm.soundtracks.BuildConfig;
import com.application.mxm.soundtracks.data.local.Local;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.remote.Remote;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

public class LyricsRepository {

    private final LyricsDataSource localDataSource;
    private final LyricsDataSource networkDataSource;
    private final Context context;

    @Inject
    LyricsRepository(Context context, @Local LyricsDataSource localDataSource, @Remote LyricsDataSource networkDataSource) {
        this.localDataSource = localDataSource;
        this.networkDataSource = networkDataSource;
        this.context = context;
    }

    /**
     * get cached or network data
     * @return
     */
    public Observable<Lyric> getLyrics(String trackId) {
        if (localDataSource.hasLyrics()) {
            //show data from cache
            return localDataSource.getLyrics(trackId, BuildConfig.API_KEY);
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource.getLyrics(trackId, BuildConfig.API_KEY)
                .doOnNext(localDataSource::setLyrics);
    }

    public void refreshCache() {
        //TODO implement it
    }
}
