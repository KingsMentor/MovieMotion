package xyz.belvi.motion.movieDetails

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detailed.*
import kotlinx.android.synthetic.main.content_movie_detailed_activty.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.FavMovie
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.models.enums.MoviePosterSize
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter
import xyz.belvi.motion.movieDetails.trailers.TrailersFragment
import xyz.belvi.motion.movieDetails.viewModel.MovieDetailsVM
import xyz.belvi.motion.preferences.getFilterType


/**
 * Created by Belvi on 6/24/18.
 *
 * @MovieDetailedActivity is a detailed activity for selected movie
 *
 */

class MovieDetailedActivity : AppCompatActivity(), MovieDetailsPresenter {


    companion object {

        val MOVIE_KEY = "xyz.belvi.motion.views.activities.MovieDetailedActivty.MOVIE_KEY"

    }

    var favItem: MenuItem? = null
    private lateinit var movieDetailsVM: MovieDetailsVM

    // get movied  passed via intent. This is optional because it can be null
    private fun movie(): Movie? = intent.getParcelableExtra(MOVIE_KEY)

    // custom interaction of toolbar scr
    private fun initCustomTitleInteraction() {
        layout_title.post({
            val layoutParams = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
            layoutParams.height = layout_title.height
            toolbar.layoutParams = layoutParams
        })

        app_bar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            // to get an adequate midpoint for the custom title
            val mid = (toolbar_layout.width / 2) - (movie_title.width / 2)
            val point = (Math.abs(verticalOffset) * mid) / appBarLayout.totalScrollRange
            // scale font so it reduces as the user scrolls the page upwards. Range is from 0 to 14.
            val font = (Math.abs(verticalOffset) * 14) / appBarLayout.totalScrollRange

            // adjust the x position of the movie_title in the toolbar
            movie_title.x = point.toFloat()
            // apply scale font to movie_title
            movie_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32f - font)
        }
    }

    // update views with data from movie object
    private fun presentDetails(movie: Movie) {
        val stubMovie = FavMovie(movie.id, movie)
        Glide.with(this).load(stubMovie.getMoviePosterPath(MoviePosterSize.w500)).into(img_postal)
        Glide.with(this).load(stubMovie.getMovieBackDropPosterPath(MoviePosterSize.w342)).into(thumbnail)
        release_date.text = stubMovie.getMovieReleaseDate()
        movie_title.text = stubMovie.getMovieTitle()
        rating_txt.text = stubMovie.getMovieVoteAverage()
        ftv.apply {
            text = stubMovie.getMovieOverview()
            setTextSize(24f)
            textColor = ContextCompat.getColor(this@MovieDetailedActivity, R.color.white)
        }
        rating.rating = stubMovie.getMovieMovieRating()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detailed)
        setSupportActionBar(toolbar)
        initCustomTitleInteraction()
        // apply chanhes to supportActionBar
        supportActionBar?.apply {
            title = ""
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        // initialise livedata viewModel
        movieDetailsVM = ViewModelProviders.of(this).get(MovieDetailsVM::class.java)
        // onlt bind to viewModel if movie is available (not null)
        movie()?.let {
            movieDetailsVM.bind(this@MovieDetailedActivity)
            // initiate a fragment trasaction for trailers , passing the movie id as a params,
            supportFragmentManager.beginTransaction()
                    .replace(R.id.trailers_and_reviews, TrailersFragment().newInstance(it.id))
                    .commitAllowingStateLoss()
            presentDetails(it)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // go back to MainActivity
            onBackPressed()
        } else {
            item.isChecked = !item.isChecked
            movie()?.let {
                // add to favorite if checked or remove from favorite if not checked.
                movieDetailsVM.addToFavoriteList(FavMovie(it.id, it), item.isChecked)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie_detailed_activty, menu)
        favItem = menu.findItem(R.id.action_fav)
        // only show fav menu item if movie is not of type FavoriteMovie
        favItem?.isVisible = getFilterType() != MovieFilter.FAVORITE
        movie()?.let {
            movieDetailsVM.updateCheck(it.id)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun markFavorite(isFav: Boolean): MovieDetailsPresenter {
        // update favItem (menuItem) UI
        favItem?.setIcon(if (isFav) R.drawable.ic_star_white_selected else R.drawable.ic_star_white_24dp)
        return this
    }


    // render all font with calligraphy default app font set in MotionApp
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


}
