package xyz.belvi.motion.movieDetails.presenter

import xyz.belvi.motion.data.realmObject.MotionMovie

/**
 * Created by Nosa Belvi on 6/24/18.
 *
 *  @MovieDetailsPresenter abstract implementation for @MovieDetailsActivity
 *
 */
interface MovieDetailsPresenter {
    // notify if a movie is favorite  or nor
    fun markFavorite(isFav: Boolean): MovieDetailsPresenter
}