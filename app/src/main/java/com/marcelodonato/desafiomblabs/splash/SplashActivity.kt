package com.marcelodonato.desafiomblabs.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.databinding.ActivitySplashBinding
import com.marcelodonato.desafiomblabs.home.HomeActivity
import com.marcelodonato.desafiomblabs.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        Handler().postDelayed(
            {
                checkConnected()
            }, 3000.toLong()
        )
    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.root
    }

    private fun startWelcome() {
        val intent = Intent(baseContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun startLogin() {
        val intent = Intent(baseContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun checkConnected() {
        val user = FirebaseAuth.getInstance().currentUser;
        if (user != null) {
            startWelcome()
        } else {
            startLogin()
        }
    }

}