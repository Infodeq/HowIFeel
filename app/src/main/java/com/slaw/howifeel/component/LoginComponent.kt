package com.slaw.howifeel.component

import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.module.ApplicationModule
import com.slaw.howifeel.module.LoginModule
import com.slaw.howifeel.module.NetworkModule
import com.slaw.howifeel.ui.login.LoginActivity
import com.slaw.howifeel.ui.login.LoginContract
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [LoginModule::class]
)
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
    fun presenter(): LoginContract.Presenter
    @Component.Builder
    interface Builder {
        fun build(): LoginComponent
        fun appComponent(component: ApplicationComponent): Builder

        @BindsInstance
        fun view(view: LoginContract.View?): Builder
    }
}