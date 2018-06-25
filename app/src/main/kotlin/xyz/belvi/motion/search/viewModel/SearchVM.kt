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
import xyz.belvi.motion.utils.toTopRatedMovies

/**
 * Created by zone2 on 6/25/18.
 */
class SearchVM : ViewModel() {

    private lateinit var mPresenter: SearchResultPresenter
    private val rxDispose: CompositeDisposable = CompositeDisposable()
    fun bind(presenter: SearchResultPresenter) {
        this.mPresenter = presenter
    }


    fun search(query: String) {
        mPresenter.onLoadStarted(true)
        runQuery(query)
    }

    private fun runQuery(query: String) {

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
                                mPresenter.onSearchResult(it.results.toTopRatedMovies() as MutableList<MotionMovie>)
                            }
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        rxDispose.clear()
    }
}