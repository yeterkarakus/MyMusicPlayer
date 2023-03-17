package com.yeterkarakus.miniyoutube.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.R
import com.yeterkarakus.miniyoutube.databinding.AlbumsRecyclerRowBinding
import com.yeterkarakus.miniyoutube.databinding.TrackRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.SearchActiveFragmentDirections
import com.yeterkarakus.miniyoutube.view.searchpage.albumsmodel.AlbumDetailsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.model.AlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.model.TrackViewModel
import javax.inject.Inject


class AlbumsRecyclerAdapter(private val albumList: List<AlbumViewModel>,
                            private var albumDetailsViewModel: AlbumDetailsViewModel? = null
): RecyclerView.Adapter<AlbumsRecyclerAdapter.TrackViewHolder>() {
    class TrackViewHolder(val binding: AlbumsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = AlbumsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrackViewHolder(binding)

    }

    override fun getItemCount(): Int {
       return albumList.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.binding.apply {
            albumList.let {
                albumName.text = it[position].name
                artisName.text= it[position].artist
                Glide.with(holder.itemView).load(it[position].imgUrl).into(albumImg)
            }
        }

        holder.itemView.setOnClickListener {view->
            albumDetailsViewModel?.let {
                val action = SearchActiveFragmentDirections.actionSearchActiveFragmentToAlbumsFragment(it)
                view.findNavController().navigate(action)
            }

        }
    }

}