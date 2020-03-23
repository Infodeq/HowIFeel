package com.slaw.howifeel.ui.main

interface MainActivityContract {
    interface View {
        fun showMessage(message: String)
        fun openThankyouScreen()
    }

    interface Presenter {
        fun detach()
        fun start()
        fun submitClicked(
            gps: DoubleArray,
            hasNoSymptoms: Boolean,
            hasCough: Boolean,
            hasShortBreath: Boolean,
            hasFever: Boolean,
            hasRunnyNose: Boolean,
            hasSneezing: Boolean,
            hasSoreThroat: Boolean,
            hasHeadache: Boolean,
            hasFatigue: Boolean,
            hasChills: Boolean,
            hasBodyAche: Boolean,
            hasDiarrehea: Boolean,
            hasVomiting: Boolean,
            hasAbdominalPain: Boolean
        )
    }
}