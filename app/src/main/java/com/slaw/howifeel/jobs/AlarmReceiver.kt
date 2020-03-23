package com.slaw.howifeel.jobs

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.R
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.ui.login.LoginActivity
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("calender boom")
        HowIFeelApplication.injectorComponent
            .serviceComponent()
            .injectAlarmNotification(this)
        if(dataManager.shownSymptom) {
            setNotification(context, intent)
        }
    }

    @Inject
    lateinit var dataManager: DataManager
    private fun setNotification(context: Context, intent: Intent) {
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationIntent = Intent(context, LoginActivity::class.java).apply {
//            this.putExtra(MainActivity.OPENED_FROM_NOTIFICATION, true)
        }
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mNotifyBuilder = NotificationCompat.Builder(
            context,CHANNEL_ID
        ).setSmallIcon(R.drawable.how_i_feel)
            .setContentTitle(context.getString(R.string.how_i_feel))
            .setContentText(context.getString(R.string.dont_forget_to_log_symptoms)).setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        notificationManager.notify(0, mNotifyBuilder.build())
    }
}
const val CHANNEL_ID = "howifeel_notif_channel"