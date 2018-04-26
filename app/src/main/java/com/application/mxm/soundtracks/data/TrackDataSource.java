package com.application.mxm.soundtracks.data;


import android.content.Context;

import com.application.mxm.soundtracks.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
//    Observable<List<Track>> getTracks(String owner, String repo);
    Observable<List<Track>> getTracks(Context context, String owner, String repo);
    void setTracks(List<Track> stargazers);
    boolean hasTracks();
}
