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
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.Trailer
import xyz.belvi.motion.movieDetails.presenter.TrailerPresenter
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient
import xyz.belvi.motion.utils.apiKey

/**
 * Created by zone2 on 6/24/18.
 */
class TrailersVM : ViewModel() {

    private var mPresenter: TrailerPresenter? = null
    val rxDisposal = CompositeDisposable()

    private var liveMovies: MutableLiveData<MutableList<Trailer>> = MutableLiveData()

    fun bind(presenter: TrailerPresenter, movieId: Int): LiveData<MutableList<Trailer>> {
        mPresenter = presenter
        loadTrailers(movieId)
        return liveMovies
    }

    fun retry(movieId: Int) {
        loadTrailers(movieId)
    }

    private fun loadTrailers(movieId: Int) {
        mPresenter?.startLoading(isRealmListEmpty<Trailer>())
        rxDisposal.clear()
        rxDisposal.add(
                fetchById(movieId)?.asFlowable()?.subscribe {
                    liveMovies.value = it
                }!!
        )
        fetchTrailers(movieId)
    }

    private fun fetchTrailers(movieId: Int) {
        apiKey()?.let {
            ApiClient.apiClient.create(ApiInterface::class.java).fetchTrailers(movieId, it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        mPresenter?.onTrailerRetrieveFailed(isRealmListEmpty<Trailer>())
                    }
                    .onErrorResumeNext(io.reactivex.Observable.empty())
                    .subscribe {
                        it.results.forEach {
                            it.movieId = movieId
                        }
                        update(it.results)
                        mPresenter?.onLoadCompleted(it.results.size == 0)
                    }

        }
    }


}