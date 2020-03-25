package com.slaw.howifeel.ui.otp

import com.slaw.howifeel.BuildConfig
import com.slaw.howifeel.data.DataManager
import dagger.Binds
import dagger.Module
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Module
abstract class OtpModule {
    @Binds
    abstract fun providesPresenter(presenter: OtpPresenter): OtpContract.Presenter
}
class OtpPresenter @Inject constructor(
    val dataManager: DataManager,
    var view: OtpContract.View?
): OtpContract.Presenter {
    val compositeDisposable = CompositeDisposable()
    override fun detach() {
        compositeDisposable.dispose()
        view = null
    }

    override fun start() {

    }

    override fun otpEntered(code: String) {
//        if(BuildConfig.DEBUG){
//            view?.openHowYouFeelingActivity()
//            return
//        }
        if(code.isEmpty()){
            view?.showError("Please enter the code.")
            return
        }
        view?.enableNextButton(false)
       compositeDisposable.add(
           dataManager.otpEntered(code)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeBy (
                   onSuccess = {
                       view?.openHowYouFeelingActivity()
                   }, onError = {
                        view?.showError(it.localizedMessage?:"Something went wrong. Please try again")
                       view?.enableNextButton(true)
                   }
               )
       )
    }

}