package com.application.mxm.soundtracks.data.local;


import android.content.Context;

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

    public Observable<List<Track>> getTracks(Context context, String owner, String repo) {
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
