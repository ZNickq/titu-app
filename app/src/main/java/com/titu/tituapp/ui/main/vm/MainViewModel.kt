package com.titu.tituapp.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Movie(val name: String)

class MainViewModel : ViewModel() {

    // Encapsulate access to mutable LiveData using backing property
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies


    fun loadData(){
        _movies.value = arrayListOf(Movie("Titanic 1"), Movie("Titanic 2"))
    }
}