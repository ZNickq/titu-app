package com.titu.tituapp.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.titu.tituapp.ui.main.datamodels.Movie
import com.titu.tituapp.ui.main.networking.ApiFactory
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    // Encapsulate access to mutable LiveData using backing property
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val api = ApiFactory().tmdbApi


    private suspend fun getPopularMovies() : List<Movie>?{

        //safeApiCall is defined in BaseRepository.kt (https://gist.github.com/navi25/67176730f5595b3f1fb5095062a92f15)
        val movieResponse = safeApiCall(
            call = {api.getPopularMoviesAsync().await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return movieResponse?.results

    }


    fun loadData(){

        scope.launch {
            val popularMovies = getPopularMovies()

            _movies.postValue(popularMovies)
        }
    }
}