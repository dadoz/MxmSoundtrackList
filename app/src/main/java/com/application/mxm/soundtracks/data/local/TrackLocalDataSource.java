package com.application.mxm.soundtracks.data.local;


import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    private List list = new ArrayList();

    /**
     *
     * @param page
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @param apiKey
     * @return
     */
    @Override
    public Observable<List<Track>> getTracks(String page, String pageSize, String country, String fHasLyrics, String apiKey) {
        return Observable.just(list);
    }

    @Override
    public boolean hasTracks() {
        return list.size() != 0;
    }

    public void setTracks(List stargazerList) {
        list.addAll(stargazerList);
    }
}
