package xyz.belvi.motion.movieDetails.trailers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.belvi.motion.R
import xyz.belvi.motion.movieDetails.presenter.TrailerPresenter

/**
 * Created by zone2 on 6/24/18.
 */
class TrailersFragment : Fragment(), TrailerPresenter {
    override fun onTrailerRetrieveFailed(emptyList: Boolean) {
    }

    override fun startLoading(emptyList: Boolean) {

    }

    override fun onLoadCompleted(emptyList: Boolean) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trailers_layout, container, false)
    }

}