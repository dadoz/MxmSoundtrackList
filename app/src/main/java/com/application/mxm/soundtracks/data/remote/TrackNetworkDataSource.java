package com.application.mxm.soundtracks.data.remote;

import android.content.Context;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class TrackNetworkDataSource extends RetrofitDataSourceBase implements TrackDataSource {
    /**
     *
     * @param owner
     * @param repo
     * @return
     */
    public Observable<List<Track>> getTracks(Context context, String owner, String repo) {
        try {
            InputStream inputStream = context.getAssets().open("sound_track_response_200.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            List<Track> res = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<List<Track>>() {}.getType(), new RetrofitServiceRx.TrackJsonDeserializer())
                    .registerTypeAdapter(Lyric.class, new RetrofitServiceRx.LyricsJsonDeserializer())
                    .create()
                    .fromJson(json, new TypeToken<List<Track>>() {}.getType());
            return Observable.just(res);

        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("error on get data");
        }
    }

/*    public Observable<List<Track>> getStargazer(String owner, String repo) {
        return new RetrofitServiceRx().getTrackRetrofit()
                .compose(handleRxErrorsTransformer());
                .getStargazers(owner, repo);
    }
*/
    /**
     * TODO plese refactorize
     * @param stargazers
     */
    @Override
    public void setTracks(List<Track> stargazers) {
    }

    @Override
    public boolean hasTracks() {
        return false;
    }
}
