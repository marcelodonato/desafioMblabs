package com.marcelodonato.desafiomblabs.common.model

import android.net.Uri
import java.io.Serializable


data class Event(
    val uid: String? = null,
    val name: String,
    val desc: String? = null,
    val uri: String? = null
) : Serializable
