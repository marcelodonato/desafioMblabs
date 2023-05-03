package com.marcelodonato.desafiomblabs.common.extension

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.marcelodonato.desafiomblabs.presentation.base.BaseDialog
import com.marcelodonato.desafiomblabs.presentation.base.BaseFragment

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }

fun FragmentActivity.addFragment(
    containerViewId: Int,
    fragment: Fragment,
    enterAnimation: Int = FragmentTransaction.TRANSIT_NONE,
    exitAnimation: Int = FragmentTransaction.TRANSIT_NONE,
    backStack: String? = null,
    tag: String? = fragment.javaClass.canonicalName,
) {
    if (!fragment.isAdded) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation)
            add(containerViewId, fragment, tag)
            backStack?.let { addToBackStack(it) }
            commitSafe(supportFragmentManager)
        }
    }
}

fun FragmentActivity.replaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    enterAnimation: Int = FragmentTransaction.TRANSIT_NONE,
    exitAnimation: Int = FragmentTransaction.TRANSIT_NONE,
    backStack: String? = null,
    tag: String? = fragment.javaClass.canonicalName,
    allowStateLoss: Boolean = true,
) {
    supportFragmentManager.beginTransaction().run {
        setCustomAnimations(enterAnimation, exitAnimation)
        replace(containerViewId, fragment, tag)
        backStack?.let { addToBackStack(it) }
        if (allowStateLoss)
            commitAllowingStateLoss(supportFragmentManager)
        else
            commitSafe(supportFragmentManager)
    }
}

fun FragmentActivity.removeFragment(
    fragment: Fragment,
    allowStateLoss: Boolean = true,
) {
    supportFragmentManager.beginTransaction().run {
        remove(fragment)
        if (allowStateLoss)
            commitAllowingStateLoss(supportFragmentManager)
        else
            commitSafe(supportFragmentManager)
    }
}

fun FragmentActivity.clearBackStack(backStack: String? = null) {
    supportFragmentManager.run {
        popBackStack(backStack, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        runPendingTransactions()
    }
}

fun FragmentActivity.clearTop(fragment: Fragment) {
    supportFragmentManager.run {
        fragments.firstOrNull { it.javaClass.name == fragment.javaClass.name }?.let {
            do {
                val lastFragment = fragments.last()
                popBackStack()
                runPendingTransactions()
            } while (lastFragment != it)
        }
    }
}

fun FragmentTransaction?.commitSafe(fragmentManager: FragmentManager?) {
    fragmentManager?.let { fm ->
        if (!fm.isDestroyed && !fm.isStateSaved)
            this?.commit()
        else
            this?.commitAllowingStateLoss()
    }
}


fun FragmentTransaction?.commitAllowingStateLoss(fragmentManager: FragmentManager?) {
    fragmentManager?.let {
        this?.commitAllowingStateLoss()
    }
}

fun FragmentManager.runPendingTransactions() {
    try {
        this.executePendingTransactions()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun FragmentManager.getAppFragments() = this.fragments.filter {
    it.tag != null && it is BaseFragment<*>
}

inline fun <reified T> Fragment.argument(key: String, crossinline default: () -> T): Lazy<T> =
    lazy {
        val value = arguments?.get(key)
        if (value is T) value else default()
    }

inline fun <reified T> Fragment.argument(key: String): Lazy<T> = lazy {
    val value = arguments?.get(key)
    if (value is T) {
        value
    } else {
        throw IllegalArgumentException("Couldn't find extra with key \"$key\" from type " + T::class.java.canonicalName)
    }
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

fun Intent.clearTop() = this.apply {
    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
}

fun FragmentActivity.showSafeDialog(dialog: BaseDialog, tag: String? = null) {
    if (!isFinishing) dialog.show(supportFragmentManager, tag)
}

