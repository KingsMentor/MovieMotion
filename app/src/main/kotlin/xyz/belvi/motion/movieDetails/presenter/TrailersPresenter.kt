package xyz.belvi.motion.movieDetails.presenter

import xyz.belvi.motion.data.realmObject.Trailer
import java.util.ArrayList

/**
 * Created by zone2 on 6/24/18.
 */
interface TrailerPresenter {

    fun onTrailerRetrieveFailed(emptyList: Boolean)
    fun startLoading(emptyList: Boolean)
    fun onLoadCompleted(emptyList: Boolean)


}
