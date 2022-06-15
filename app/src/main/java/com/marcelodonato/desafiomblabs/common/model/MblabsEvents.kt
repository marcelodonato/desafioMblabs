package com.marcelodonato.desafiomblabs.common.model

import java.io.Serializable

data class MblabsEvents(
    val uid: String = "",
    val name: String = "",
    val desc: String = "",
    val price: Double = 0.0,
    val uri: String = "",
    val date: String = ""
) : Serializable
