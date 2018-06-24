package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.Trailer

/**
 * Created by zone2 on 6/23/18.
 */

data class TrailerResponse(
        val page: Int,
        val results: MutableList<Trailer>)

