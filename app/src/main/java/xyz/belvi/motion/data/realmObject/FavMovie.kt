package xyz.belvi.motion.data.realmObject

import au.com.helfie.molecheck.constants.IMG_PATH
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by zone2 on 6/23/18.
 */
open class FavMovie(
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
    override fun getMovieTitle(): String {
        return title
    }

    override fun getMoviePosterPath(moviePosterSize: MoviePosterSize): String {
        return IMG_PATH + moviePosterSize.size + "/" + this.poster_path
    }

    constructor() : this(id = 0)


}
