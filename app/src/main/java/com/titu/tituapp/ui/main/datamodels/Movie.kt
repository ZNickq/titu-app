package com.titu.tituapp.ui.main.datamodels

// Data Model for TMDB Movie item
data class Movie(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val poster_path: String
)

// Data Model for the Response returned from the TMDB Api
data class MovieResponse(
    val results: List<Movie>
)