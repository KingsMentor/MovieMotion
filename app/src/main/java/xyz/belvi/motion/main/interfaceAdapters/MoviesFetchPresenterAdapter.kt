package xyz.belvi.motion.main.interfaceAdapters

import android.view.View
import xyz.belvi.motion.data.realmObject.Movie

/**
 * Created by zone2 on 6/23/18.
 */
interface MoviesFetchPresenterAdapter : MoviesFetchPresenter {

    override fun movieSelected(view: View, movie: Movie, postion: Int) {

    }
}