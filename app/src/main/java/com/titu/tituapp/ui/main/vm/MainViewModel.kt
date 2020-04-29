package com.titu.tituapp.ui.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _badMovies: MutableList<String> by lazy {
        mutableListOf(
            "Titanic",
            "Liceenii",
            "Terminator"
        )
    }
    val badMovies: MutableLiveData<List<String>> = MutableLiveData(_badMovies)

    private val _goodMovies: MutableList<String> by lazy {
        mutableListOf(
            "Titanic - Part2",
            "Liceenii - Reluare",
            "Terminator - But Skynet won"
        )
    }
    val goodMovies: MutableLiveData<List<String>> = MutableLiveData(_goodMovies)

    fun downrateMovie(position: Int) {
        val movie = _goodMovies.removeAt(position)
        _badMovies.add(movie)

        badMovies.postValue(_badMovies)
        goodMovies.postValue(_goodMovies)
    }

    fun uprateMovie(position: Int) {
        val movie = _badMovies.removeAt(position)
        _goodMovies.add(movie)

        badMovies.postValue(_badMovies)
        goodMovies.postValue(_goodMovies)
    }
}
