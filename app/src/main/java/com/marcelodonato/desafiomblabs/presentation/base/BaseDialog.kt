package com.marcelodonato.desafiomblabs.presentation.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog: DialogFragment() {

    override fun show(manager: FragmentManager, tag: String?) {
        manager.takeIf { !isAdded }?.run {
            if (!manager.isDestroyed && !manager.isStateSaved)
                super.show(manager, tag)
            else
                manager.beginTransaction().add(this@BaseDialog, tag).commitAllowingStateLoss()
        }
    }

    override fun dismiss() {
        if (isResumed)
            super.dismiss()
        else
            dismissAllowingStateLoss()
    }
}