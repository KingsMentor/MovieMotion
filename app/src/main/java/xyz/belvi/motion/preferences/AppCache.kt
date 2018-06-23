package xyz.belvi.motion.preferences

import xyz.belvi.instasale.models.secure.pref.SecurePref
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.models.enums.MovieFilter

/**
 * Created by zone2 on 1/9/18.
 */
class AppCache : SecurePref(MotionApp.instance?.applicationContext, AppCache::class.simpleName!!) {

    companion object {
        private val FILTER_TYPE = "FILTER_TYPE"
        private val PAGE_POPULAR = "PAGE_POPULAR"
        private val PAGE_RATED = "PAGE_RATED"
    }

    /**
     * update sort preference
     */

    var updateFilterType: MovieFilter
        get() = MovieFilter.valueOf(getString(FILTER_TYPE, MovieFilter.POPULAR.name))
        set(movieSort) = putString(FILTER_TYPE, movieSort.name)


    private var lastRequestedPopularPage: Int
        get() = getInt(PAGE_POPULAR, 1)
        set(lastRequestedPage) = putInt(PAGE_POPULAR, lastRequestedPage)

    private var lastRequestedRatedPage: Int
        get() = getInt(PAGE_RATED, 1)
        set(lastRequestedPage) = putInt(PAGE_RATED, lastRequestedPage)

    fun resetLastRequestedPage() {
        lastRequestedPopularPage = 1
        lastRequestedRatedPage = 1
    }

    fun updateLastRequested(filter: MovieFilter) {
        if (filter == MovieFilter.TOP_RATED)
            lastRequestedRatedPage += 1
        else
            lastRequestedPopularPage += 1
    }


}

