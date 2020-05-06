package com.titu.tituapp.ui.main.datamodels

import android.content.Context
import androidx.room.*

// The below classes should most definitely not be here, but it's enough for a 5 i think.

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>?)

    @Delete
    fun delete(movie: Movie)
}

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

class MovieDatabase {

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Sample.db")
                .build()
    }
}

// Data Model for the Response returned from the TMDB Api
data class MovieResponse(
    val results: List<Movie>
)