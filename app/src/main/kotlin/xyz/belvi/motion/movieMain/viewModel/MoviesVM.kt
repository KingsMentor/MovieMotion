package xyz.belvi.motion.movieMain.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmModel
import xyz.belvi.motion.data.client.*
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.TopRatedMovie
import xyz.belvi.motion.movieMain.presenter.MoviesFetchPresenter
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.models.retroResponse.MovieResponse
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient.Companion.apiClient
import com.google.gson.GsonBuilder
import xyz.belvi.motion.utils.*


/**
 * Created by zone2 on 6/23/18.
 */
class MoviesVM : ViewModel() {

    private var liveMovies: MutableLiveData<MutableList<MotionMovie>> = MutableLiveData()
    private var presenter: MoviesFetchPresenter? = null
    val rxDisposal = CompositeDisposable()


    fun bind(presenter: MoviesFetchPresenter, filter: MovieFilter): LiveData<MutableList<MotionMovie>> {
        resetPageCounter()
        this.presenter = presenter
        loadMoviesByFilter(filter)
        return liveMovies;
    }

    fun requestNextPage(filter: MovieFilter) {
        filter.updatePageCounter()

        if (filter == MovieFilter.POPULAR) {
            this.presenter?.onLoadStarted(false)
            fetchMoviesFromApi<PopularMovie>(filter)
        } else if (filter == MovieFilter.TOP_RATED) {
            this.presenter?.onLoadStarted(false)
            fetchMoviesFromApi<TopRatedMovie>(filter)
        }
    }

    fun switchFilter(movieFilter: MovieFilter) {
        this.presenter?.clearAdapter()
        loadMoviesByFilter(movieFilter)
    }

    private fun loadMoviesByFilter(filter: MovieFilter) {
        when (filter) {
            MovieFilter.POPULAR -> {
                this.presenter?.onLoadStarted(isRealmListEmpty<PopularMovie>())
                switchRealmObserver<PopularMovie>()
                fetchMoviesFromApi<PopularMovie>(filter)
            }
            MovieFilter.TOP_RATED -> {
                this.presenter?.onLoadStarted(isRealmListEmpty<TopRatedMovie>())
                switchRealmObserver<TopRatedMovie>()
                fetchMoviesFromApi<TopRatedMovie>(filter)
            }
            else -> {
                this.presenter?.onLoadStarted(isRealmListEmpty<FavMovie>())
                switchRealmObserver<FavMovie>()
                this.presenter?.onLoadCompleted(isRealmListEmpty<FavMovie>())

            }
        }
    }

    private inline fun <reified T : RealmModel> switchRealmObserver() {
        rxDisposal.clear()
        rxDisposal.add(
                fetch<T>()?.asFlowable()?.subscribe {
                    (it as? MutableList<MotionMovie>)?.let {
                        if (it.isNotEmpty()) {
                            liveMovies.value = it
                        }
                    }
                }!!
        )
    }

    private inline fun <reified D : RealmModel> fetchMoviesFromApi(filter: MovieFilter) {
        apiKey()?.let { apiKey ->
            apiClient.create(ApiInterface::class.java).fetchMovies(filter.path, apiKey, filter.currentPage())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        this.presenter?.onLoadFailure(isRealmListEmpty<D>())
                    }
                    .onErrorResumeNext(io.reactivex.Observable.empty())
                    .subscribe {
                        it?.let {
                            if (filter.currentPage() == 1)
                                clear<D>()
                            if (filter == MovieFilter.TOP_RATED)
                                update(it.results.toTopRatedMovies())
                            else
                                update(it.results.toPopularMovies())
                            this.presenter?.onLoadCompleted(it.results.size == 0)
                        }
                    }
        } ?: kotlin.run {
            this.presenter?.onLoadFailure(isRealmListEmpty<D>())
        }
    }

    override fun onCleared() {
        super.onCleared()
        rxDisposal.clear()
    }

}