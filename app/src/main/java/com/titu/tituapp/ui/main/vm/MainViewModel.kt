package com.titu.tituapp.ui.main.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.titu.tituapp.ui.main.datamodels.Movie
import com.titu.tituapp.ui.main.datamodels.MovieDatabase
import com.titu.tituapp.ui.main.networking.ApiFactory
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    // Encapsulate access to mutable LiveData using backing property
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val api = ApiFactory().tmdbApi


    private suspend fun refreshPopularMovies(context: Context) : List<Movie>?{

        //safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val movieResponse = safeApiCall(
            call = {api.getPopularMoviesAsync().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        val res = movieResponse?.results

        MovieDatabase.getInstance(context).movieDao().insertAll(res)

        return movieResponse?.results

    }


    fun loadData(context: Context){

        scope.launch {

            _movies.postValue(MovieDatabase.getInstance(context).movieDao().getAll())

            refreshPopularMovies(context)

            _movies.postValue(MovieDatabase.getInstance(context).movieDao().getAll())
        }
    }
}