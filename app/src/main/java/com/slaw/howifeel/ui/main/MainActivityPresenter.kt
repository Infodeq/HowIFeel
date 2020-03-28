package com.slaw.howifeel.ui.main

import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.data.api.payload.symptomPost.CoarseLocation
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import dagger.Binds
import dagger.Module
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@Module
abstract class MainActivityModule {
    @Binds
    abstract fun provideMainPresenter(presenter: MainActivityPresenter): MainActivityContract.Presenter
}
class MainActivityPresenter @Inject constructor(
    val dataManager: DataManager,
    var view: MainActivityContract.View?
): MainActivityContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun detach() {
        compositeDisposable.dispose()
        view = null
    }

    override fun start() {

    }

    override fun submitClicked(
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
    ) {
        val symptomList = mutableListOf<String>()
        view?.enableSubmit(false)
        if(hasCough){
            symptomList.add("cough")
        }
        if(hasShortBreath){
            symptomList.add("short breath")
        }
        if(hasFever){
            symptomList.add("fever")
        }
        if(hasRunnyNose){
            symptomList.add("runny nose")
        }
        if(hasSneezing){
            symptomList.add("sneezing")
        }
        if(hasSoreThroat){
            symptomList.add("sore throat")
        }
        if(hasHeadache){
            symptomList.add("headache")
        }
        if(hasFatigue){
            symptomList.add("fatigue")
        }
        if(hasChills){
            symptomList.add("chills")
        }
        if(hasBodyAche){
            symptomList.add("body aches")
        }
        if(hasDiarrehea){
            symptomList.add("diarrhea")
        }
        if(hasVomiting){
            symptomList.add("vomiting")
        }
        if(hasAbdominalPain){
            symptomList.add("abdominal pain")
        }
        val symptomRequest = SymptomRequest(
            CoarseLocation.fromGps(gps),
            symptomList
        )
        compositeDisposable.add(
            dataManager.sendSymptom(symptomRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        dataManager.shownSymptom = true
//                        view?.showMessage("Thank you for your response")
                        view?.openHeatmapActivity()
                        view?.enableSubmit(true)
                    }, onError = {
                        Timber.e(it.localizedMessage)
                        view?.showMessage("${it.localizedMessage}\nSomething went wrong. Please try again.")
                        view?.enableSubmit(true)
                    }
                )
        )

    }


}