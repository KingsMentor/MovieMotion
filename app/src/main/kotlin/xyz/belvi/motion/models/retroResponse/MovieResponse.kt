package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie

/**
 * Created by zone2 on 6/23/18.
 */

data class MovieResponse(
        val page: Int,
        val results: MutableList<Movie>)
