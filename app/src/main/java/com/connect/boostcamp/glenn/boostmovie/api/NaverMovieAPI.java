package com.connect.boostcamp.glenn.boostmovie.api;

import com.connect.boostcamp.glenn.boostmovie.model.MovieListInfo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NaverMovieAPI {
    @Headers({
            "X-Naver-Client-Id: XjV5oQ0EIZ9nV7chrJxS",
            "X-Naver-Client-Secret: sOs9cxUiVZ"
    })
    @GET("v1/search/movie.json")
    Call<MovieListInfo> listRepos(@QueryMap Map<String, String> options);
}