package com.marcelodonato.desafiomblabs.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcelodonato.desafiomblabs.common.model.Event
import com.marcelodonato.desafiomblabs.databinding.EventItemBinding


class MainAdapter(private val items: MutableList<Event>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
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


    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {

        val eventList = items[position]
        holder.binding.apply {
            itemEventName.text = eventList.name
            itemEventDesc.text = eventList.desc
        }
    }


}