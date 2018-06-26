package xyz.belvi.motion


import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.movieMain.MainActivity
import xyz.belvi.motion.movieMain.viewModel.MoviesVM
import xyz.belvi.motion.utils.apiKey
import xyz.belvi.motion.utils.currentPage
import xyz.belvi.motion.utils.resetPageCounter
import xyz.belvi.motion.utils.updatePageCounter

/**
 * Created by Nosa Belvi on 6/26/18.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class HelperUtilsExt {


    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testApiKey() {
        val viewModel = Mockito.spy(MoviesVM())
        val expected = "e7b1f85516eecf5f36dc00942f575c6a"
        Assert.assertEquals(expected, viewModel.apiKey())
    }


    @Test
    fun testPageCounter() {
        val viewModel = Mockito.spy(MoviesVM())
        viewModel.resetPageCounter()
        val movieFilter = MovieFilter.POPULAR
        val currentPage = movieFilter.currentPage()
        movieFilter.updatePageCounter()
        Assert.assertEquals(currentPage + 1, movieFilter.currentPage())
        viewModel.resetPageCounter()
        Assert.assertEquals(1, movieFilter.currentPage())


    }

}