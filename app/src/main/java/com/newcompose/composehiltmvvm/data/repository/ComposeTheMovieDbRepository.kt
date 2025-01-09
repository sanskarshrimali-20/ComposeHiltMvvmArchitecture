package com.newcompose.composehiltmvvm.data.repository

import com.newcompose.composehiltmvvm.data.remote.ComposeTheMovieDbApis
import com.newcompose.composehiltmvvm.util.safeApiCall
import javax.inject.Inject

class ComposeTheMovieDbRepository @Inject constructor(
    private val composeTheMovieDbApis: ComposeTheMovieDbApis
) {

    suspend fun fetchMovieList(
        page: String
    ) = safeApiCall {
        composeTheMovieDbApis.fetchMovieList(
            page = page
        )
    }

    suspend fun fetMovieDetail(
        movieId: String
    ) = safeApiCall {
        composeTheMovieDbApis.fetMovieDetail(
            movieId = movieId
        )
    }

}