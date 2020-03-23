package com.slaw.howifeel.ui.otp

interface OtpContract {
    interface View {
        fun showError(error: String)
        fun openHowYouFeelingActivity()
    }

    interface Presenter {
        fun detach()
        fun start()
        fun otpEntered(code: String)
    }
}