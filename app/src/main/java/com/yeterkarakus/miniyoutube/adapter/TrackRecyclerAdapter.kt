package com.yeterkarakus.miniyoutube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yeterkarakus.miniyoutube.databinding.TrackRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.TrackViewModel

lateinit var onTracksItemClickListener : TrackRecyclerAdapter.OnItemClickListener
lateinit var trackListItem : List<TrackViewModel>
class TrackRecyclerAdapter(  val trackList: List<TrackViewModel>, onItemClickListener: OnItemClickListener
    ): RecyclerView.Adapter<TrackRecyclerAdapter.TrackViewHolder>() {

    init {
        onTracksItemClickListener = onItemClickListener
        trackListItem = trackList

    }
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
                Glide.with(holder.itemView).load(it[position].imgUrl).transform(RoundedCorners(15)).into(trackImg)
            }
        }
        holder.itemView.setOnClickListener {
            onTracksItemClickListener.onItemSelected(trackListItem[position])
        }
    }

    interface OnItemClickListener{
        fun onItemSelected( trackViewModel : TrackViewModel)
    }

}