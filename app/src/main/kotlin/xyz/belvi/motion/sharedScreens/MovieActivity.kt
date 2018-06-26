package xyz.belvi.motion.sharedScreens

import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.movieDetails.MovieDetailedActivity

/**
 * Created by Nosa Belvi on 6/26/18.
 *
 * @MovieActivity is abstract because it is meant to serve as a parent activity (and not to be registered in @AndroidManifest.xml)
 * to activities that can launch @MovieDetailedActivity e.g @MainActivity and @SearchActivity
 */
abstract class MovieActivity : AppCompatActivity() {

    /**
     * start MovieDetailsActivity. This is handles here
     * because both MainActivity and Search Activity calls same function.
     * To avoid duplication of code, this approach was used.
     *
     */

    fun showMovieDetails(view: View, movie: MotionMovie) {
        val p1 = Pair.create(view, getString(R.string.postal_transition_name))
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(Intent(this, MovieDetailedActivity::class.java)
                    .putExtra(MovieDetailedActivity.MOVIE_KEY, movie.getMovieItem()), optionsCompat.toBundle())
        } else {
            startActivity(Intent(this, MovieDetailedActivity::class.java)
                    .putExtra(MovieDetailedActivity.MOVIE_KEY, movie.getMovieItem()))
        }


    }
}