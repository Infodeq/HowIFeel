package com.slaw.howifeel.ui.base


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.slaw.howifeel.R
import org.jetbrains.anko.toast


open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.privacy_policy -> {
                openWebPage()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun openWebPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Infodeq/Docs/blob/master/privacy-policy/Privacy.md"))
        try {
            startActivity(browserIntent)
        }catch (e: ActivityNotFoundException){
            toast("Cant find any app to open webpage.")
        }

    }
}