package com.marcelodonato.desafiomblabs.presentation.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.marcelodonato.desafiomblabs.databinding.AppFooterBarBinding

class AppFooterBar : ConstraintLayout {

    private val binding : AppFooterBarBinding = AppFooterBarBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}