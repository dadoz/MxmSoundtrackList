package com.application.mxm.soundtracks.data;


import com.application.mxm.soundtracks.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
    Observable<List<Track>> getTracks(String page, String pageSize, String country, String fHasLyrics, String apiKey);
//    Observable<List<Track>> getTracks(Context context, String owner, String repo);
    void setTracks(List<Track> stargazers);
    boolean hasTracks();
}
