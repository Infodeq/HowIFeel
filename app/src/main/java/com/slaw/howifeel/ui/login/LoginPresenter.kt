package com.slaw.howifeel.ui.login

import com.slaw.howifeel.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val dataManager: DataManager,
    private var view: LoginContract.View?
): LoginContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun start() {
        if(dataManager.clientToken.isNotEmpty()){
            view?.openHowYouFeelingActivity()
        }
    }

    override fun detach() {
        view = null
        compositeDisposable.dispose()
    }

    override fun loginClicked(
        countryCode: String,
        phoneNumber: String,
        gender: String,
        yearBirth: String
    ) {
        if(phoneNumber.isEmpty()||yearBirth.isEmpty()){
            view?.showError("Please enter all the fields")
            return
        }
        compositeDisposable.add(
            dataManager.login(countryCode, phoneNumber, gender, yearBirth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        view?.openOtpActivity()
                    }, onError = {
                        view?.showError(it.localizedMessage?:"")
                    }
                )
        )

    }
}