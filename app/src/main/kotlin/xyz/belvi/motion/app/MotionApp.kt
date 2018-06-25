package xyz.belvi.motion.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import io.realm.Realm
import io.realm.RealmConfiguration
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import xyz.belvi.motion.R

/**
 *
 * Created by Nosa Belvi on 6/23/18.
 *
 * MotionApp extends the application class
 * for initialisation of few functionality needed for proper
 * functioning of the app
 *
 * Realm - Database
 * Calligraphy - App Font
 * MultiDex - handles exceeding of 64k methods
 *
 */
open class MotionApp : Application() {


    // singleton. provides application context access to classes without context.
    companion object {
        var instance: MotionApp? = null

    }

    override fun onCreate() {
        super.onCreate()
        instance ?: kotlin.run {
            instance = this
            realmInit()
            defineCalligraphy()
        }
    }

    // initialise realm
    private fun realmInit() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name(getString(R.string.realm_db_name)).build()
        Realm.setDefaultConfiguration(config)
    }

    // initialise calligraphy
    private fun defineCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source_code.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    // set up multiDex
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}