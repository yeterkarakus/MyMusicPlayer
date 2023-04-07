package com.yeterkarakus.miniyoutube.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeterkarakus.miniyoutube.databinding.AlbumDetailsRecyclerRowBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel

lateinit var onItemClick :  AlbumTracksRecyclerAdapter.OnItemClickListener
lateinit var albumTrackList : List<AlbumTracksViewModel>

class AlbumTracksRecyclerAdapter (albumTracks: List<AlbumTracksViewModel>, onItemClickListener : OnItemClickListener):RecyclerView.Adapter<AlbumTracksRecyclerAdapter.AlbumTracksViewHolder>(){
    init {
        onItemClick = onItemClickListener
        albumTrackList = albumTracks

    }

    class AlbumTracksViewHolder (val binding: AlbumDetailsRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTracksViewHolder {
        val binding =
            AlbumDetailsRecyclerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumTracksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumTrackList.size
    }


    override fun onBindViewHolder(holder: AlbumTracksViewHolder, position: Int) {
        holder.binding.apply {
            albumTrackList.let {
                trackName.text = it[position].name
                artisName.text = it[position].artistName
                trackNumber.text = albumTrackList[position].trackNumber.toString()
            }

        }
        holder.itemView.setOnClickListener {
            onItemClick.onAlbumTrackItemsSelect(albumTrackList[position])
        }
    }
    interface OnItemClickListener{

        fun onAlbumTrackItemsSelect(albumTracksViewModel: AlbumTracksViewModel)

    }
}
