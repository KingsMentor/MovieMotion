package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.TopRatedMovie

/**
 * Created by zone2 on 6/23/18.
 */

data class TopRatedMovieResponse(
        val page: Int,
        val results: MutableList<TopRatedMovie>)
