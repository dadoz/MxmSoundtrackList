package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.model.TrackMap;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
                .flatMap(trackMap -> Observable.just(trackMap.getTrackList()));
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
    public void setTracks(List<Track> trackList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        Single.create((SingleOnSubscribe<Void>) singleSubscriber -> {
            try(Realm r = Realm.getDefaultInstance()) { // <-- auto-close
                r.executeTransaction(realm -> {
                    realm.createObject(TrackMap.class, paramsKey)
                            .getTrackList().addAll(realm.copyToRealmOrUpdate(trackList));
                });
            }})
                .subscribeOn(Schedulers.io())
                .subscribe();
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
