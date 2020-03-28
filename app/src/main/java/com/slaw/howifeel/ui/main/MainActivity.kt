package com.slaw.howifeel.ui.main

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.slaw.howifeel.R
import com.slaw.howifeel.component.ActivityScope
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.heatmap.HeatMapActivity
import com.slaw.howifeel.ui.login.appComponent
import com.slaw.howifeel.ui.thankyou.ThankyouActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.jar.Manifest
import javax.inject.Inject


@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun presenter(): MainActivityContract.Presenter
    @Component.Builder
    interface Builder {
        fun build(): MainActivityComponent
        fun appComponent(component: ApplicationComponent): Builder

        @BindsInstance
        fun view(view: MainActivityContract.View?): Builder
    }
}
class MainActivity : BaseActivity(),MainActivityContract.View, CompoundButton.OnCheckedChangeListener {

    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMainActivityComponent.builder()
            .appComponent(appComponent())
            .view(this)
            .build()
            .inject(this)
        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest();
                }
            }).check()
        setContentView(R.layout.activity_main)
        supportActionBar?.setHomeButtonEnabled(true)
        setupView()
    }

    override fun enableSubmit(enable: Boolean) {
        submit.isEnabled = enable
    }

    override fun openHeatmapActivity() {
        startActivity<HeatMapActivity>()
        finish()
    }

    private fun setupView() {
        no_symptoms.setOnCheckedChangeListener(this)
        cough.setOnCheckedChangeListener(this)
        short_breath.setOnCheckedChangeListener(this)
        fever.setOnCheckedChangeListener(this)
        runny_nose.setOnCheckedChangeListener(this)
        sneezing.setOnCheckedChangeListener(this)
        sore_throat.setOnCheckedChangeListener(this)
        headache.setOnCheckedChangeListener(this)
        fatigue.setOnCheckedChangeListener(this)
        chills.setOnCheckedChangeListener(this)
        body_ache.setOnCheckedChangeListener(this)
        diarrehea.setOnCheckedChangeListener(this)
        vomiting.setOnCheckedChangeListener(this)
        abdominal_pain.setOnCheckedChangeListener(this)

        submit.setOnClickListener {
            presenter.submitClicked(getGPS(this),
                no_symptoms.isChecked,
                cough.isChecked,
                short_breath.isChecked, fever.isChecked, runny_nose.isChecked,
                sneezing.isChecked, sore_throat.isChecked, headache.isChecked, fatigue.isChecked,
                chills.isChecked, body_ache.isChecked, diarrehea.isChecked, vomiting.isChecked,
                abdominal_pain.isChecked)
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }


    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        if(compoundButton.id!=R.id.no_symptoms){
            if(no_symptoms.isChecked&&isChecked){
                no_symptoms.isChecked = false
            }
        }else{
            if(isChecked){
                cough.isChecked = false
                short_breath.isChecked = false
                fever.isChecked = false
                runny_nose.isChecked = false
                sneezing.isChecked = false
                sore_throat.isChecked = false
                headache.isChecked = false
                fatigue.isChecked = false
                chills.isChecked = false
                body_ache.isChecked = false
                diarrehea.isChecked = false
                vomiting.isChecked= false
                abdominal_pain.isChecked= false
            }
        }
    }


}
fun getGPS(context: Context): DoubleArray {
    val lm = context.getSystemService(
        Context.LOCATION_SERVICE
    ) as LocationManager
    val providers = lm.getProviders(true)
    var l: Location? = null
    try{
        for (i in providers.indices.reversed()) {
            l = lm.getLastKnownLocation(providers[i])
            if (l != null) break
        }
    }catch (e: SecurityException){
    }

    val gps = DoubleArray(2)
    if (l != null) {
        gps[0] = l.latitude
        gps[1] = l.longitude
    }
    return gps
}
