package xyz.belvi.motion.preferences

import android.content.Context
import xyz.belvi.motion.models.enums.MovieFilter

/**
 * Created by zone2 on 6/23/18.
 */

fun Context.setFilterType(sort: MovieFilter) {
    AppCache().updateFilterType = sort
}

fun Context.getFilterType(): MovieFilter = AppCache().updateFilterType
