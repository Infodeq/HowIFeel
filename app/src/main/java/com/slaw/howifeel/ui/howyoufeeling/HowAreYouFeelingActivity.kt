package com.slaw.howifeel.ui.howyoufeeling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slaw.howifeel.R
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.main.MainActivity
import com.slaw.howifeel.ui.thankyou.ThankyouActivity
import kotlinx.android.synthetic.main.activity_how_are_you_feeling.*
import org.jetbrains.anko.startActivity

class HowAreYouFeelingActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_are_you_feeling)
        setupView()
    }

    private fun setupView() {
        im_feeling_good.setOnClickListener {
            startActivity<ThankyouActivity>()
            finish()
        }
        im_having_some_symptoms.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }
}
