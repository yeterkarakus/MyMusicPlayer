package com.yeterkarakus.miniyoutube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yeterkarakus.miniyoutube.databinding.ArtistAlbumsRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.AlbumsViewModel


lateinit var onSingleItemClickListener : ArtistAlbumAdapter.OnItemClickListener
lateinit var artistAlbumList: List<AlbumsViewModel>

class ArtistAlbumAdapter(albumList: List<AlbumsViewModel>, onItemClickListener:OnItemClickListener): RecyclerView.Adapter<ArtistAlbumAdapter.ArtistSingleViewHolder>() {
    init {
        onSingleItemClickListener = onItemClickListener
        artistAlbumList = albumList

    }


    class ArtistSingleViewHolder(val binding: ArtistAlbumsRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistSingleViewHolder {
        val binding = ArtistAlbumsRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtistSingleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistAlbumList.size
    }

    override fun onBindViewHolder(holder: ArtistSingleViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(artistAlbumList[position].albumsImgUrl)
                .transform(RoundedCorners(15))
                .into(albumImg)
            albumYear.text = artistAlbumList[position].albumsDate.toString()
            albumName.text = artistAlbumList[position].albumsName
        }
        holder.itemView.setOnClickListener {
            onSingleItemClickListener.onAlbumsItemSelected(artistAlbumList[position])
        }
    }


    interface OnItemClickListener {
        fun onAlbumsItemSelected(albumsViewModel: AlbumsViewModel)
    }
}

