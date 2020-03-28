package com.slaw.howifeel.ui.howyoufeeling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.slaw.howifeel.R
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.data.api.payload.symptomPost.CoarseLocation
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.heatmap.HeatMapActivity
import com.slaw.howifeel.ui.login.appComponent
import com.slaw.howifeel.ui.main.MainActivity
import com.slaw.howifeel.ui.main.getGPS
import com.slaw.howifeel.ui.thankyou.ThankyouActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_how_are_you_feeling.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

class HowAreYouFeelingActivity : BaseActivity() {


    @Inject
    lateinit var dataManager: DataManager
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_are_you_feeling)
        setupView()
        appComponent().inject(this)
    }

    private fun setupView() {
        im_feeling_good.setOnClickListener {
            sendEmptySymptoms()
        }
        im_having_some_symptoms.setOnClickListener {
            startActivity<MainActivity>()
        }
    }

    private fun sendEmptySymptoms() {
        val gps = getGPS(this)
        val coarseLocation = CoarseLocation.fromGps(gps)
        im_feeling_good.isEnabled = false
        compositeDisposable.add(
            dataManager.sendSymptom(SymptomRequest(
                    coarseLocation, emptyList()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = {
                        im_feeling_good.isEnabled = true
                        startActivity<HeatMapActivity>()
                    }, onError = {
                        im_feeling_good.isEnabled = true
                        toast(it.localizedMessage?:"Something went wrong. Please try again")
                    }
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
