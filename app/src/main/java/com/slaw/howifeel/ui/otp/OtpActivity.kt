package com.slaw.howifeel.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slaw.howifeel.ui.main.MainActivity
import com.slaw.howifeel.R
import com.slaw.howifeel.component.ActivityScope
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.howyoufeeling.HowAreYouFeelingActivity
import com.slaw.howifeel.ui.login.appComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.android.synthetic.main.activity_otp.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject


@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [OtpModule::class]
)
interface OtpComponent {
    fun inject(loginActivity: OtpActivity)
    fun presenter(): OtpContract.Presenter
    @Component.Builder
    interface Builder {
        fun build(): OtpComponent
        fun appComponent(component: ApplicationComponent): Builder

        @BindsInstance
        fun view(view: OtpContract.View?): Builder
    }
}
class OtpActivity : BaseActivity(), OtpContract.View {

    @Inject
    lateinit var presenter: OtpContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerOtpComponent.builder()
            .appComponent(appComponent())
            .view(this)
            .build()
            .inject(this)
        setContentView(R.layout.activity_otp)
        supportActionBar?.setHomeButtonEnabled(true)
        presenter.start()
        setupView()

    }

    private fun setupView() {
        next.setOnClickListener {
            presenter.otpEntered(codeText.text.toString())
        }
    }
    override fun openHowYouFeelingActivity() {
        startActivity<HowAreYouFeelingActivity>()
        finish()
    }

    override fun showError(error: String) {
        toast(error)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}
