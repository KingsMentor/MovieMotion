package xyz.belvi.motion.movieDetails.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import xyz.belvi.motion.data.client.addOrRemoveFavMovie
import xyz.belvi.motion.data.client.findMovieById
import xyz.belvi.motion.data.client.isFavMovie
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.data.realmObject.PopularMovie
import xyz.belvi.motion.data.realmObject.TopRatedMovie
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter
import xyz.belvi.motion.utils.toFavMovie

/**
 * Created by zone2 on 6/24/18.
 */
class MovieDetailsVM : ViewModel() {
    private var liveMovie: MutableLiveData<Movie> = MutableLiveData()
    private var mMovieDetailsPresenter: MovieDetailsPresenter? = null
    private var movie: Movie? = null
    fun bind(presenter: MovieDetailsPresenter, movieFilter: MovieFilter, movieId: Int): LiveData<Movie> {
        mMovieDetailsPresenter = presenter
        presentMovieDetails(movieFilter, movieId)
        return liveMovie

    }

    fun updateCheck(movieId: Int){
        this.mMovieDetailsPresenter?.markFavorite(isFavMovie(movieId))
    }

    fun addToFavoriteList(movie: Movie, state: Boolean) {
        if (movie is FavMovie)
            addOrRemoveFavMovie(movie, state)
        else {
            movie.toFavMovie()?.let {
                addOrRemoveFavMovie(it, state)
            }
        }
        this.mMovieDetailsPresenter?.markFavorite(isFavMovie(movie.getMovieId()))
    }

    private fun presentMovieDetails(movieFilter: MovieFilter, movieId: Int) {
        movie = when (movieFilter) {
            MovieFilter.POPULAR -> {
                findMovieById<PopularMovie>(movieId)
            }
            MovieFilter.TOP_RATED -> {
                findMovieById<TopRatedMovie>(movieId)
            }
            else -> {
                findMovieById<FavMovie>(movieId)
            }
        }
        liveMovie.value = movie
        movie?.let {
            mMovieDetailsPresenter?.presentDetails(it)?.markFavorite(isFavMovie(movieId))
        }
    }
}