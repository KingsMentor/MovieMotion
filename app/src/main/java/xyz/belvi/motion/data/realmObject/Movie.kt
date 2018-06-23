package xyz.belvi.motion.data.realmObject

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by zone2 on 6/23/18.
 */
open class Movie(
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
        var backdrop_path: String = "",
        var adult: Boolean = false,
        var overview: String = "",
        var release_date: String = ""

) : RealmObject() {
    constructor() : this(id = 0)
}
