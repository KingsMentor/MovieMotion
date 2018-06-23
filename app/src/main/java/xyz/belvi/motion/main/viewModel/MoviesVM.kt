package xyz.belvi.motion.main.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.RealmModel
import xyz.belvi.motion.data.client.*
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.TopRatedMovie
import xyz.belvi.motion.main.interfaceAdapters.MoviesFetchPresenter
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.models.retroResponse.PopularMovieResponse
import xyz.belvi.motion.models.retroResponse.TopRatedMovieResponse
import xyz.belvi.motion.network.call.ApiInterface
import xyz.belvi.motion.network.client.ApiClient.Companion.apiClient

/**
 * Created by zone2 on 6/23/18.
 */
class MoviesVM : ViewModel() {

    private var page = 1
    private var liveMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    private lateinit var presenter: MoviesFetchPresenter
    val rxDisposal = CompositeDisposable()


    fun bindWithModel(presenter: MoviesFetchPresenter, filter: MovieFilter): LiveData<MutableList<Movie>> {
        resetPageCounter()
        this.presenter = presenter
        loadMoviesByFilter(filter)
        return liveMovies;
    }

    fun requestNextPage(filter: MovieFilter) {
        if (filter == MovieFilter.POPULAR) {
            this.presenter.onLoadStarted(false)
            fetchMoviesFromApi<PopularMovieResponse, PopularMovie>(filter)
        } else if (filter == MovieFilter.TOP_RATED) {
            fetchMoviesFromApi<PopularMovieResponse, PopularMovie>(filter)
        }
    }

    fun switchFilter(movieFilter: MovieFilter) {
        loadMoviesByFilter(movieFilter)
    }

    private fun loadMoviesByFilter(filter: MovieFilter) {
        when (filter) {
            MovieFilter.POPULAR -> {
                this.presenter.onLoadStarted(isMovieListEmpty<PopularMovie>())
                switchRealmObserver<PopularMovie>()
                fetchMoviesFromApi<PopularMovieResponse, PopularMovie>(filter)
            }
            MovieFilter.TOP_RATED -> {
                this.presenter.onLoadStarted(isMovieListEmpty<TopRatedMovie>())
                switchRealmObserver<TopRatedMovie>()
                fetchMoviesFromApi<PopularMovieResponse, PopularMovie>(filter)
            }
            else -> {
                this.presenter.onLoadStarted(isMovieListEmpty<FavMovie>())
                switchRealmObserver<FavMovie>()

            }
        }
    }

    private inline fun <reified T : RealmModel> switchRealmObserver() {
        rxDisposal.clear()
        rxDisposal.add(
                fetchMovies<T>()?.asFlowable()?.subscribe {
                    it.let {
                        if (it.isNotEmpty()) {
                            liveMovies.value = it as MutableList<Movie>
                        }
                    }
                }!!
        )
    }

    private inline fun <reified T, reified D : RealmModel> fetchMoviesFromApi(filter: MovieFilter) {
        apiKey()?.let { apiKey ->
            apiClient.create(ApiInterface::class.java).fetchMovies(filter.sortType, apiKey, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        this.presenter.onLoadFailure(isMovieListEmpty<D>())
                    }
                    .onErrorResumeNext(io.reactivex.Observable.empty())
                    .map { Gson().fromJson(it.asJsonObject.toString(), T::class.java) }
                    .subscribe {
                        it?.let {
                            if (page == 0)
                                clearMovies<D>()
                            filter.updatePageCounter()
                            if (filter == MovieFilter.TOP_RATED)
                                updateMovies((it as PopularMovieResponse).results)
                            else
                                updateMovies((it as TopRatedMovieResponse).results)
                            this.presenter.onLoadCompleted()
                        }
                    }
        }
    }

}