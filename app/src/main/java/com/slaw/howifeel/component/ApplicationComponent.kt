package com.slaw.howifeel.component

import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.module.ApplicationModule
import com.slaw.howifeel.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(application: HowIFeelApplication)
//    fun loginComponent(): LoginComponent
    fun dataManager(): DataManager

    fun serviceComponent(): ServiceComponent
}