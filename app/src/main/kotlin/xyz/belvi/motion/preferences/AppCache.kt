package xyz.belvi.motion.preferences

import xyz.belvi.instasale.models.secure.pref.SecurePref
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.models.enums.MovieFilter

/**
 * Created by Nosa Belvi on 1/9/18.
 *
 * @AppCache handles all sharePreferences operation in the app.
 * extends @SecurePref to implements encrpted shared preference.
 * This is not the best use case but this implementation is really important for securing date in share preference
 * see documentation on @SecurePref for more information
 *
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


    /**
     * update last requested page on  @MovieFilter.POPULAR
     */

    private var lastRequestedPopularPage: Int
        get() = getInt(PAGE_POPULAR, 1)
        set(lastRequestedPage) = putInt(PAGE_POPULAR, lastRequestedPage)


    /**
     * update last requested page on  @MovieFilter.TOP_RATED
     */
    private var lastRequestedRatedPage: Int
        get() = getInt(PAGE_RATED, 1)
        set(lastRequestedPage) = putInt(PAGE_RATED, lastRequestedPage)


    /**
     * reset page of  @MovieFilter.TOP_RATED and @MovieFilter.POPULAR
     */
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

    fun currentPage(filter: MovieFilter): Int {
        return if (filter == MovieFilter.TOP_RATED)
            lastRequestedRatedPage
        else
            lastRequestedPopularPage
    }


}

