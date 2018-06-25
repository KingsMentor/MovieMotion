package xyz.belvi.motion.utils

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.data.realmObject.*
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.movieDetails.MovieDetailedActivity
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

fun MutableList<Movie>.toPopularMovies(): MutableList<PopularMovie> {
    val popularMovies = mutableListOf<PopularMovie>()
    this.forEach {
        popularMovies.add(xyz.belvi.motion.data.realmObject.PopularMovie(it.id, it))
    }
    return popularMovies
}

fun MutableList<Movie>.toTopRatedMovies(): MutableList<TopRatedMovie> {
    val topRated = mutableListOf<TopRatedMovie>()
    this.forEach {
        topRated.add(xyz.belvi.motion.data.realmObject.TopRatedMovie(it.id, it))
    }
    return topRated
}


fun AppCompatActivity.showMovieDetails(view: View, movie: MotionMovie) {
    val p1 = Pair.create(view, getString(R.string.postal_transition_name))
    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(Intent(this, MovieDetailedActivity::class.java)
                .putExtra(MovieDetailedActivity.MOVIE_KEY, movie.getMovieItem()), optionsCompat.toBundle())
    } else {
        startActivity(Intent(this, MovieDetailedActivity::class.java)
                .putExtra(MovieDetailedActivity.MOVIE_KEY, movie.getMovieItem()))
    }


}
