package com.marcelodonato.desafiomblabs.presentation.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.EventItemBinding

class HomeAdapter(
    private val items: MutableList<MblabsEvents>,
    private val onItemClick: ((item: MblabsEvents, index: Int) -> Unit)? = null
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eventList = items[position]

        holder.binding.apply {
            tvNameEvent.text = eventList.name
            tvDateEvent.text = eventList.date
            tvDescEvent.text = eventList.desc
            Glide.with(holder.binding.ivEvent).load(eventList.uri.toUri()).into(ivEvent)
            cvContainerEvent.setOnClickListener {
                onItemClick?.invoke(eventList, position)
            }
        }
    }
}