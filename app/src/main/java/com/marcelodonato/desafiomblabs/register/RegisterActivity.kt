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
        val name = binding.etNameRegister.validate()
        val email = binding.etEmailRegister.validate()
        val password = binding.etPasswordRegister.validate()
        val confirmPassword =
            binding.etConfirmPasswordRegister.getEditText() != binding.etPasswordRegister.getEditText()
        val passwordLength = binding.etPasswordRegister.length() !in 6..63
        val confirmEmail = confirmIfEmailIsValid(binding.etEmailRegister.getEditText())

        when {
            name -> binding.etNameRegister.error =
                getString(R.string.generic_error_edit_text, getString(R.string.register_name))
            email -> binding.etEmailRegister.error =
                getString(R.string.generic_error_edit_text, getString(R.string.email))
            confirmEmail -> binding.etEmailRegister.error = getString(R.string.email_error)
            password -> binding.etPasswordRegister.error =
                getString(R.string.generic_error_edit_text, getString(R.string.password))
            passwordLength -> binding.etPasswordRegister.error =
                getString(R.string.password_error_length)
            confirmPassword -> binding.etConfirmPasswordRegister.error =
                getString(R.string.confirm_password)
            else -> {
                registerToFirebase(
                    binding.etEmailRegister.getEditText(),
                    binding.etPasswordRegister.getEditText()
                )
            }
        }
    }

    private fun registerToFirebase(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { finish() }
            .addOnFailureListener { errorCase() }
    }

    private fun registerButton() {
        binding.btnRegister.setOnClickListener {
            validateFields()
        }
    }

    private fun goBackToLogin() {
        binding.tvRegister.setOnClickListener {
            finish()
        }
    }

    private fun errorCase() {
        Toast.makeText(this, getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show()
    }
}