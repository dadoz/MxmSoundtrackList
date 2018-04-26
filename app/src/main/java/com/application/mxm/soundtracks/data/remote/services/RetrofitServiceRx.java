package com.application.mxm.soundtracks.data.remote.services;


import com.application.mxm.soundtracks.BuildConfig;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.model.Track;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceRx {
    /**
     * get service
     * @return
     */
    public TracksService getTrackRetrofit() {
        try {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.MXM_BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build()
                    .create(TracksService.class);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    /**
     * track json to a list of track item
     */
    public static class TrackJsonDeserializer implements JsonDeserializer<List<Track>> {
        @Override
        public List<Track> deserialize(JsonElement json, Type typeOfT,
                                 JsonDeserializationContext context) throws JsonParseException {
            return new Gson().fromJson(json.getAsJsonObject().get("message").getAsJsonObject().get("body").getAsJsonObject()
                    .get("track_list").getAsJsonArray(), typeOfT);
        }
    }
    /**
     * track json to a list of track item
     */
    public static class LyricsJsonDeserializer implements JsonDeserializer<Lyric> {
        @Override
        public Lyric deserialize(JsonElement json, Type typeOfT,
                                 JsonDeserializationContext context) throws JsonParseException {
            return new Gson().fromJson(json.getAsJsonObject().get("message").getAsJsonObject().get("body")
                    .getAsJsonObject().get("lyrics").getAsJsonObject(), typeOfT);
        }
    }
}
