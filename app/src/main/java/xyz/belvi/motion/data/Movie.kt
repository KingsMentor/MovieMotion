package xyz.belvi.motion.data

import io.realm.RealmObject

/**
 * Created by zone2 on 6/23/18.
 */
class Movie(
        val voteCount: Int,
        val id: Int,
        val video: Boolean,
        val vote_average: Float,
        val title: String,
        val popularity: Float,
        val poster_path: String,
        val original_language: String,
        val original_title: String,
        val backdrop_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String

) : RealmObject()
