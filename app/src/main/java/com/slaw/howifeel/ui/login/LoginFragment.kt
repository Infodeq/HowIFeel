package com.slaw.howifeel.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.ISlidePolicy
import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.R
import com.slaw.howifeel.component.DaggerLoginComponent
import com.slaw.howifeel.ui.intro.IntroActivity
import com.slaw.howifeel.ui.otp.OtpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import javax.inject.Inject


class LoginFragment : Fragment(), LoginContract.View, ISlidePolicy {

//    @Inject
//    lateinit var presenter: LoginContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DaggerLoginComponent.builder()
//            .appComponent(appComponent())
//            .view(this)
//            .build()
//            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        presenter.start()
        setupView()
    }

//    override fun enableLoginButton(enable: Boolean) {
//        login.isEnabled = enable
//    }

    private fun setupView() {
    }

//    override fun openOtpActivity() {
//        startActivity<OtpActivity>()
//    }

    private fun getGenderFromRadioButton(): String {
        return when(gender.checkedRadioButtonId){
            R.id.male -> "M"
            R.id.female -> "F"
            else -> "U"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter.detach()
    }
    override fun showError(message: String) {
        requireActivity().toast(message)
    }

    override fun isPolicyRespected(): Boolean {
        (activity as IntroActivity).gender = getGenderFromRadioButton()
        (activity as IntroActivity).yOB = yearBirth.text.toString()
        return yearBirth.text.toString().isNotEmpty()
    }

    override fun onUserIllegallyRequestedNextPage() {
        requireActivity().toast(R.string.we_need_this_info)
    }
}
fun appComponent() = HowIFeelApplication.injectorComponent