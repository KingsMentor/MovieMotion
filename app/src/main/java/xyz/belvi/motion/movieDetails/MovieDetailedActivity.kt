package xyz.belvi.motion.movieDetails

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detailed.*
import kotlinx.android.synthetic.main.content_movie_detailed_activty.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.models.enums.MoviePosterSize
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter
import xyz.belvi.motion.movieDetails.viewModel.MovieDetailsVM
import xyz.belvi.motion.movieMain.viewModel.MoviesVM
import xyz.belvi.motion.preferences.getFilterType

class MovieDetailedActivity : AppCompatActivity(), MovieDetailsPresenter {


    companion object {

        val MOVIE_KEY = "xyz.belvi.motion.views.activities.MovieDetailedActivty.MOVIE_KEY"

    }

    var favItem: MenuItem? = null
    private lateinit var movieDetailsVM: MovieDetailsVM

    private fun movieId(): Int? = intent.getIntExtra(MOVIE_KEY, -1)
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detailed)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        movieDetailsVM = ViewModelProviders.of(this).get(MovieDetailsVM::class.java)
        movieId()?.let {
            movieDetailsVM.bind(this@MovieDetailedActivity, getFilterType(), it).observeForever {
                it?.let {
                    presentDetails(it)
                }
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        else
            movieDetailsVM.addToFavoriteList(movie, item.isChecked)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie_detailed_activty, menu)
        favItem = menu.findItem(R.id.action_fav)
        return super.onCreateOptionsMenu(menu)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun markFavorite(isFav: Boolean): MovieDetailsPresenter {
        favItem?.setIcon(if (isFav) R.drawable.ic_star_white_selected else R.drawable.ic_star_white_24dp)
        return this
    }

    override fun presentDetails(movie: Movie): MovieDetailsPresenter {
        this.movie = movie
        Glide.with(this).load(movie.getMoviePosterPath(MoviePosterSize.w500)).into(img_postal)
        Glide.with(this).load(movie.getMovieBackDropPosterPath(MoviePosterSize.w500)).into(thumbnail)
        release_date.text = movie.getMovieReleaseDate()
        movie_title.text = movie.getMovieTitle()
        rating_txt.text = movie.getMovieVoteAverage()
        ftv.apply {
            text = movie.getMovieOverview()
            setTextSize(24f)
            textColor = ContextCompat.getColor(this@MovieDetailedActivity, R.color.white)
        }
        rating.rating = movie.getMovieMovieRating()
        return this
    }
}
