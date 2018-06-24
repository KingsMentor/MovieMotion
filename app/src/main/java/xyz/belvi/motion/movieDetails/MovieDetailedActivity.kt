package xyz.belvi.motion.movieDetails

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_movie_detailed.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.Movie
import xyz.belvi.motion.movieDetails.presenter.MovieDetailsPresenter

class MovieDetailedActivity : AppCompatActivity(), MovieDetailsPresenter {


    companion object {

        val MOVIE_KEY = "xyz.belvi.motion.views.activities.MovieDetailedActivty.MOVIE_KEY"

    }

    lateinit var favItem: MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detailed)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
//        else
//            handleFavItemClick(item)
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

    override fun markFavorite(isFav: Boolean) {
        favItem.setIcon(if (isFav) R.drawable.ic_star_white_selected else R.drawable.ic_star_white_24dp)
    }

    override fun presentDetails(movie: Movie) {
    }
}
