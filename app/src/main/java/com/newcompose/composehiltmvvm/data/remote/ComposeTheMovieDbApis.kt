package com.newcompose.composehiltmvvm.data.remote

import com.newcompose.composehiltmvvm.data.model.MovieDetailResponse
import com.newcompose.composehiltmvvm.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComposeTheMovieDbApis {

    @GET("discover/movie")
    suspend fun fetchMovieList(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("page") page: String,
    ): Response<MovieListResponse>

    @GET("movie/{movieId}")
    suspend fun fetMovieDetail(
        @Path(value = "movieId", encoded = true) movieId: String,
    ): Response<MovieDetailResponse>

}