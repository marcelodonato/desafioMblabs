package com.marcelodonato.desafiomblabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            itemEventName.text = eventList.name
            itemEventDesc.text = eventList.desc
            cvContainerEvent.setOnClickListener {
                onItemClick?.invoke(eventList,position)
            }
        }
    }

}