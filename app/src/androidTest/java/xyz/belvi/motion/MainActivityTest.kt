package xyz.belvi.motion

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.belvi.motion.movieMain.MainActivity
import android.support.v7.widget.RecyclerView
import xyz.belvi.motion.movieDetails.MovieDetailedActivity
import xyz.belvi.motion.movieMain.adapter.MovieListAdapter


/**
 * Created by Nosa Belvi on 6/26/18.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {


    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testLaunchOfDetails() {
        if (getRVcount() != 0) {
            onView(withId(R.id.movies)).perform(RecyclerViewActions.actionOnItemAtPosition<MovieListAdapter.MovieHolder>(0, click()))
            Intents.init()
            intended(hasComponent(MovieDetailedActivity::class.java.name))
            intended(hasExtraWithKey(MovieDetailedActivity.MOVIE_KEY))
            Intents.release()
        }


    }

    private fun getRVcount(): Int {
        val recyclerView = mActivityRule.activity.findViewById(R.id.movies) as RecyclerView
        return recyclerView.adapter.itemCount
    }

}