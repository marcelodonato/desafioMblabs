package com.marcelodonato.desafiomblabs.presentation.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.EventItemProfileBinding

class ProfileAdapter(
    private val items: MutableList<MblabsEvents?>,
    private val onItemClick: ((item: MblabsEvents, index: Int) -> Unit)? = null
) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EventItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(var binding: EventItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        val eventList = items[position]

        holder.binding.apply {
            tvNameEventProfile.text = eventList?.name
            tvDateEventProfile.text = eventList?.date
            tvDescEventProfile.text = eventList?.desc
            Glide.with(holder.binding.ivEventProfile).load(eventList?.uri?.toUri())
                .into(ivEventProfile)
            btnExclude.setOnClickListener {
                if (eventList != null) {
                    onItemClick?.invoke(eventList, position)
                }
            }
        }
    }
}