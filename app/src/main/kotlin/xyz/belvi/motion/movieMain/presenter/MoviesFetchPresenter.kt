package xyz.belvi.motion.movieMain.presenter

import android.view.View
import xyz.belvi.motion.data.realmObject.MotionMovie

/**
 * Created by Nosa Belvi on 6/23/18.
 *
 * @MoviesFetchPresenter abstract allowabled implementation for mainActivity from @MoviesVM
 */
interface MoviesFetchPresenter {
    fun onLoadFailure(emptyDataSet: Boolean)
    fun onLoadStarted(freshLoad: Boolean)
    fun onLoadCompleted(isEmpty: Boolean)
    fun clearAdapter()
    fun movieSelected(view: View, movie: MotionMovie)

}