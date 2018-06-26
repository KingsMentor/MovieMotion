package xyz.belvi.motion

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.preferences.getFilterType
import xyz.belvi.motion.preferences.setFilterType

/**
 * Created by Nosa Belvi on 6/26/18.
 */
class PrefExtTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getTargetContext()

    }

    @Test
    fun filerType() {
        var expected = MovieFilter.POPULAR
        context.let {
            it.setFilterType(expected)
            Assert.assertEquals(expected, it.getFilterType())
            expected = MovieFilter.TOP_RATED
            it.setFilterType(expected)
            Assert.assertEquals(expected, it.getFilterType())
        }
    }
}