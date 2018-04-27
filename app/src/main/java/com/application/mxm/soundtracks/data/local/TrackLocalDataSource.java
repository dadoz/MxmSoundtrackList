package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.ArrayList;
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
        String paramKey = Utils.getTrackParamsKey(page, pageSize, country, fHasLyrics);
        return Observable.just(map.get(paramKey));
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return map.get(paramsKey) != null &&
                map.get(paramsKey).size() != 0;
    }

    @Override
    public void setTracks(List trackList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        //set all items as new
        ArrayList<Track> list = new ArrayList<>();
        list.addAll(trackList);
        map.put(paramsKey, list);

        Log.i(getClass().getName(), map.get("1_2_it_0").size() + "------------------");
        if (map.get("2_2_it_0") != null)
            Log.i(getClass().getName(), map.get("2_2_it_0").size() + "------------------");
    }
}
