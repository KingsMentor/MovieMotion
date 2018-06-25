package xyz.belvi.motion.movieDetails.presenter

import xyz.belvi.motion.data.realmObject.MotionMovie

/**
 * Created by zone2 on 6/24/18.
 */
interface MovieDetailsPresenter {
    fun markFavorite(isFav: Boolean): MovieDetailsPresenter
}