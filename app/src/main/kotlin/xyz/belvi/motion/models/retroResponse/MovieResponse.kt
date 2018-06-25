package xyz.belvi.motion.models.retroResponse

import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @MovieResponse is used to model  JSON parse from retrofit response for movies api
 *
 *
 */


// SAMPLE OF RESPONSE
//{
//    "page": 1,
//    "total_results": 1194,
//    "total_pages": 60,
//    "results": [
//    {
//        "vote_count": 12712,
//        "id": 550,
//        "video": false,
//        "vote_average": 8.4,
//        "title": "Fight Club",
//        "popularity": 29.559748,
//        "poster_path": "/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
//        "original_language": "en",
//        "original_title": "Fight Club",
//        "genre_ids": [
//        18
//        ],
//        "backdrop_path": "/87hTDiay2N2qWyX4Ds7ybXi9h8I.jpg",
//        "adult": false,
//        "overview": "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
//        "release_date": "1999-10-15"
//    }
//}


data class MovieResponse(
        val page: Int,
        val results: MutableList<Movie>)

