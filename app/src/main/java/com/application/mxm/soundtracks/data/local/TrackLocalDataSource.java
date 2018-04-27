package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.model.TrackMap;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    private final Realm realm;

    @Inject
    public TrackLocalDataSource(Realm realm) {
        this.realm = realm;
    }
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
        String paramKey = Utils.getTrackParamsKey(page, pageSize, country, fHasLyrics);
        return query(paramKey).<TrackMap>asFlowable().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map(TrackMap::getTrackList);
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        TrackMap cache = query(paramsKey);
        return cache != null &&
                cache.getTrackList() != null &&
                cache.getTrackList().size() != 0;
    }

    @Override
    public void setTracks(List<Track> trackList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        realm.executeTransaction(realm1 -> {
                    TrackMap trackMap = realm1.createObject(TrackMap.class, paramsKey);
                    trackMap.getTrackList().addAll(realm1.copyToRealmOrUpdate(trackList));
                });
    }

    /**
     * query to handle tracks items
     * @param paramsKey
     * @return
     */
    private TrackMap query(String paramsKey) {
        return Realm.getDefaultInstance().where(TrackMap.class).equalTo("trackParamsKey", paramsKey)
                .findFirst();
    }
}
