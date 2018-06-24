package xyz.belvi.motion.data.realmObject

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by zone2 on 6/24/18.
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