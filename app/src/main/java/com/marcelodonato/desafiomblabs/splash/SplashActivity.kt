package com.marcelodonato.desafiomblabs.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.cadasterEvent.RegisterEventActivity
import com.marcelodonato.desafiomblabs.home.HomeActivity
import com.marcelodonato.desafiomblabs.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(
            {
                startWelcome()
            }, 4000.toLong()
        )
    }


    private fun startWelcome() {
        val intent = Intent(baseContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

}