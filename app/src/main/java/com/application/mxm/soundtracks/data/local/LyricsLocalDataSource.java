package com.application.mxm.soundtracks.data.local;

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

    @Override
    public boolean hasLyrics() {
        return lyric != null;
    }

    /**
     *
     * @param trackId
     * @param apiKey
     * @return
     */
    @Override
    public Observable<Lyric> getLyrics(String trackId, String apiKey) {
        return Observable.just(lyric);
    }

    public void setLyrics(Lyric lyric) {
        this.lyric = lyric;
    }
}
