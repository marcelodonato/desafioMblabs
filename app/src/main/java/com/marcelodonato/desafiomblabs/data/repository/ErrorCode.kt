package com.marcelodonato.desafiomblabs.data.repository


import com.marcelodonato.desafiomblabs.R


enum class ErrorCode(val value: String, val stringCode: Int) {

    INVALID("Invalid", R.string.error_generic);

    companion object {
        fun fromString(value: String?) = values().find { it.value == value } ?: INVALID
    }
}
