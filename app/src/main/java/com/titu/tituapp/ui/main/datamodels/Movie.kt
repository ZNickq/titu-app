package com.titu.tituapp.ui.main.datamodels

import androidx.room.*

// Data Model for TMDB Movie item
@Entity
data class Movie(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "vote_average")
    val vote_average: Double,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,


    @ColumnInfo(name = "posterPath")
    val poster_path: String
)