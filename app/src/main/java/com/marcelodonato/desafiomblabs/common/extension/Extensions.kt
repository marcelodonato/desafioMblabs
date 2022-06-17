package com.marcelodonato.desafiomblabs.common.extension

import android.app.Activity
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

fun EditText.getEditText() = text.toString().trim()
fun EditText.validate() = text.toString().trim().isEmpty()
fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun confirmIfEmailIsValid(email: String): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches().not()
}