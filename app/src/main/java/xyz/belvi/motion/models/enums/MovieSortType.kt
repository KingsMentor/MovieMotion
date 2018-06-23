package xyz.belvi.motion.models.enums

import android.support.annotation.IdRes
import xyz.belvi.motion.R

/**
 * Created by zone2 on 6/23/18.
 */
enum class MovieFilter(val sortType: String, @IdRes val id: Int) {
    POPULAR("popular", R.id.action_filter_popular),
    TOP_RATED("top_rated", R.id.action_filter_top_rated),
    FAVORITE("Favorite", R.id.action_fav);

}

fun findByResID(@IdRes resId: Int): MovieFilter {
    MovieFilter.values().forEach {
        if (it.id == resId)
            return it
    }
    return MovieFilter.POPULAR
}