package xyz.belvi.motion.data.realmObject

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.belvi.motion.constants.IMG_PATH
import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * FavMovie is a @RealmObject that keeps track of all favorite movies
 * It implements @MotionMovie - which provides base attrs for all Movie implementation
 */
open class FavMovie(
        @PrimaryKey
        var id: Int = 0,
        var movie: Movie? = Movie()

) : RealmObject(), MotionMovie {


    override fun getMovieItem(): Movie? {
        return movie
    }

    override fun getMovieBackDropPosterPath(moviePosterSize: MoviePosterSize): String {
        return IMG_PATH + moviePosterSize.size + "/" + this.movie?.backdrop_path
    }

    override fun getMovieReleaseDate(): String {
        return movie?.release_date ?: kotlin.run { "" }
    }

    override fun getMovieVoteAverage(): String {
        return movie?.vote_average?.toString() ?: kotlin.run { "" }
    }

    override fun getMovieMovieRating(): Float {
        return (movie?.vote_average ?: kotlin.run { 0 } / 2f)
    }

    override fun getMovieOverview(): String {
        return movie?.overview ?: kotlin.run { "" }
    }

    override fun getMovieId(): Int {
        return id
    }

    override fun getMovieTitle(): String {
        return movie?.title ?: kotlin.run { "" }
    }

    override fun getMoviePosterPath(moviePosterSize: MoviePosterSize): String {
        return IMG_PATH + moviePosterSize.size + "/" + this.movie?.poster_path
    }

    constructor() : this(id = 0)


}
