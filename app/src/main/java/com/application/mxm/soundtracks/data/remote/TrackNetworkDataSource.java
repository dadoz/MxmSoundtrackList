package com.application.mxm.soundtracks.data.remote;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class TrackNetworkDataSource extends RetrofitDataSourceBase implements TrackDataSource {

//    public Observable<List<Track>> getTracks(Context context, String owner, String repo) {
//        try {
//            InputStream inputStream = context.getAssets().open("sound_track_response_200.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            String json = new String(buffer, "UTF-8");
//
//            List<Track> res = new GsonBuilder()
//                    .registerTypeAdapter(new TypeToken<List<Track>>() {}.getType(), new RetrofitServiceRx.TrackJsonDeserializer())
//                    .registerTypeAdapter(Lyric.class, new RetrofitServiceRx.LyricsJsonDeserializer())
//                    .create()
//                    .fromJson(json, new TypeToken<List<Track>>() {}.getType());
//            return Observable.just(res);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new UnsupportedOperationException("error on get data");
//        }
//    }

    /**
     *
     * @param page
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @param apiKey
     * @return
     */
    public Observable<List<Track>> getTracks(String page, String pageSize, String country, String fHasLyrics, String apiKey) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getTracks(page, pageSize, country, fHasLyrics, apiKey)
                .compose(handleRxErrorsTransformer());
    }

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
