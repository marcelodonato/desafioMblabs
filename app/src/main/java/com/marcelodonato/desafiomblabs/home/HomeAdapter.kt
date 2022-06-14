package com.marcelodonato.desafiomblabs.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.EventItemBinding

class HomeAdapter(
    private val items: MutableList<MblabsEvents>,
    private val onItemClick: ((item: MblabsEvents, index: Int) -> Unit)? = null
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        return ViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(var binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val eventList = items[position]

        holder.binding.apply {
            tvNameEvent.text = eventList.name
            tvDescEvent.text = eventList.desc
            Glide.with(holder.binding.ivEvent).load(eventList.uri.toUri()).into(ivEvent)

            cvContainerEvent.setOnClickListener {
                onItemClick?.invoke(eventList, position)

            }
        }
    }
}