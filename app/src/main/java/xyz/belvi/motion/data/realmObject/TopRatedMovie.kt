package xyz.belvi.motion.data.realmObject

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.belvi.motion.constants.IMG_PATH
import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by zone2 on 6/23/18.
 */
open class TopRatedMovie(
        @PrimaryKey
        var id: Int = 0,
        var voteCount: Int = 0,
        var video: Boolean = false,
        var vote_average: Float = 0f,
        var title: String = "",
        var popularity: Float = 0f,
        var poster_path: String = "",
        var original_language: String = "",
        var original_title: String = "",
        var backdrop_path: String? = "",
        var adult: Boolean = false,
        var overview: String = "",
        var release_date: String = ""

) : RealmObject(), Movie {

    override fun getMovieBackDropPosterPath(moviePosterSize: MoviePosterSize): String {
        return IMG_PATH + moviePosterSize.size + "/" + this.backdrop_path
    }

    override fun getMovieReleaseDate(): String {
        return release_date
    }

    override fun getMovieVoteAverage(): String {
        return vote_average.toString()
    }

    override fun getMovieMovieRating(): Float {
        return (vote_average / 2f)
    }

    override fun getMovieOverview(): String {
        return overview
    }

    override fun getMovieId(): Int {
        return id
    }

    override fun getMovieTitle(): String {
        return title
    }

    override fun getMoviePosterPath(moviePosterSize: MoviePosterSize): String {
        return IMG_PATH + moviePosterSize.size + "/" + this.poster_path
    }

    constructor() : this(id = 0)


}
