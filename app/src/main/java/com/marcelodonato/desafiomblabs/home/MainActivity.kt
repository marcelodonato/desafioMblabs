package com.marcelodonato.desafiomblabs.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.databinding.ActivityLoginBinding
import com.marcelodonato.desafiomblabs.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        clickToRegister()


    }

    private fun startBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.root
    }


    private fun clickToRegister(){
        binding.txtLogin.setOnClickListener {
            goToRegisterActivity()
        }
    }


    private fun goToRegisterActivity(){
        val loginIntent = Intent(this, RegisterActivity::class.java)
        startActivity(loginIntent)
    }

}