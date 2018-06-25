package xyz.belvi.motion.models.enums

import android.support.annotation.IdRes
import xyz.belvi.motion.R

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @MovieFilter enumerates filter options for viewing movies
 */
enum class MovieFilter(val friendlyName: String, val path: String, @IdRes val id: Int) {
    POPULAR("Popular", "popular", R.id.action_filter_popular),
    TOP_RATED("Top Rated", "top_rated", R.id.action_filter_top_rated),
    FAVORITE("Favorite", "", R.id.action_filter_favorite);

}

//
/**this is to find a movie filter by id.
 * @resId is a menuitem id.
 */
fun findByResID(@IdRes resId: Int): MovieFilter {
    MovieFilter.values().forEach {
        if (it.id == resId)
            return it
    }
    return MovieFilter.POPULAR
}