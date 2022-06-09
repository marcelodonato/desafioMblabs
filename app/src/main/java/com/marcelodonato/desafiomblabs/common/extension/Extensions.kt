package com.marcelodonato.desafiomblabs.common.extension

import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern


fun EditText.getEditText() = text.toString().trim()
fun EditText.validate() = text.toString().trim().isEmpty()

fun confirmIfEmailIsValid(email: String): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches().not()
}