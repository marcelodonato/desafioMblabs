package com.marcelodonato.desafiomblabs.common.model

import java.io.Serializable

data class MblabsEvents(
    val uid: String? = null,
    val name: String? = null,
    val desc: String? = null,
    val price: Double? = null,
    val uri: String? = null,
    val date: String? = null
) : Serializable
