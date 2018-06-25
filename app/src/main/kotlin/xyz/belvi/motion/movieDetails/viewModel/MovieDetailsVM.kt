package xyz.belvi.motion.movieDetails.viewModel


import android.arch.lifecycle.ViewModel
import xyz.belvi.motion.data.client.addOrRemoveFavMovie
import xyz.belvi.motion.data.client.isFavMovie
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter

/**
 * Created by zone2 on 6/24/18.
 */
class MovieDetailsVM : ViewModel() {

    private var mMovieDetailsPresenter: MovieDetailsPresenter? = null

    fun bind(presenter: MovieDetailsPresenter) {
        mMovieDetailsPresenter = presenter

    }

    fun updateCheck(movieId: Int) {
        this.mMovieDetailsPresenter?.markFavorite(isFavMovie(movieId))
    }

    fun addToFavoriteList(movie: FavMovie, state: Boolean) {
        addOrRemoveFavMovie(movie, state)
        this.mMovieDetailsPresenter?.markFavorite(state)
    }

}