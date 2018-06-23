package xyz.belvi.motion.main.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import xyz.belvi.motion.data.client.clearMovies
import xyz.belvi.motion.data.client.fetchMovies
import xyz.belvi.motion.data.client.isMovieListEmpty
import xyz.belvi.motion.data.client.updateMovies
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.main.interfaceAdapters.MoviesFetchPresenter
import xyz.belvi.motion.models.enums.MovieSort
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient.Companion.apiClient

/**
 * Created by zone2 on 6/23/18.
 */
class MoviesVM : ViewModel() {

    private var page = 0
    private var liveMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    private lateinit var presenter: MoviesFetchPresenter

    fun bindWithModel(presenter: MoviesFetchPresenter, apiKey: String, sort: MovieSort): LiveData<MutableList<Movie>> {
        this.presenter = presenter
        this.presenter.onLoadStarted(isMovieListEmpty())
        fetchMovies()?.asFlowable()?.subscribe {
            (it as MutableList<Movie>).let {
                if (it.isNotEmpty()) {
                    liveMovies.value = it
                }
            }
        }
        fetchMoviesFromApi(apiKey, sort)
        return liveMovies;
    }

    fun fetchMoviesFromApi(apiKey: String, sortType: MovieSort) {
        apiClient.create(ApiInterface::class.java).fetchMovies(sortType.sortType, apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    this.presenter.onLoadFailure()
                }
                .onErrorResumeNext(io.reactivex.Observable.empty())
                .subscribe {
                    page++
                    clearMovies()
                    updateMovies(it.result)
                    this.presenter.onLoadCompleted()
                }
    }

}