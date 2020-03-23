package com.slaw.howifeel.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.ui.main.MainActivity
import com.slaw.howifeel.R
import com.slaw.howifeel.component.DaggerLoginComponent
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.howyoufeeling.HowAreYouFeelingActivity
import com.slaw.howifeel.ui.otp.OtpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerLoginComponent.builder()
            .appComponent(appComponent())
            .view(this)
            .build()
            .inject(this)
        setContentView(R.layout.activity_login)
        presenter.start()
        setupView()
    }

    private fun setupView() {
        login.setOnClickListener {
            val gender = getGenderFromRadioButton()
            presenter.loginClicked(
                ccp.selectedCountryCode, phonenumber.text.toString(), gender,yearBirth.text.toString()
            )
        }
    }

    override fun openOtpActivity() {
        startActivity<OtpActivity>()
    }

    private fun getGenderFromRadioButton(): String {
        return when(gender.checkedRadioButtonId){
            R.id.male -> "male"
            R.id.female -> "female"
            else -> "na"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun openHowYouFeelingActivity() {
        startActivity(Intent(this, HowAreYouFeelingActivity::class.java))
        finish()
    }

    override fun showError(message: String) {
        toast(message)
    }
}
fun appComponent() = HowIFeelApplication.injectorComponent