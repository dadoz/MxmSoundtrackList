package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    private HashMap<String, List<Track>> map = new HashMap<>();

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
        return Observable.just(map.get(Utils.getTrackParamsKey(page, pageSize, country, fHasLyrics)));
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return map.get(paramsKey) != null &&
                map.get(paramsKey).size() != 0;
    }

    @Override
    public void setTracks(List stargazerList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        //set all items as new
        map.put(paramsKey, stargazerList);
    }
}
