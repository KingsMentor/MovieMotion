package xyz.belvi.motion.main.interfaceAdapters

import android.view.View
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie

/**
 * Created by zone2 on 6/23/18.
 */
interface MoviesFetchPresenter {
    fun onLoadFailure(emptyDataSet: Boolean)
    fun onLoadStarted(freshLoad: Boolean)
    fun onLoadCompleted(isEmpty: Boolean)
    fun clearAdapter()
    fun movieSelected(view: View, movie: Movie, postion: Int)
}