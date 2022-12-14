package com.marcelodonato.desafiomblabs.presentation.splash.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.marcelodonato.desafiomblabs.presentation.base.BaseActivity
import com.marcelodonato.desafiomblabs.presentation.base.BaseViewModel
import com.marcelodonato.desafiomblabs.common.extension.viewBinding
import com.marcelodonato.desafiomblabs.databinding.ActivitySplashBinding
import com.marcelodonato.desafiomblabs.presentation.home.view.HomeActivity
import com.marcelodonato.desafiomblabs.presentation.login.view.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<BaseViewModel>() {

    override val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(
            { checkConnected() }, 3000.toLong()
        )
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