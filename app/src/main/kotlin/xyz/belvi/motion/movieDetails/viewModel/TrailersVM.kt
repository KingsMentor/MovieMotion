package xyz.belvi.motion.movieDetails.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import xyz.belvi.motion.data.client.fetchById
import xyz.belvi.motion.data.client.isRealmListEmpty
import xyz.belvi.motion.data.client.update
import xyz.belvi.motion.data.realmObject.Trailer
import xyz.belvi.motion.movieDetails.presenter.TrailerPresenter
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient
import xyz.belvi.motion.utils.apiKey

/**
 * Created by Nosa Belvi on 6/24/18.
 *
 *  @MovieDetailsVM is a livedata viewmModel for TrailersFragment
 *
 */
class TrailersVM : ViewModel() {

    private var mPresenter: TrailerPresenter? = null
    // disposal bag to handle memory leaks with subscriptions on rx
    val rxDisposal = CompositeDisposable()


    // initialise livedata for MutableList<Trailer>
    private var liveMovies: MutableLiveData<MutableList<Trailer>> = MutableLiveData()

    // bind viewModel with TrailersFragment
    fun bind(presenter: TrailerPresenter, movieId: Int): LiveData<MutableList<Trailer>> {
        mPresenter = presenter
        loadTrailers(movieId)
        return liveMovies
    }

    // handle retry for failed scenerio
    fun retry(movieId: Int) {
        loadTrailers(movieId)
    }

    /** handles loading of trailers. First, we load from database (if any entry exist). Then, we procedd to
     * make an api call to update local database
     */
    private fun loadTrailers(movieId: Int) {
        mPresenter?.startLoading(isRealmListEmpty<Trailer>())
        rxDisposal.clear()
        rxDisposal.add(
                // fetch and listen to changes on this realm object query
                fetchById(movieId)?.asFlowable()?.subscribe {
                    liveMovies.value = it
                }!!
        ) // add this subscription to a dispose bad to avoid memory leaks
        fetchTrailers(movieId)
    }

    // handles fecthing from api using retrofit
    private fun fetchTrailers(movieId: Int) {
        // proceed with this call if and only if apiKey was retrieved
        apiKey()?.let {
            // initiate a network call the with retrofit
            ApiClient.apiClient.create(ApiInterface::class.java).fetchTrailers(movieId, it)
                    .subscribeOn(Schedulers.io()) // perform the network call on an io thread
                    .observeOn(AndroidSchedulers.mainThread()) // return response on Android mainThread
                    .doOnError {
                        // notify for error
                        mPresenter?.onTrailerRetrieveFailed(isRealmListEmpty<Trailer>())
                    }
                    .onErrorResumeNext(io.reactivex.Observable.empty()) // handles expeception that might lead to app crash
                    .subscribe {
                        // update trailers databse
                        it.results.forEach {
                            it.movieId = movieId
                        }
                        update(it.results)
                        // notify completion of tailers fetch.
                        mPresenter?.onLoadCompleted(it.results.size == 0)
                    }

        } ?: kotlin.run {
            // notify error on failed scnerio
            mPresenter?.onTrailerRetrieveFailed(isRealmListEmpty<Trailer>())
        }
    }

    override fun onCleared() {
        super.onCleared()
        rxDisposal.clear()
    }


}