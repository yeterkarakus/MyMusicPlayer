package com.yeterkarakus.miniyoutube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.databinding.AlbumsRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.AlbumViewModel

    lateinit var onItemClickListener: AlbumsRecyclerAdapter.OnItemClickListener
    lateinit var  albumList: List<AlbumViewModel>

class AlbumsRecyclerAdapter(albumList2: List<AlbumViewModel>, onItemClickListener2: OnItemClickListener): RecyclerView.Adapter<AlbumsRecyclerAdapter.AlbumViewHolder>() {
    init {
        onItemClickListener = onItemClickListener2
        albumList = albumList2
    }

    class AlbumViewHolder(val binding: AlbumsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = AlbumsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlbumViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.binding.apply {
            albumList.let {
                albumName.text = it[position].name
                artisName.text= it[position].artist
                Glide.with(holder.itemView)
                    .load(it[position].imgUrl)
                    .transform(RoundedCorners(15))
                    .into(albumImg)
            }
        }

        holder.itemView.setOnClickListener{
            onItemClickListener.onItemSelect(albumList[position])
        }
    }

    interface OnItemClickListener {
        fun onItemSelect(albumViewModel: AlbumViewModel)
    }
}