package com.titu.tituapp.ui.main.networking

import com.titu.tituapp.ui.main.datamodels.Movie
import com.titu.tituapp.ui.main.datamodels.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TmdbApi{

    @GET("movie/popular")
    fun getPopularMoviesAsync() : Deferred<Response<MovieResponse>>

    @GET("movie/{id}")
    fun getMovieByIdAsync(@Path("id") id:Int): Deferred<Response<Movie>>

}