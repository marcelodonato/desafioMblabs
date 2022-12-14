package com.marcelodonato.desafiomblabs.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import kotlin.math.roundToInt

fun Context.getStyledAttributes(
    attributeSet: AttributeSet?,
    styleArray: IntArray,
    block: TypedArray.() -> Unit
) = this.obtainStyledAttributes(attributeSet, styleArray, 0, 0).use(block)

const val NOT_TOUCHABLE = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

fun Context.getDrawableCompat(drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

fun Context.getColorCompat(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getColorStateListCompat(color: Int) = ContextCompat.getColorStateList(this, color)

fun Context.hideKeyboard() {
    try {
        (this as Activity).currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.lockScreen() {
    (this as? AppCompatActivity)?.window?.setFlags(NOT_TOUCHABLE, NOT_TOUCHABLE)
}

fun Context.unlockScreen() {
    (this as? AppCompatActivity)?.window?.clearFlags(NOT_TOUCHABLE)
}

fun Context.intentByClassName(className: String) = Intent().setClassName(this, className)

fun <T : Number> Context.dpToPX(dp: T): Int =
    (resources.displayMetrics.density.times(dp.toFloat())).roundToInt()