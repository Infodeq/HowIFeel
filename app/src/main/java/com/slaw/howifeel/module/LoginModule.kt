package com.slaw.howifeel.module

import com.slaw.howifeel.ui.login.LoginContract
import com.slaw.howifeel.ui.login.LoginPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LoginModule {
    @Binds
    abstract fun providesPresenter(presenter: LoginPresenter): LoginContract.Presenter
}
