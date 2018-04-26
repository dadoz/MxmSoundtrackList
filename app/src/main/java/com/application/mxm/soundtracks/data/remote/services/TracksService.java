package com.application.mxm.soundtracks.data.remote.services;


import com.application.mxm.soundtracks.data.model.Track;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TracksService {
    @GET("repos/{owner}/{repo}/stargazers")
    Observable<List<Track>> getStargazers(@Path("owner") String owner, @Path("repo") String repo);
}
