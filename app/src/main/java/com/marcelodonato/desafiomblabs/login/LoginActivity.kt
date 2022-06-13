package com.marcelodonato.desafiomblabs.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.confirmIfEmailIsValid
import com.marcelodonato.desafiomblabs.common.extension.getEditText
import com.marcelodonato.desafiomblabs.common.extension.validate
import com.marcelodonato.desafiomblabs.databinding.ActivityLoginBinding
import com.marcelodonato.desafiomblabs.home.HomeActivity
import com.marcelodonato.desafiomblabs.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        clickToRegister()
        validatedLogin()
    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.root
    }

    private fun validateFields() {
        val email = binding.etEmailLogin.validate()
        val confirmEmail = confirmIfEmailIsValid(binding.etEmailLogin.getEditText())
        val password = binding.etPasswordLogin.validate()

        when {
            email -> binding.etEmailLogin.error =
                getString(R.string.generic_error_edit_text, getString(R.string.email))
            confirmEmail -> binding.etEmailLogin.error = getString(R.string.email_error)
            password -> binding.etPasswordLogin.error = getString(R.string.password_error)
            else -> {
                loginFirebase(
                    binding.etEmailLogin.getEditText(),
                    binding.etPasswordLogin.getEditText()
                )
            }
        }
    }

    private fun loginFirebase(email: String, password: String) {
        val firebase = FirebaseAuth.getInstance()
        firebase.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startHome()
                } else {
                    Toast.makeText(this, "Conta não encontrada", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun startHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun validatedLogin() {
        binding.btnLogin.setOnClickListener {
            validateFields()
        }
    }

    private fun clickToRegister() {
        binding.tvLogin.setOnClickListener {
            goToRegisterActivity()
        }
    }

    private fun goToRegisterActivity() {
        val loginIntent = Intent(this, RegisterActivity::class.java)
        startActivity(loginIntent)
    }
}