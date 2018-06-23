package xyz.belvi.motion.main.interfaceAdapters

import android.view.View
import xyz.belvi.motion.data.realmObject.Movie

/**
 * Created by zone2 on 6/23/18.
 */
interface MoviesFetchPresenter {
    fun onLoadFailure()
    fun onLoadStarted(freshLoad: Boolean)
    fun onLoadCompleted()
    fun movieSelected(view: View, movie: Movie, postion: Int)
}