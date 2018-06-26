package xyz.belvi.motion.search.presenter

import android.view.View
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.movieMain.presenter.MoviesFetchPresenter

/**
 * Created by Nosa Belvi on 6/25/18.
 * @SearchResultPresenter abstracts implementation for @SearchActivity
 * extends @MoviesFetchPresenter since both perform similar core operation
 * @override @MoviesFetchPresenter methods to prevent class that extends @SearchResultPresenter from doing same.
 * see @SearchActivity for examples.
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