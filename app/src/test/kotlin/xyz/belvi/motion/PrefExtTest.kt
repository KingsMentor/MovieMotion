package xyz.belvi.motion

import android.app.Application
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import xyz.belvi.motion.app.MotionApp
import xyz.belvi.motion.models.enums.MovieFilter
import xyz.belvi.motion.preferences.getFilterType
import xyz.belvi.motion.preferences.setFilterType
import kotlin.test.assertEquals
import org.mockito.ArgumentMatchers.anyInt
import android.content.Intent
import android.content.pm.PackageInfo
import org.mockito.Mockito.`when`
import android.content.pm.ResolveInfo
import android.content.pm.Signature
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowApplication
import org.robolectric.RuntimeEnvironment.application
import org.robolectric.shadows.ShadowActivityThread.getPackageManager
import java.util.*
import org.robolectric.shadows.ShadowApplicationPackageManager
import org.robolectric.annotation.Implements




/**
 * Created by zone2 on 6/24/18.
 */

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
        application = MotionApp::class,
        sdk = intArrayOf(21))
class PrefExtTest {

    @Before
    fun setupPackageManager() {
        val rpm = shadowOf(RuntimeEnvironment.application.packageManager)
        val firstPackageInfo = PackageInfo()
        firstPackageInfo.packageName = RuntimeEnvironment.systemContext.packageName
        firstPackageInfo.signatures = arrayOf(Signature("00000000"))
        rpm.addPackage(firstPackageInfo)


    }

    @Test
    fun testMovieFilterPref() {
        val context = RuntimeEnvironment.systemContext
        context.setFilterType(MovieFilter.FAVORITE)
        assertEquals(context.getFilterType(), MovieFilter.FAVORITE)
    }

    @Implements(value = MotionApp::class, inheritImplementationMethods = true)
    internal inner class MyCustomPackageManager : ShadowApplicationPackageManager()

}