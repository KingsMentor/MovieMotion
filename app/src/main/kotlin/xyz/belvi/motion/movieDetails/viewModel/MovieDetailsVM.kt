package xyz.belvi.motion.movieDetails.viewModel


import android.arch.lifecycle.ViewModel
import xyz.belvi.motion.data.client.addOrRemoveFavMovie
import xyz.belvi.motion.data.client.isFavMovie
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter

/**
 * Created by Nosa Belvi on 6/24/18.
 *
 * @MovieDetailsVM is a viewmModel for MovieDetailsActivity
 */
class MovieDetailsVM : ViewModel() {

    private var mMovieDetailsPresenter: MovieDetailsPresenter? = null

    // binds this model with the activity
    fun bind(presenter: MovieDetailsPresenter) {
        mMovieDetailsPresenter = presenter

    }

    // check if a movie is on favorite list
    fun updateCheck(movieId: Int) {
        this.mMovieDetailsPresenter?.markFavorite(isFavMovie(movieId))
    }

    // add or remove a move from a databse implementation of favorite list
    fun addToFavoriteList(movie: FavMovie, state: Boolean) {
        addOrRemoveFavMovie(movie, state)
        this.mMovieDetailsPresenter?.markFavorite(state)
    }

}