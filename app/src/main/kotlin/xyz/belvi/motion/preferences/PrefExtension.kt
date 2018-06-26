package xyz.belvi.motion.preferences

import android.content.Context
import xyz.belvi.motion.models.enums.MovieFilter

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @xyz.belvi.motion.preferences.PrefExtensionKt provides extensible funtions on @Context
 * this allows prefereces to be easily acccessible from any class with a  @Context
 *
 */

// cache preference of filterType
fun Context.setFilterType(sort: MovieFilter) {
    AppCache().updateFilterType = sort
}

// getPreference on filterType
fun Context.getFilterType(): MovieFilter = AppCache().updateFilterType
