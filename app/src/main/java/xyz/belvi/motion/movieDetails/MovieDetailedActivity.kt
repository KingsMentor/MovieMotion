package xyz.belvi.motion.movieDetails

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detailed.*
import kotlinx.android.synthetic.main.content_movie_detailed_activty.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.models.enums.MoviePosterSize
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter
import xyz.belvi.motion.movieDetails.trailers.TrailersFragment
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

    private fun initCustomTitleInteraction() {
        layout_title.post({
            val layoutParams = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            layoutParams.height = layout_title.height
            toolbar.layoutParams = layoutParams
        })

        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            val mid = (toolbar_layout.width / 2) - (movie_title.width / 2)
            val point = (Math.abs(verticalOffset) * mid) / appBarLayout.totalScrollRange
            val font = (Math.abs(verticalOffset) * 14) / appBarLayout.totalScrollRange

            movie_title.x = point.toFloat()
            movie_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f - font)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detailed)
        setSupportActionBar(toolbar)
        initCustomTitleInteraction()
        supportActionBar?.apply {
            title = ""
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
            supportFragmentManager.beginTransaction()
                    .replace(R.id.trailers_and_reviews, TrailersFragment().newInstance(it))
                    .commitAllowingStateLoss()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        else {
            item.isChecked = !item.isChecked
            movieDetailsVM.addToFavoriteList(movie, item.isChecked)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie_detailed_activty, menu)
        favItem = menu.findItem(R.id.action_fav)
        movieId()?.let {
            movieDetailsVM.updateCheck(it)
        }
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
        Glide.with(this).load(movie.getMovieBackDropPosterPath(MoviePosterSize.w342)).into(thumbnail)
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
