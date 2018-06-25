package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.Trailer

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @TrailerResponse is used to model JSON parse from retrofit response for movie trailers api
 *
 */


// SAMPLE OF RESPONSE

//{
//    "id": 19404,
//    "results": [
//    {
//        "id": "5581bd68c3a3685df70000c6",
//        "iso_639_1": "en",
//        "iso_3166_1": "US",
//        "key": "c25GKl5VNeY",
//        "name": "Dilwale Dulhania Le Jayenge | Official Trailer | Shah Rukh Khan | Kajol",
//        "site": "YouTube",
//        "size": 720,
//        "type": "Trailer"
//    }
//}
data class TrailerResponse(
        val page: Int,
        val results: MutableList<Trailer>)

