package com.application.mxm.soundtracks.data;


import android.content.Context;

import com.application.mxm.soundtracks.data.model.Lyric;

import io.reactivex.Observable;

public interface LyricsDataSource {
//    Observable<List<Track>> getTracks(String owner, String repo);
    Observable<Lyric> getLyrics(Context context, String owner, String repo);
    void setLyrics(Lyric lyrics);
    boolean hasLyrics();
}
