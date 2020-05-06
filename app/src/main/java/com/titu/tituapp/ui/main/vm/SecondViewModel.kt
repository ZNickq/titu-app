package com.titu.tituapp.ui.main.vm

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.titu.tituapp.ui.main.datamodels.Movie
import com.titu.tituapp.ui.main.util.SelectionStateManager

class SecondViewModel(val sharedPrefs: SharedPreferences) : BaseViewModel() {

    val selectedMovie: Movie = SelectionStateManager.instance.selectedMovie!!  // Again, bad code, but does the trick to show data binding

    val currentStatus: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        update()
    }

    fun update() {
        val a = sharedPrefs.getInt("movie-${selectedMovie.id}", 0)
        if (a == 0) {
            currentStatus.postValue("none")
        }
        if (a == 1) {
            currentStatus.postValue("like")
        }
        if (a == -1) {
            currentStatus.postValue("dislike")
        }
    }

    fun onLike() {
        with (sharedPrefs.edit()) {
            putInt("movie-${selectedMovie.id}", 1)
            commit()

            update()
        }
    }

    fun onDislike() {
        with (sharedPrefs.edit()) {
            putInt("movie-${selectedMovie.id}", -1)
            commit()

            update()
        }
    }
}
