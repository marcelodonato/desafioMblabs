package com.marcelodonato.desafiomblabs.common.model

import java.io.Serializable

data class Event(
    val uid: String,
    val name: String,
    val desc: String,
    val price: Double,
    val uri: String
) : Serializable
