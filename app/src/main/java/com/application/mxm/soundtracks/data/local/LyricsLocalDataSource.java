package com.application.mxm.soundtracks.data.local;

import android.content.Context;

import com.application.mxm.soundtracks.data.LyricsDataSource;
import com.application.mxm.soundtracks.data.model.Lyric;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * In Ram memory storage
 */
@Singleton
public class LyricsLocalDataSource implements LyricsDataSource {
    private Lyric lyric;

    public Observable<Lyric> getLyrics(Context context, String owner, String repo) {
        return Observable.just(lyric);
    }

    @Override
    public boolean hasLyrics() {
        return lyric != null;
    }

    public void setLyrics(Lyric lyric) {
        this.lyric = lyric;
    }
}
