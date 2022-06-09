package com.marcelodonato.desafiomblabs.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.confirmIfEmailIsValid
import com.marcelodonato.desafiomblabs.common.extension.getEditText
import com.marcelodonato.desafiomblabs.common.extension.validate
import com.marcelodonato.desafiomblabs.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        registerButton()
        goBackToLogin()

    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.root
    }

    private fun validateFields() {
        val name = binding.userName.validate()
        val email = binding.registerEmail.validate()
        val password = binding.registerPassword.validate()
        val confirmPassword =
            binding.registerConfirmPassword.getEditText() != binding.registerPassword.getEditText()
        val passwordLength = binding.registerPassword.length() !in 6..63
        val confirmEmail = confirmIfEmailIsValid(binding.registerEmail.getEditText())

        when {
            name -> binding.userName.error = getString(R.string.generic_error_edit_text)
            email -> binding.registerEmail.error = getString(R.string.generic_error_edit_text)
            confirmEmail -> binding.registerEmail.error = getString(R.string.email_error)
            password -> binding.registerPassword.error = getString(R.string.generic_error_edit_text)
            passwordLength -> binding.registerPassword.error =
                getString(R.string.password_error_length)
            confirmPassword -> binding.registerConfirmPassword.error =
                getString(R.string.confirm_password)

            else -> {
                registerToFirebase(binding.registerEmail.getEditText(), binding.registerPassword.getEditText())
            }
        }
    }

    private fun registerToFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email , password)
            .addOnCompleteListener { finish() }
            .addOnFailureListener { errorCase() }
    }

    private fun registerButton(){
        binding.btnRegister.setOnClickListener {
            validateFields()
        }
    }

    private fun goBackToLogin(){
        binding.txtRegister.setOnClickListener {
            finish()
        }
    }

    private fun errorCase() {
        Toast.makeText(this, getString(R.string.unexpected_error),Toast.LENGTH_SHORT).show()
    }
}