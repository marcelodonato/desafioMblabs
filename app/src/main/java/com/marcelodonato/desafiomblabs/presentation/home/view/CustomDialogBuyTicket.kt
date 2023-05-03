package com.marcelodonato.desafiomblabs.presentation.home.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.toast
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.DialogBuyTicketBinding
import com.marcelodonato.desafiomblabs.presentation.base.BaseDialog
import com.marcelodonato.desafiomblabs.presentation.profile.view.ProfileActivity
import java.text.SimpleDateFormat
import java.util.*

class CustomDialogBuyTicket(private val events: MblabsEvents) : BaseDialog() {

    private var quantity: Int = 1
    private val format = "yyyy-MM-dd-HH-mm-ss-SSS"
    private lateinit var binding: DialogBuyTicketBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogBuyTicketBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setValuesTicket()
        closeDialog()
    }

    private fun addEventToDatabase(
        name: String?,
        desc: String?,
        price: Double?,
        uri: String?,
        date: String?
    ) {
        val id = SimpleDateFormat(format, Locale.US).format(System.currentTimeMillis())
        val ref = FirebaseDatabase.getInstance().getReference("/profile/$id")
        val events =
            MblabsEvents(uid = id, name = name, desc = desc, price = price, uri = uri, date = date)
        ref.setValue(events)
    }

    private fun setValuesTicket() {
        binding.tvNameEventTicket.text = events.name
        binding.tvDateEventTicket.text = events.date
        binding.tvDescEventTicket.text = events.desc
        binding.tvPriceTicket.text = events.price.toString()

        binding.tvAddTicket.setOnClickListener {
            quantity++
            binding.tvQuantityTicket.text = quantity.toString()
            binding.tvPriceTicket.text = getString(R.string.price_ticket,
                events.price?.let { it1 -> setValue(it1) })
        }
        binding.tvRemoveTicket.setOnClickListener {
            quantity--
            binding.tvQuantityTicket.text = quantity.toString()
            binding.tvPriceTicket.text = getString(R.string.price_ticket,
                events.price?.let { it1 -> setValue(it1) })
            if (quantity < 1) {
                quantity = 1
                binding.tvQuantityTicket.text = quantity.toString()
                binding.tvPriceTicket.text =
                    getString(R.string.price_ticket, events.price?.let { it1 -> setValue(it1) })
            }
        }
        binding.btnBuyTicket.setOnClickListener {
            addEventToDatabase(events.name, events.desc, events.price, events.uri, events.date)
            binding.animationBuyTicket.visibility = VISIBLE
            Handler().postDelayed({ dialog?.cancel() }, 2500.toLong())
        }
        Glide.with(binding.ivEventTicket).load(events.uri?.toUri()).into(binding.ivEventTicket)
    }

    private fun setValue(value: Double): Double {
        return value * quantity
    }

    private fun closeDialog() {
        binding.btnCloseTicket.setOnClickListener {
            dialog?.cancel()
        }
    }
}