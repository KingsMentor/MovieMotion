package xyz.belvi.motion.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import xyz.belvi.motion.R
import xyz.belvi.motion.movieMain.MainActivity

/**
 * Created by Nosa Belvi on 6/26/18.
 *
 * @NotificationUtils exists to solely show fragmentation issues in android and how it can be handled
 * in this class, we can see how notification build is handled differently where Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
 */


object NotificationUtils {


    private lateinit var notifManager: NotificationManager


    fun notify(aMessage: String, mContext: Context) {


        notifManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            N_NotificationChannel(mContext)
        } else {
            NotificationCompat.Builder(mContext)

        }
        builder.setContentTitle(aMessage)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentText(mContext.getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setTicker(aMessage)
        val notification = builder.build()
        notifManager.notify(mContext.getString(R.string.app_name).hashCode(), notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun N_NotificationChannel(mContext: Context): NotificationCompat.Builder {
        val importance = NotificationManager.IMPORTANCE_HIGH
        var mChannel: NotificationChannel? = notifManager.getNotificationChannel(mContext.packageName)
        mChannel ?: kotlin.run {
            mChannel = NotificationChannel(mContext.packageName, mContext.getString(R.string.app_name), importance)
            mChannel?.apply {
                description = mContext.getString(R.string.app_name)
                enableVibration(true)
            }
            notifManager.createNotificationChannel(mChannel)
        }


        return NotificationCompat.Builder(mContext, mContext.packageName)
    }
}
