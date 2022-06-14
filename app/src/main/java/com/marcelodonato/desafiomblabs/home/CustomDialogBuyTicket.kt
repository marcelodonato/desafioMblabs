package com.marcelodonato.desafiomblabs.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.DialogBuyTicketBinding

class CustomDialogBuyTicket(private val events: MblabsEvents) : DialogFragment() {

    private var quantity: Int = 1
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
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        setValuesTicket()
    }

    private fun setValuesTicket() {

        binding.tvNameEventTicket.text = events.name
        binding.tvDescEventTicket.text = events.desc
        binding.tvPriceTicket.text = events.price.toString()

        binding.tvAddTicket.setOnClickListener {
            quantity++
            binding.tvQuantityTicket.text = quantity.toString()
            binding.tvPriceTicket.text = getString(R.string.price_ticket, setValue(events.price))

        }
        binding.tvRemoveTicket.setOnClickListener {
            quantity--
            if (quantity < 1) {
                quantity = 1
                binding.tvQuantityTicket.text = quantity.toString()
                binding.tvPriceTicket.text =
                    getString(R.string.price_ticket, setValue(events.price))
            }
        }

    }

    private fun setValue(value: Double): Double {
        return value * quantity
    }
}