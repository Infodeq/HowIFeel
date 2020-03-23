package com.slaw.howifeel

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.multidex.MultiDexApplication
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.component.DaggerApplicationComponent
import com.slaw.howifeel.jobs.AlarmReceiver
import com.slaw.howifeel.jobs.CHANNEL_ID
import com.slaw.howifeel.module.ApplicationModule
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import java.util.*

class HowIFeelApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())
        injectorComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        injectorComponent.inject(this)
        createNotificationChannel(
            this
        )
        setupRepeatingAlarm(this)
    }
    companion object {
        internal lateinit var injectorComponent: ApplicationComponent
    }
    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.reminder_to_log_symptoms)
//            val descriptionText = getString(R.string.badges_incentives_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

fun setupRepeatingAlarm(context: Context) {
    var _alarm: Long = 0
    val now = Calendar.getInstance()
    val alarm = Calendar.getInstance()
    alarm.set(Calendar.HOUR_OF_DAY, 19)
    alarm.set(Calendar.MINUTE, 30)
    alarm.set(Calendar.SECOND, 20)
    _alarm = if(alarm.timeInMillis <= now.timeInMillis)
        alarm.timeInMillis + (AlarmManager.INTERVAL_DAY+1);
    else
        alarm.timeInMillis;

    val intent1 = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent1,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    am.setRepeating(
        AlarmManager.RTC_WAKEUP,
        _alarm,
        AlarmManager.INTERVAL_HALF_DAY,
        pendingIntent
    )
}