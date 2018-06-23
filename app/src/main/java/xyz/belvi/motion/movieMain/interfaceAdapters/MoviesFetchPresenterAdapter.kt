package xyz.belvi.motion.movieMain.interfaceAdapters

import android.view.View
import xyz.belvi.motion.data.realmObject.Movie

/**
 * Created by zone2 on 6/23/18.
 */
abstract class MoviesFetchPresenterAdapter : MoviesFetchPresenter {

    override fun movieSelected(view: View, movie: Movie, postion: Int) {

    }

    override fun onLoadCompleted(isEmpty: Boolean) {

    }

    override fun onLoadFailure(emptyDataSet: Boolean) {

    }

    override fun onLoadStarted(freshLoad: Boolean) {

    }
}