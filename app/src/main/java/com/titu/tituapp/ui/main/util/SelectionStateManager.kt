package com.titu.tituapp.ui.main.util

import com.titu.tituapp.ui.main.datamodels.Movie

// This shouldn't exist, bad code, but adding navigation data takes too much time and this works just as well
class SelectionStateManager {

    companion object {
        val instance = SelectionStateManager()
    }

    var selectedMovie: Movie? = null

}