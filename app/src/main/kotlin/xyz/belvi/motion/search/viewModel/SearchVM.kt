package xyz.belvi.motion.search.viewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient
import xyz.belvi.motion.search.presenter.SearchResultPresenter
import xyz.belvi.motion.utils.apiKey
import xyz.belvi.motion.utils.toPopularMovies
import xyz.belvi.motion.utils.toTopRatedMovies

/**
 * Created by Nosa Belvi on 6/25/18.
 *
 * ViewModel for SearchActiviy
 * extends @ViewModel
 * @rxDispose - disposeBag for rxSubscriptions
 *
 */
class SearchVM : ViewModel() {

    private lateinit var mPresenter: SearchResultPresenter
    private val rxDispose: CompositeDisposable = CompositeDisposable()

    fun bind(presenter: SearchResultPresenter) {
        this.mPresenter = presenter
    }


    // perform a search query
    fun search(query: String) {
        mPresenter.onLoadStarted(true)
        runQuery(query)
    }

    private fun runQuery(query: String) {

        // only call this call if apiKey was successfully retrieved
        apiKey()?.let {
            rxDispose.clear()
            rxDispose.add(
                    ApiClient.apiClient.create(ApiInterface::class.java)
                            .search(query, it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnError {
                                mPresenter.onLoadFailure(true)
                            }
                            .onErrorResumeNext(io.reactivex.Observable.empty())
                            .subscribe {

                                /** first convert to any MotionMove type. I choose to use @it.results.toTopRatedMovies(),
                                 * it can also be @it.results.toPopularMovies(). Both are of type @MotionMovie and woud work
                                 */

                                mPresenter.onSearchResult(it.results.toPopularMovies() as MutableList<MotionMovie>)
                            }
            )
        } ?: kotlin.run {
            // notify error on failed scnerio
            mPresenter.onLoadFailure(true)
        }
    }


    // clear disposebag
    override fun onCleared() {
        super.onCleared()
        rxDispose.clear()
    }
}