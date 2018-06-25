package xyz.belvi.motion.search.viewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient
import xyz.belvi.motion.search.presenter.SearchResultPresenter
import xyz.belvi.motion.utils.apiKey

/**
 * Created by zone2 on 6/25/18.
 */
class SearchVM : ViewModel() {

    private lateinit var mPresenter: SearchResultPresenter
    private var currentPage = 1
    fun bind(presenter: SearchResultPresenter) {
        this.mPresenter = presenter
    }

    fun requestNextPage(query: String) {
        currentPage++
        mPresenter.searching()
        runQuery(query)
    }

    fun search(query: String) {
        currentPage = 1
        mPresenter.searching()
        runQuery(query)
    }

    private fun runQuery(query: String, isUpdate: Boolean = false) {
        apiKey()?.let {
            ApiClient.apiClient.create(ApiInterface::class.java)
                    .search(query, it, currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        mPresenter.searchFailed()
                    }
                    .onErrorResumeNext(io.reactivex.Observable.empty())
                    .subscribe {
                        mPresenter.searchCompleted(isUpdate, it.results)
                    }
        }
    }


}