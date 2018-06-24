package xyz.belvi.motion.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import io.realm.Realm
import io.realm.RealmConfiguration
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import xyz.belvi.motion.R

/**
 * Created by zone2 on 6/23/18.
 */
open class MotionApp : Application() {


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

    private fun realmInit() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().name(getString(R.string.realm_db_name)).build()
        Realm.setDefaultConfiguration(config)
    }

    private fun defineCalligraphy() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source_code.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}