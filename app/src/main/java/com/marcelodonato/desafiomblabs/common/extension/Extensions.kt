package com.marcelodonato.desafiomblabs.common.extension

import android.widget.EditText


fun EditText.getEditText() = text.toString().trim()
fun EditText.validate() = text.toString().trim().isEmpty()