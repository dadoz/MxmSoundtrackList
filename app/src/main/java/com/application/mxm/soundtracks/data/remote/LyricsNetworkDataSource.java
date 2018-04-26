package com.application.mxm.soundtracks.data.remote;

import android.content.Context;

import com.application.mxm.soundtracks.data.LyricsDataSource;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class LyricsNetworkDataSource implements LyricsDataSource {
    /**
     *
     * @param owner
     * @param repo
     * @return
     */
    public Observable<Lyric> getLyrics(Context context, String owner, String repo) {
        try {
            InputStream inputStream = context.getAssets().open("sound_track_lyrics_response_200.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            Lyric res = new GsonBuilder()
                    .registerTypeAdapter(Lyric.class, new RetrofitServiceRx.LyricsJsonDeserializer())
                    .create()
                    .fromJson(json, Lyric.class);
            return Observable.just(res);

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("error on get data");
        }
    }

/*    public Observable<List<Track>> getStargazer(String owner, String repo) {
        return new RetrofitServiceRx().getTrackRetrofit()
                .getStargazers(owner, repo);
    }
*/
    /**
     * TODO plese refactorize
     */
    @Override
    public void setLyrics(Lyric lyric) {
    }

    @Override
    public boolean hasLyrics() {
        return false;
    }
}
