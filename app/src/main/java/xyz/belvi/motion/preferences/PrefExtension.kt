package xyz.belvi.motion.preferences

import android.content.Context
import xyz.belvi.motion.models.enums.MovieSort

/**
 * Created by zone2 on 6/23/18.
 */

fun Context.setSortType(sort: MovieSort) {
    AppCache().updateSortType = sort
}

fun Context.getSortType(): MovieSort = AppCache().updateSortType
