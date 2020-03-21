package com.slaw.howifeel

import android.app.Application
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.module.ApplicationModule
import timber.log.Timber

class HowIFeelApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
//        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}