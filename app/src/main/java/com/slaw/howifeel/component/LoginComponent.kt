package com.slaw.howifeel.component

import com.slaw.howifeel.module.LoginModule
import com.slaw.howifeel.ui.login.LoginFragment
import com.slaw.howifeel.ui.login.LoginContract
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [LoginModule::class]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
    fun presenter(): LoginContract.Presenter
    @Component.Builder
    interface Builder {
        fun build(): LoginComponent
        fun appComponent(component: ApplicationComponent): Builder

        @BindsInstance
        fun view(view: LoginContract.View?): Builder
    }
}