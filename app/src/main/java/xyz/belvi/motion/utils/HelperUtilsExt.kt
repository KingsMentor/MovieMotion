package xyz.belvi.motion.utils

import android.arch.lifecycle.ViewModel
import xyz.belvi.motion.R
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.TopRatedMovie
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

fun Movie.toFavMovie(): FavMovie? {
    if (this is PopularMovie) {
        return FavMovie(
                id = this.id,
                vote_average = this.vote_average,
                poster_path = this.poster_path,
                backdrop_path = this.backdrop_path,
                adult = this.adult,
                title = this.title,
                voteCount = this.voteCount,
                popularity = this.popularity,
                original_language = this.original_language,
                release_date = this.release_date,
                original_title = this.original_title,
                video = this.video,
                overview = this.overview)
    } else if (this is TopRatedMovie) {
        return FavMovie(
                id = this.id,
                vote_average = this.vote_average,
                poster_path = this.poster_path,
                backdrop_path = this.backdrop_path,
                adult = this.adult,
                title = this.title,
                voteCount = this.voteCount,
                popularity = this.popularity,
                original_language = this.original_language,
                release_date = this.release_date,
                original_title = this.original_title,
                video = this.video,
                overview = this.overview)
    }
    return null
}

