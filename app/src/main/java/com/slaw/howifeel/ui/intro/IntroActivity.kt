package com.slaw.howifeel.ui.intro

import android.os.Bundle
import com.github.paolorotolo.appintro.AppIntro
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.ui.howyoufeeling.HowAreYouFeelingActivity
import com.slaw.howifeel.ui.login.LoginFragment
import com.slaw.howifeel.ui.login.appComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import javax.inject.Inject

class IntroActivity : AppIntro() {

    @Inject
    lateinit var dataManager: DataManager
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        if(dataManager.clientToken.isEmpty()) {

            showSkipButton(false)
            showPagerIndicator(true)
            showSeparator(true)

            addSlide(CoronaIntroFragment())
            addSlide(ProvideLocationFragment())
            addSlide(LoginFragment())

            setVibrate(true)
            setVibrateIntensity(15)
        }else{
            finish()
            startActivity(intentFor<HowAreYouFeelingActivity>().clearTask().newTask())
        }
    }
    var gender = ""
    var yOB = ""
    override fun onDonePressed() {
        super.onDonePressed()
        compositeDisposable.add(
            dataManager.login( gender, yOB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        startActivity(intentFor<HowAreYouFeelingActivity>().clearTask().newTask())
                    }, onError = {
                        toast("Something went wrong. Please try again")
                    }
                )
        )
    }

    fun locationAccessProvided() {

    }
}
