package xyz.belvi.motion.data.realmObject

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Nosa Belvi on 6/24/18.
 *
 * @Trailer is a @RealmObject that keeps track of all trailers belonging to a movie
 * @movieId helps map a trailer to a particular movie.
 *
 */
open class Trailer(
        @PrimaryKey
        var id: String = "",
        var movieId: Int = 0,
        var key: String = "",
        var name: String = ""
) : RealmObject() {
    constructor() : this(id = "")
}