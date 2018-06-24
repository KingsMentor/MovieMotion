package xyz.belvi.motion.movieDetails.presenter

import android.view.View
import xyz.belvi.motion.data.realmObject.Movie

/**
 * Created by zone2 on 6/24/18.
 */
interface MovieDetailsPresenter {
    fun markFavorite(isFav: Boolean)
    fun presentDetails(movie: Movie)
}