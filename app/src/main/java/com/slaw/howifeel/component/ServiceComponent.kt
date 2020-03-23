package com.slaw.howifeel.component

import com.slaw.howifeel.jobs.AlarmReceiver
import com.slaw.howifeel.module.ServiceModule
//import com.theseus.willpower.jobs.UpdateScoreInBackground
import dagger.Subcomponent

@Subcomponent(modules = [ServiceModule::class])
interface ServiceComponent {
    fun injectAlarmNotification(alarmReceiver: AlarmReceiver)
}