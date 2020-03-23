package com.slaw.howifeel.ui.login

interface LoginContract {
    interface View {
        fun openHowYouFeelingActivity()
        fun showError(message: String)
        fun openOtpActivity()
    }

    interface Presenter {
        fun start()
        fun detach()
        fun loginClicked(countryCode: String, phoneNumber: String, gender: String, yearBirth: String)
    }
}