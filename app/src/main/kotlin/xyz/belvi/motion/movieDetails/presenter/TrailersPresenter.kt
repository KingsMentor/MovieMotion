package xyz.belvi.motion.movieDetails.presenter

import xyz.belvi.motion.data.realmObject.Trailer
import java.util.ArrayList

/**
 * Created by Nosa Belvi on 6/24/18.
 *
 * @TrailerPresenter abstract implementation for @TrailerFragments
 *
 */
interface TrailerPresenter {
    // when a trailer fetch request fails
    fun onTrailerRetrieveFailed(emptyList: Boolean)
    // notify that fetching has started
    fun startLoading(emptyList: Boolean)
    // notify fetch completed
    fun onLoadCompleted(emptyList: Boolean)


}
