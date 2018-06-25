package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.Trailer
import xyz.belvi.motion.models.SearchResult

/**
 * Created by zone2 on 6/23/18.
 */

data class SearchResponse(
        val page: Int,
        val results: MutableList<SearchResult>)

