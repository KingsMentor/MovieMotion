package xyz.belvi.motion.utils

import android.arch.lifecycle.ViewModel
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.preferences.AppCache

/**
 * Created by zone2 on 6/24/18.
 */
fun ViewModel.apiKey(): String? {
    return MotionApp.instance?.getString(R.string.movie_db_api_key)
}

fun ViewModel.resetPageCounter() {
    AppCache().resetLastRequestedPage()
}

fun MovieFilter.updatePageCounter() {
    AppCache().updateLastRequested(this)
}

fun MovieFilter.currentPage(): Int {
    return AppCache().currentPage(this)
}