package com.slaw.howifeel.ui.otp

interface OtpContract {
    interface View {
        fun showError(error: String)
        fun openHowYouFeelingActivity()
        fun enableNextButton(enable: Boolean)
    }

    interface Presenter {
        fun detach()
        fun start()
        fun otpEntered(code: String)
    }
}