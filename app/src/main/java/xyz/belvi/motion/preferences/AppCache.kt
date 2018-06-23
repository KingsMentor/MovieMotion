package xyz.belvi.motion.preferences

import android.content.Context
import xyz.belvi.instasale.models.secure.pref.SecurePref
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.models.enums.MovieSort

/**
 * Created by zone2 on 1/9/18.
 */
class AppCache : SecurePref(MotionApp.instance?.applicationContext, AppCache::class.simpleName!!) {

    companion object {
        private val SORT_TYPE = "SORT_TYPE"
    }

    /**
     * update sort preference
     */

    var updateSortType: MovieSort
        get() = MovieSort.valueOf(getString(SORT_TYPE, MovieSort.FAVORITE.sortType))
        set(movieSort) = putString(SORT_TYPE, movieSort.sortType)


}

