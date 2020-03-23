package com.slaw.howifeel.ui.thankyou

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slaw.howifeel.R
import kotlinx.android.synthetic.main.activity_thankyou.*

class ThankyouActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)
        done.setOnClickListener {
            finish()
        }
    }
}
