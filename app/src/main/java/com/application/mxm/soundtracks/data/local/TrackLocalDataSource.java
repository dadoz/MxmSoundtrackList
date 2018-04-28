package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.model.TrackMap;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
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
        return getTrackMap(paramKey)
                .map(trackMap -> {
                    try(Realm realm = Realm.getDefaultInstance()) {
                        //detach from realm -> since there's a problem with adding item in same list
                        return realm.copyFromRealm(trackMap.getTrackList());
                    }
                });
    }

    /**
     * has track check
     * @param paramsKey
     * @return
     */
    @Override
    public boolean hasTracks(String paramsKey) {
        try (Realm realm = Realm.getDefaultInstance()) {
            TrackMap cache = realm.where(TrackMap.class).equalTo("trackParamsKey", paramsKey)
                    .findFirst();
            return cache != null &&
                    cache.getTrackList() != null &&
                    cache.getTrackList().size() != 0;
        }
    }

    /**
     * set track on storage - realmio
     * @param trackList
     * @param paramsKey
     */
    @Override
    public void setTracks(List<Track> trackList, String paramsKey, String paramsKeyPrev) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        try(Realm r = Realm.getDefaultInstance()) {
            r.executeTransaction(realm -> {
                //create track map
                TrackMap trackMap = realm.createObject(TrackMap.class, paramsKey);
                //adding new items
                trackMap.getTrackList().addAll(realm.copyToRealmOrUpdate(trackList));
            });
        }
    }

    /**
     * get tracks from storage
     * @param paramsKey
     * @return
     */
    public Observable<TrackMap> getTrackMap(String paramsKey) {
        return Observable.just(Realm.getDefaultInstance())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(realm1 -> realm1.where(TrackMap.class)
                        .equalTo("trackParamsKey", paramsKey)
                        .findFirst()
                        .<TrackMap>asFlowable()
                        .toObservable());
    }

}
