package com.yeterkarakus.miniyoutube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.databinding.TrackRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.model.TrackViewModel


class TrackRecyclerAdapter( private val trackList: List<TrackViewModel>
    ): RecyclerView.Adapter<TrackRecyclerAdapter.TrackViewHolder>() {
    class TrackViewHolder(val binding: TrackRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrackViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return trackList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.binding.apply {
            trackList.let {
                trackName.text = it[position].name
                artisName.text= it[position].artist
                Glide.with(holder.itemView).load(it[position].imgUrl).into(trackImg)
            }
        }
    }

}