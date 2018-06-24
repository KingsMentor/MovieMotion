package xyz.belvi.motion.data.realmObject

import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by zone2 on 6/23/18.
 */

interface Movie {
    fun getMovieTitle(): String
    fun getMoviePosterPath(moviePosterSize: MoviePosterSize): String
    fun getMovieBackDropPosterPath(moviePosterSize: MoviePosterSize): String
    fun getMovieId(): Int
    fun getMovieReleaseDate(): String
    fun getMovieVoteAverage(): String
    fun getMovieMovieRating(): Float
    fun getMovieOverview(): String
}