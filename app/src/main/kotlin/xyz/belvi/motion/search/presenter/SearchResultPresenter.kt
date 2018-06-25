package xyz.belvi.motion.search.presenter

import android.view.View
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.movieMain.presenter.MoviesFetchPresenter

/**
 * Created by zone2 on 6/25/18.
 */
interface SearchResultPresenter : MoviesFetchPresenter {

    override fun onLoadCompleted(isEmpty: Boolean) {

    }

    override fun onLoadFailure(emptyDataSet: Boolean) {

    }

    override fun onLoadStarted(freshLoad: Boolean) {

    }

    override fun clearAdapter() {

    }

    override fun movieSelected(view: View, movie: MotionMovie) {

    }

    fun onSearchResult(result: MutableList<MotionMovie>) {

    }
}