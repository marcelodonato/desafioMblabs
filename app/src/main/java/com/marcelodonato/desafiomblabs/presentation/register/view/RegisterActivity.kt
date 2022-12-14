package com.marcelodonato.desafiomblabs.presentation.register.view

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.base.BaseActivity
import com.marcelodonato.desafiomblabs.common.base.BaseViewModel
import com.marcelodonato.desafiomblabs.common.extension.confirmIfEmailIsValid
import com.marcelodonato.desafiomblabs.common.extension.getEditText
import com.marcelodonato.desafiomblabs.common.extension.validate
import com.marcelodonato.desafiomblabs.common.extension.viewBinding
import com.marcelodonato.desafiomblabs.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<BaseViewModel>() {

    override val binding by viewBinding(ActivityRegisterBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerButton()
        backToLogin()
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

    private fun backToLogin() {
        binding.tvRegister.setOnClickListener {
            finish()
        }
    }

    private fun errorCase() {
        Toast.makeText(this, getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show()
    }
}