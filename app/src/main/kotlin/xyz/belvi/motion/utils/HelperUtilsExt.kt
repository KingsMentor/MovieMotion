package xyz.belvi.motion.utils

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import xyz.belvi.motion.BuildConfig
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.data.realmObject.*
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.preferences.AppCache


/**
 * Created by Nosa Belvi on 6/24/18.
 *
 * @xyz.belvi.motion.utils.HelperUtilsExtKt provides basic helper functions
 * for different objects with the help of kotlin extention
 */

// get api ket from string.xml
fun ViewModel.apiKey(): String? {
    return MotionApp.instance?.getString(R.string.movie_db_api_key)
}

// reset page counter
fun ViewModel.resetPageCounter() {
    AppCache().resetLastRequestedPage()
}

// update page counter. This is for paginating api calls
fun MovieFilter.updatePageCounter() {
    AppCache().updateLastRequested(this)
}

// return current page or last requested page
fun MovieFilter.currentPage(): Int {
    return AppCache().currentPage(this)
}


// convert a movie list to a list of popular movies
fun MutableList<Movie>.toPopularMovies(): MutableList<PopularMovie> {
    val popularMovies = mutableListOf<PopularMovie>()
    this.forEach {
        popularMovies.add(xyz.belvi.motion.data.realmObject.PopularMovie(it.id, it))
    }
    return popularMovies
}


// convert a movie list to a list of topRated movies
fun MutableList<Movie>.toTopRatedMovies(): MutableList<TopRatedMovie> {
    val topRated = mutableListOf<TopRatedMovie>()
    this.forEach {
        topRated.add(xyz.belvi.motion.data.realmObject.TopRatedMovie(it.id, it))
    }
    return topRated
}



// only display log on debug mode
fun Context.motionError(tag: String, message: String?) {
    if (BuildConfig.DEBUG)
        Log.e(tag, message)
}

// calculate number of columns that can fit into the screen
fun Context.calculateNoOfColumns(): Int {
    val displayMetrics = this.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 200).toInt()
}